package com.wtho.booklisting.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.wtho.booklisting.ItemListener;
import com.wtho.booklisting.data.Books;
import com.wtho.booklisting.R;

import java.util.List;

public class BookCustomAdapter extends RecyclerView.Adapter<BookCustomAdapter.BookViewHolder> {
   private List<Books> booksList;
   private Context context;
   private ItemListener listener;

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
   public void onBindViewHolder(@NonNull BookViewHolder holder, final int position) {
      Books book = booksList.get(position);
      holder.mTitle.setText(book.getmTitle());
      holder.mAuthor.setText(book.getmAuthor());
      holder.mPublishedDate.setText(book.getmPublishedDate());
      Picasso.with(context).load(book.getmImageUrl())
              .error(R.drawable.ic_book_24dp).into(holder.mBookImage);
      holder.mTitle.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            listener.onItemClick(position);
         }
      });
   }

   public void setOnItemClickListener(ItemListener itemListener) {
      this.listener = itemListener;
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
      private Context context;

      public BookViewHolder(@NonNull View itemView) {
         super(itemView);
         context = itemView.getContext();
         mTitle = itemView.findViewById(R.id.tvTitle);
         mAuthor = itemView.findViewById(R.id.tvAuthor);
         mPublishedDate = itemView.findViewById(R.id.tvPublishedDate);
         mBookImage = itemView.findViewById(R.id.book_image);
      }
   }
}
