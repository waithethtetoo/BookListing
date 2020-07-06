package com.wtho.booklisting.data;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.wtho.booklisting.activity.BookShowActivity;

import java.util.List;

public class BooksLoader extends AsyncTaskLoader<List<Books>> {

   public static final String LOG_TAG = BookShowActivity.class.getName();
   private String mUrl;

   public BooksLoader(Context context, String url) {
      super(context);
      this.mUrl = url;
   }

   @Override
   protected void onStartLoading() {
      super.onStartLoading();
      forceLoad();
   }

   @Override
   public List<Books> loadInBackground() {
      if (mUrl == null) {
         return null;
      }
      List<Books> books = QueryUtils.fetchBooksData(mUrl);
      return books;
   }
}
