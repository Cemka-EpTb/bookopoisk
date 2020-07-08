package com.example.bookopoisk;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookopoisk.Api.MainApplication;
import com.example.bookopoisk.Api.ResponseObject;
import com.example.bookopoisk.Api.ServerBookData;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookDetailActivity extends AppCompatActivity implements BaseOnOffsetChangedListener {

    public static final String EXTRA_BOOK_ID = "bookId";

    private Context context;
    private LinearLayout titleLinearLayout;
    private Animation animation_appear;
    private Animation animation_hide;
    private boolean anim_switch;

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

        int bookId = (Integer) getIntent().getExtras().get(EXTRA_BOOK_ID);

        MainApplication.apiManager.getBookInfo(bookId, new Callback<ResponseObject>() {
            @Override
            public void onResponse(@NonNull Call<ResponseObject> call, @NonNull Response<ResponseObject> response) {
                ResponseObject responseBook = response.body();
                if (response.isSuccessful() && responseBook != null) {
                    ServerBookData getBook = responseBook.getData();

                    String bookName = getBook.getName();
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
        animation_appear = AnimationUtils.loadAnimation(this, R.anim.tv_animation_appear);
        animation_hide = AnimationUtils.loadAnimation(this, R.anim.tv_animation_hide);
        titleLinearLayout = findViewById(R.id.title_linear_layout);
        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(this);

        RecyclerView recyclerView = findViewById(R.id.recycle_reviews);
        String[] userNames = new String[ReviewsTemp.reviews.length];
        for (int i = 0; i < userNames.length; i++) {
            userNames[i] = ReviewsTemp.reviews[i].getName();
        }

        int[] userRating = new int[ReviewsTemp.reviews.length];
        for (int i = 0; i < userRating.length; i++) {
            userRating[i] = ReviewsTemp.reviews[i].getRating();
        }

        String[] userReview = new String[ReviewsTemp.reviews.length];
        for (int i = 0; i < userReview.length; i++) {
            userReview[i] = ReviewsTemp.reviews[i].getReview();
        }

        int[] userAvatar = new int[ReviewsTemp.reviews.length];
        for (int i = 0; i < userAvatar.length; i++) {
            userAvatar[i] = ReviewsTemp.reviews[i].getImageResourceId();
        }

        CaptionedReviewsAdapter adapter = new CaptionedReviewsAdapter(userNames, userRating, userReview, userAvatar);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
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
}
