package com.wtho.booklisting;

public class Books {
   public String mTitle;
   public String mAuthor;
   public String mPublishedDate;

   public Books(String mTitle, String mAuthor, String mPublishedDate) {
      this.mTitle = mTitle;
      this.mAuthor = mAuthor;
      this.mPublishedDate = mPublishedDate;
   }

   public String getmTitle() {
      return mTitle;
   }

   public String getmAuthor() {
      return mAuthor;
   }

   public String getmPublishedDate() {
      return mPublishedDate;
   }
}
