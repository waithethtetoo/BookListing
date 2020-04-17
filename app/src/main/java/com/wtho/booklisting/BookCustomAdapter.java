package com.wtho.booklisting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class BookCustomAdapter extends ArrayAdapter<Books> {

   public BookCustomAdapter(@NonNull Context context, @NonNull List<Books> objects) {
      super(context, 0, objects);
   }

   @NonNull
   @Override
   public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
      View rootView = convertView;
      if (rootView == null) {
         rootView = LayoutInflater.from(getContext()).inflate(R.layout.list_items, parent, false);
      }
      TextView tvTitle = rootView.findViewById(R.id.tvTitle);
      TextView tvAuthor = rootView.findViewById(R.id.tvAuthor);
      TextView tvPublishedDate = rootView.findViewById(R.id.tvPublishedDate);

      Books book = getItem(position);
      tvTitle.setText(book.getmTitle());
      tvAuthor.setText(book.getmAuthor());
      tvPublishedDate.setText(book.getmPublishedDate());

      return rootView;
   }
}
