package com.wtho.booklisting;

public class Books {
   public String mTitle;
   public String mAuthor;
   public String mPublishedDate;
   public String mImageUrl;

   public Books(String mTitle, String mAuthor, String mPublishedDate, String mImageUrl) {
      this.mTitle = mTitle;
      this.mAuthor = mAuthor;
      this.mPublishedDate = mPublishedDate;
      this.mImageUrl = mImageUrl;
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

   public String getmImageUrl() {
      return mImageUrl;
   }

   public void setmImageUrl(String mImageUrl) {
      this.mImageUrl = mImageUrl;
   }
}
