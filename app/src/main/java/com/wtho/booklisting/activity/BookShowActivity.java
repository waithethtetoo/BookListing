package com.wtho.booklisting.activity;

import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.wtho.booklisting.ItemListener;
import com.wtho.booklisting.data.Books;
import com.wtho.booklisting.data.BooksLoader;
import com.wtho.booklisting.R;
import com.wtho.booklisting.adapter.BookCustomAdapter;


public class BookShowActivity extends AppCompatActivity implements LoaderCallbacks<List<Books>> {
   private RecyclerView lstBooks;
   private TextView tvEmpty;
   private ProgressBar progressBar;
   private static String BOOKS_URL = "https://www.googleapis.com/books/v1/volumes?q=fashion";
   private ConnectivityManager cm;
   private BookCustomAdapter adapter;
   private List<Books> mBooksList;
   private FirebaseAuth auth;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_book_show);

      auth = FirebaseAuth.getInstance();

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
         adapter.setOnItemClickListener(new ItemListener() {
            @Override
            public void onItemClick(int position) {
               String bookURL = mBooksList.get(position).getmImageUrl();
               String bookDescription = mBooksList.get(position).getmDescription();

               Intent intent = new Intent(getApplicationContext(), BookDescriptionActivity.class);
               intent.putExtra("bookURL", bookURL);
               intent.putExtra("description", bookDescription);
               startActivity(intent);
            }
         });
      } else {
         tvEmpty.setText(R.string.no_books);
      }
   }

   @Override
   public void onLoaderReset(@NonNull Loader<List<Books>> loader) {
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.menu_book, menu);
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      switch (item.getItemId()) {
         case R.id.action_profile:
            startActivity(new Intent(BookShowActivity.this, UserProfileActivity.class));
            break;
         case R.id.action_sign_out:
            signOut();
            break;
      }
      return super.onOptionsItemSelected(item);
   }

   private void signOut() {
      if (auth.getCurrentUser() != null) {
         auth.signOut();
         startActivity(new Intent(BookShowActivity.this, LoginActivity.class));
      }
   }
}
