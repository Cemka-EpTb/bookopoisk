package com.example.bookopoisk;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    private ArrayList<String> names;
    private ArrayList<String> rating;
    private ArrayList<String> reviews;

    ReviewsAdapter(ArrayList<String> names, ArrayList<String> rating, ArrayList<String> reviews) {
        this.names = names;
        this.rating = rating;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_reviews, parent, false);
        return new ReviewsAdapter.ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsAdapter.ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        ImageView imageView = cardView.findViewById(R.id.user_avatar);
        Drawable drawable =
                ContextCompat.getDrawable(cardView.getContext(), R.mipmap.avatar_default);
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(names.get(position));

        TextView userName = cardView.findViewById(R.id.user_name);
        userName.setText(names.get(position));

        TextView userReview = cardView.findViewById(R.id.user_review);
        userReview.setText(reviews.get(position));

        TextView userRating = cardView.findViewById(R.id.user_rating);
        userRating.setText(new StringBuilder().append(rating.get(position)).append(" из 10").toString());
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;

        ViewHolder(@NonNull CardView v) {
            super(v);
            cardView = v;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
