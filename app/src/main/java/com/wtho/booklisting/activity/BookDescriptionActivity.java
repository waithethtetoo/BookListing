package com.wtho.booklisting.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.wtho.booklisting.R;

public class BookDescriptionActivity extends AppCompatActivity {
   private ImageView mBookCover;
   private TextView mBookDescription;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_book_description);

      // Initialize
      mBookCover = findViewById(R.id.book_cover);
      mBookDescription = findViewById(R.id.book_description);

      Intent intent = getIntent();
      String bookURL = intent.getStringExtra("bookURL");
      String description = intent.getStringExtra("description");

      Picasso.with(this).load(bookURL).error(R.drawable.ic_book_24dp).into(mBookCover);
      mBookDescription.setText(description);
   }
}
