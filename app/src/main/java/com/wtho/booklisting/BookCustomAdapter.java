package com.wtho.booklisting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookCustomAdapter extends RecyclerView.Adapter<BookCustomAdapter.BookViewHolder> {
   private List<Books> booksList;

   public BookCustomAdapter(List<Books> booksList) {
      this.booksList = booksList;
   }


   @NonNull
   @Override
   public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View rootView = LayoutInflater.from(parent.getContext())
              .inflate(R.layout.list_items, parent, false);
      return new BookViewHolder(rootView);
   }

   @Override
   public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
      Books book = booksList.get(position);
      holder.mTitle.setText(book.getmTitle());
      holder.mAuthor.setText(book.getmAuthor());
      holder.mPublishedDate.setText(book.getmPublishedDate());
   }

   @Override
   public int getItemCount() {
      return booksList.size();
   }

   public static class BookViewHolder extends RecyclerView.ViewHolder {
      TextView mTitle;
      TextView mAuthor;
      TextView mPublishedDate;

      public BookViewHolder(@NonNull View itemView) {
         super(itemView);
         mTitle = itemView.findViewById(R.id.tvTitle);
         mAuthor = itemView.findViewById(R.id.tvAuthor);
         mPublishedDate = itemView.findViewById(R.id.tvPublishedDate);
      }
   }
}
