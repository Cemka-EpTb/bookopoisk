package com.example.bookopoisk;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.material.appbar.AppBarLayout;

public class BookDetailActivity extends AppCompatActivity implements AppBarLayout.BaseOnOffsetChangedListener {

    public static final String EXTRA_BOOK_ID = "bookId";

    private LinearLayout titleLinearLayout;
    private Animation animation_appear;
    private Animation animation_hide;
    private boolean anim_switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        int bookId = (Integer) getIntent().getExtras().get(EXTRA_BOOK_ID);
        String bookName = Book.books[bookId].getName();
        TextView textName = findViewById(R.id.book_name);
        textName.setText(bookName);

        String bookAuthor = Book.books[bookId].getAuthor();
        TextView textAuthor = findViewById(R.id.book_author);
        textAuthor.setText(bookAuthor);

        int bookImage = Book.books[bookId].getImageResourceId();
        ImageView imageView = findViewById(R.id.book_image);
        imageView.setImageDrawable(ContextCompat.getDrawable(this, bookImage));

        TextView titleBookName = findViewById(R.id.title_book_name);
        titleBookName.setText(bookName);
        TextView titleBookAuthor = findViewById(R.id.title_book_author);
        titleBookAuthor.setText(bookAuthor);

        anim_switch = false;
        animation_appear = AnimationUtils.loadAnimation(this, R.anim.tv_animation_appear);
        animation_hide = AnimationUtils.loadAnimation(this, R.anim.tv_animation_hide);
        titleLinearLayout = findViewById(R.id.title_linear_layout);
        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(this);
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
        if ((Math.abs(verticalOffset) < appBarLayout.getTotalScrollRange()) && anim_switch){
            anim_switch = false;
            titleLinearLayout.clearAnimation();
            titleLinearLayout.startAnimation(animation_hide);
            titleLinearLayout.setVisibility(View.INVISIBLE);
        }
    }
}
