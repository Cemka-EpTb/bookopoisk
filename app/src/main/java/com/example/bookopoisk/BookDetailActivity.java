package com.example.bookopoisk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookopoisk.Api.MainApplication;
import com.example.bookopoisk.Api.ResponseObject;
import com.example.bookopoisk.Api.ServerBookData;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookDetailActivity extends AppCompatActivity implements BaseOnOffsetChangedListener,
        View.OnClickListener {

    public static final String EXTRA_BOOK_ID = "bookId";

    private Context context;
    private LinearLayout titleLinearLayout;
    private Animation animation_appear;
    private Animation animation_hide;
    private boolean anim_switch;
    private int bookId;
    private String login;
    private Intent intent;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        context = this;

        // Тулбар
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        bookId = (Integer) getIntent().getExtras().get(EXTRA_BOOK_ID);  // Получаем id книги
        intent = new Intent(this, ReviewActivity.class);
        intent.putExtra("bookId", bookId);

        MainApplication.apiManager.getBookInfo(bookId, new Callback<ResponseObject>() {  // Информация о книге по id с сервера bookopoisk
            @Override
            public void onResponse(@NonNull Call<ResponseObject> call, @NonNull Response<ResponseObject> response) {
                ResponseObject responseBook = response.body();
                if (response.isSuccessful() && responseBook != null) {
                    ServerBookData getBook = responseBook.getData();

                    String bookName = getBook.getName();
                    intent.putExtra("bookName", bookName);
                    TextView tvName = findViewById(R.id.book_name);
                    tvName.setText(bookName);
                    TextView titleBookName = findViewById(R.id.title_book_name);
                    titleBookName.setText(bookName);

                    String bookAuthor = getBook.getAuthor().getName();
                    TextView tvAuthor = findViewById(R.id.book_author);
                    tvAuthor.setText(bookAuthor);
                    TextView titleBookAuthor = findViewById(R.id.title_book_author);
                    titleBookAuthor.setText(bookAuthor);

                    ImageView ivBookImage = findViewById(R.id.book_image);
                    Glide.with(context)
                            .load(getBook.getImage().getUrl().concat("/120/170"))
                            .thumbnail(Glide.with(context).load(R.drawable.load_gif))
                            .error(Glide.with(context).load(R.drawable.image_not_found))
                            .into(ivBookImage);


                    String bookSynopsis = getBook.getSynopsis();
                    TextView tvSynopsis = findViewById(R.id.synopsis);
                    tvSynopsis.setText(bookSynopsis);

                    String bookRating = getBook.getRating();
                    TextView tvRating = findViewById(R.id.book_rating);
                    tvRating.setText(bookRating);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseObject> call, @NonNull Throwable t) {
                Toast.makeText(getBaseContext(),
                        "Error is " + t.getMessage()
                        , Toast.LENGTH_LONG).show();
            }
        });

        anim_switch = false;
        animation_appear = AnimationUtils.loadAnimation(this, R.anim.tv_animation_appear);  // Анимация при прокручивании страницы
        animation_hide = AnimationUtils.loadAnimation(this, R.anim.tv_animation_hide);
        titleLinearLayout = findViewById(R.id.title_linear_layout);
        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(this);

        LinearLayout messageLayout = findViewById(R.id.layout_message);
        CardView messageCard = (CardView) LayoutInflater.from(messageLayout.getContext())
                .inflate(R.layout.message_card, messageLayout, false);
        CardView reviewCard = (CardView) LayoutInflater.from(messageLayout.getContext())
                .inflate(R.layout.card_review_with_menu, messageLayout, false);
        login = getSharedPreferences("auth", MODE_PRIVATE).getString("login", "");


        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("ids/" + bookId);

        ArrayList<String> userNames = new ArrayList<>();
        ArrayList<String> userRatings = new ArrayList<>();
        ArrayList<String> userReviews = new ArrayList<>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    if (childDataSnapshot.child("score").getValue() != null) {
                        String userLogin = childDataSnapshot.getKey();
                        String userScore = childDataSnapshot.child("score").getValue().toString();
                        String userReview = childDataSnapshot.child("review").getValue().toString();

                        userNames.add(userLogin);
                        userRatings.add(userScore);
                        userReviews.add(userReview);
                    }
                }

                RecyclerView recyclerView = findViewById(R.id.recycle_reviews);
                ReviewsAdapter adapter = new ReviewsAdapter(userNames, userRatings, userReviews);
                recyclerView.setAdapter(adapter);
                LinearLayoutManager llm = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(llm);

                if (dataSnapshot.child(login).exists()) {
                    TextView tv = (TextView) LayoutInflater.from(messageLayout.getContext())
                            .inflate(R.layout.tv_review_message, messageLayout, false);
                    messageLayout.removeAllViews();
                    messageLayout.addView(tv);
                    messageLayout.addView(reviewCard);

                    ImageButton menuButton = findViewById(R.id.menuButton);
                    menuButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showMenu(menuButton);
                        }
                    });

                    TextView userLogin = findViewById(R.id.user_name);
                    userLogin.setText(login);

                    TextView userScore = findViewById(R.id.user_rating);
                    userScore.setText(dataSnapshot.child(login).child("score").getValue() + " из 10");

                    TextView userReview = findViewById(R.id.user_review);
                    userReview.setText(dataSnapshot.child(login).child("review").getValue().toString());

                    ImageView imageView = findViewById(R.id.user_avatar);
                    imageView.setImageDrawable(ContextCompat.getDrawable(reviewCard.getContext(), R.mipmap.avatar_default));
                } else {
                    messageLayout.addView(messageCard);

                    TextView card_header = findViewById(R.id.card_header);
                    card_header.setText("Оцените книгу");
                    TextView message = findViewById(R.id.message);
                    message.setText("Оставьте свою рецензию на эту книгу! Нажмите здесь чтобы сделать это.");

                    messageCard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if (login.equals("")) {
            messageLayout.addView(messageCard);

            TextView card_header = findViewById(R.id.card_header);
            card_header.setText("Оцените книгу");
            TextView message = findViewById(R.id.message);
            message.setText("Неавторизованные пользователи не могут оставлять рецензии. Нажмите здесь чтобы войти в приложение.");

            messageCard.setOnClickListener(this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if ((Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) && !anim_switch) {
            // Collapsed
            anim_switch = true;
            titleLinearLayout.setVisibility(View.VISIBLE);
            titleLinearLayout.startAnimation(animation_appear);
        }
        if ((Math.abs(verticalOffset) < appBarLayout.getTotalScrollRange()) && anim_switch) {
            anim_switch = false;
            titleLinearLayout.clearAnimation();
            titleLinearLayout.startAnimation(animation_hide);
            titleLinearLayout.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;

        switch (v.getId()) {
            case R.id.message_card:
                intent = new Intent(this, LoginActivity.class);
                break;
        }

        startActivity(intent);
    }

    private void showMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_review, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.change_review:
                        TextView tvReview = findViewById(R.id.user_review);
                        intent.putExtra("review", tvReview.getText().toString());

                        TextView tvScore = findViewById(R.id.user_rating);
                        String score = tvScore.getText().toString().substring(0, 2).replaceAll(" ", "");
                        intent.putExtra("score", score);
                        startActivity(intent);
                        break;
                    case R.id.delete_review:
                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                        DatabaseReference ref = db.getReference("ids/" + bookId + "/" + login);
                        ref.removeValue();
                        recreate();
                        Toast.makeText(getBaseContext(), "Рецензия удалена.", Toast.LENGTH_LONG).show();
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }
}
