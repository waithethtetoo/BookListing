package com.wtho.booklisting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class BookCustomAdapter extends RecyclerView.Adapter<BookCustomAdapter.BookViewHolder> {
   private List<Books> booksList;
   private Context context;

   public BookCustomAdapter(List<Books> booksList) {
      this.booksList = booksList;
   }


   @NonNull
   @Override
   public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      context = parent.getContext();
      View rootView = LayoutInflater.from(context)
              .inflate(R.layout.list_items, parent, false);
      return new BookViewHolder(rootView);
   }

   @Override
   public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
      Books book = booksList.get(position);
      holder.mTitle.setText(book.getmTitle());
      holder.mAuthor.setText(book.getmAuthor());
      holder.mPublishedDate.setText(book.getmPublishedDate());
      Picasso.with(context).load(book.getmImageUrl())
              .error(R.drawable.ic_book_24dp).into(holder.mBookImage);
   }

   @Override
   public int getItemCount() {
      return booksList.size();
   }

   public static class BookViewHolder extends RecyclerView.ViewHolder {
      TextView mTitle;
      TextView mAuthor;
      TextView mPublishedDate;
      ImageView mBookImage;

      public BookViewHolder(@NonNull View itemView) {
         super(itemView);
         mTitle = itemView.findViewById(R.id.tvTitle);
         mAuthor = itemView.findViewById(R.id.tvAuthor);
         mPublishedDate = itemView.findViewById(R.id.tvPublishedDate);
         mBookImage = itemView.findViewById(R.id.book_image);
      }
   }
}
