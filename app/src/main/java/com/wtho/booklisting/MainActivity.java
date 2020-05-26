package com.wtho.booklisting;

import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Books>> {
   private RecyclerView lstBooks;
   private TextView tvEmpty;
   private ProgressBar progressBar;
   private static String BOOKS_URL = "https://www.googleapis.com/books/v1/volumes?q=android";
   private ConnectivityManager cm;
   private BookCustomAdapter adapter;
   private List<Books> mBooksList;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      lstBooks = findViewById(R.id.lst_books);
      tvEmpty = findViewById(R.id.tv_empty);
      progressBar = findViewById(R.id.loading);
      lstBooks.setLayoutManager(new LinearLayoutManager(this));

      cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo info = cm.getActiveNetworkInfo();

      if (info.isConnected() && info != null) {
         LoaderManager loaderManager = getLoaderManager();
         loaderManager.initLoader(1, null, this);
      } else {
         progressBar.setVisibility(View.GONE);
         tvEmpty.setText(R.string.no_connection);
      }
   }


   @Override
   public Loader<List<Books>> onCreateLoader(int i, Bundle bundle) {
      return new BooksLoader(this, BOOKS_URL);
   }

   @Override
   public void onLoadFinished(@NonNull Loader<List<Books>> loader, List<Books> data) {
      progressBar.setVisibility(View.GONE);

      if (data != null && !data.isEmpty()) {
         mBooksList = data;
         adapter = new BookCustomAdapter(mBooksList);
         lstBooks.setAdapter(adapter);
      } else {
         tvEmpty.setText(R.string.no_books);
      }
   }

   @Override
   public void onLoaderReset(@NonNull Loader<List<Books>> loader) {
   }
}
