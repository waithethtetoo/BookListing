package com.wtho.booklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class BooksLoader extends AsyncTaskLoader<List<Books>> {

   public static final String LOG_TAG = MainActivity.class.getName();
   private String murl;

   public BooksLoader(Context context, String url) {
      super(context);
      this.murl = url;
   }

   @Override
   protected void onStartLoading() {
      super.onStartLoading();
      forceLoad();
   }

   @Override
   public List<Books> loadInBackground() {
      if (murl == null) {
         return null;
      }
      List<Books> books = QueryUtils.fetchBooksData(murl);
      return books;
   }
}
