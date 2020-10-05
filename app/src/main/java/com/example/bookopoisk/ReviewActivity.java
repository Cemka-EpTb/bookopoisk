package com.example.bookopoisk;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReviewActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener,
        View.OnClickListener {

    private static final String BOOK_NAME = "bookName";
    private static final String BOOK_ID = "bookId";
    private static final String BOOK_REVIEW = "review";
    private static final String BOOK_SCORE = "score";

    private TextView score;
    private int bookId;
    private TextView reviewText;
    private SeekBar seekBarScore;
    private Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        // Тулбар
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Название ниги в тулбаре
        String bookName = getIntent().getExtras().getString(BOOK_NAME);
        TextView bookNameInToolbar = findViewById(R.id.title_book_name);
        bookNameInToolbar.setText(bookName);

        // Поле оценки
        score = findViewById(R.id.score);

        // Слушатель SeekBar
        seekBarScore = findViewById(R.id.score_seek_bar);
        seekBarScore.setOnSeekBarChangeListener(this);

        // Слушатель книпки
        Button button = findViewById(R.id.button);
        button.setOnClickListener(this);

        // ID книги
        bookId = (int) getIntent().getExtras().get(BOOK_ID);

        // Текст отзыва
        reviewText = findViewById(R.id.review_text);

        // Context
        context = this;

        // Если рецензия меняется
        if (getIntent().getExtras().get(BOOK_REVIEW) != null) {
            String review = (String) getIntent().getExtras().get(BOOK_REVIEW);
            String scoreString = (String) getIntent().getExtras().get(BOOK_SCORE);
            int score = Integer.parseInt(scoreString);
            reviewText.setText(review);
            seekBarScore.setProgress(score);
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
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        score.setText(String.valueOf(progress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onClick(View v) {
        v.setVisibility(View.INVISIBLE);
        ProgressBar pb = findViewById(R.id.progress_bar);
        pb.setVisibility(View.VISIBLE);

        String login = getSharedPreferences("auth", MODE_PRIVATE).getString("login", "");

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("ids/" + bookId);

        assert login != null;
        ref.child(login).child("review").setValue(reviewText.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            ref.child(login).child("score").setValue(seekBarScore.getProgress())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                v.setVisibility(View.VISIBLE);
                                                pb.setVisibility(View.INVISIBLE);

                                                Toast.makeText(context, "Рецензия успешно опубликована.", Toast.LENGTH_SHORT).show();
                                                finish();
                                            } else {
                                                Toast.makeText(context, "Ошибка. Попробуйте ещё раз.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(context, "Ошибка. Попробуйте ещё раз.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}