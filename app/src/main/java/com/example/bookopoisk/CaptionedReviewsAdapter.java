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

public class CaptionedReviewsAdapter extends RecyclerView.Adapter<CaptionedReviewsAdapter.ViewHolder> {

    private String[] names;
    private int[] rating;
    private String[] reviews;
    private int[] imageIds;

    CaptionedReviewsAdapter(String[] names, int[] rating, String[] reviews, int[] imageIds) {
        this.names = names;
        this.rating = rating;
        this.reviews = reviews;
        this.imageIds = imageIds;
    }

    @NonNull
    @Override
    public CaptionedReviewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_captioned_reviews, parent, false);
        return new CaptionedReviewsAdapter.ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull CaptionedReviewsAdapter.ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        ImageView imageView = cardView.findViewById(R.id.user_avatar);
        Drawable drawable =
                ContextCompat.getDrawable(cardView.getContext(), imageIds[position]);
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(names[position]);

        TextView userName = cardView.findViewById(R.id.user_name);
        userName.setText(names[position]);

        TextView userReview = cardView.findViewById(R.id.user_review);
        userReview.setText(reviews[position]);

        TextView userRating = cardView.findViewById(R.id.user_rating);
        userRating.setText(new StringBuilder().append(rating[position]).append(" из 10").toString());
    }

    @Override
    public int getItemCount() {
        return names.length;
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
