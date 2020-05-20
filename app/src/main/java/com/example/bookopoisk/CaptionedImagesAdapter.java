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

public class CaptionedImagesAdapter extends
        RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder> {

    private String[] captions1;
    private String[] captions2;
    private int[] imageIds;
    private Listener listener;

    interface Listener {
        void onClick(int position);
    }

    CaptionedImagesAdapter(String[] captions1, String[] captions2, int[] imageIds) {
        this.captions1 = captions1;
        this.captions2 = captions2;
        this.imageIds = imageIds;
    }

    @NonNull
    @Override
    public CaptionedImagesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_captioned_image, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull CaptionedImagesAdapter.ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;
        ImageView imageView = cardView.findViewById(R.id.book_image);
        Drawable drawable =
                ContextCompat.getDrawable(cardView.getContext(), imageIds[position]);
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(captions1[position]);

        TextView textView1 = cardView.findViewById(R.id.book_name);
        textView1.setText(captions1[position]);

        TextView textView2 = cardView.findViewById(R.id.book_author);
        textView2.setText(captions2[position]);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return captions1.length;
    }

    void setListener(Listener listener) {
        this.listener = listener;
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
