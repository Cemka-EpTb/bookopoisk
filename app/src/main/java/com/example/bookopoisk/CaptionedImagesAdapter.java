package com.example.bookopoisk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class CaptionedImagesAdapter extends
        RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder> {

    private Context context;
    private String[] book_name;
    private String[] author_name;
    private String[] imageUrls;
    private int[] bookId;
    private Listener listener;

    interface Listener {
        void onClick(int position);
    }

    CaptionedImagesAdapter(String[] book_name, String[] author_name, String[] imageUrls, int[] bookId) {
        this.book_name = book_name;
        this.author_name = author_name;
        this.imageUrls = imageUrls;
        this.bookId = bookId;
    }

    @NonNull
    @Override
    public CaptionedImagesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_captioned_image, parent, false);
        context = parent.getContext();
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull CaptionedImagesAdapter.ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;
        ImageView imageView = cardView.findViewById(R.id.book_image);

        Glide.with(context)
                .load(imageUrls[position])
                .thumbnail(Glide.with(context).load(R.drawable.load_gif))
                .error(Glide.with(context).load(R.drawable.image_not_found))
                .into(imageView);

        imageView.setContentDescription(book_name[position]);

        TextView textView1 = cardView.findViewById(R.id.book_name);
        textView1.setText(book_name[position]);

        TextView textView2 = cardView.findViewById(R.id.book_author);
        textView2.setText(author_name[position]);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClick(bookId[position]);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return book_name.length;
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
