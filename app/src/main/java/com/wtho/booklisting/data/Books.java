package com.wtho.booklisting.data;

public class Books {
   public String mTitle;
   public String mAuthor;
   public String mPublishedDate;
   public String mDescription;
   public String mImageUrl;

   public Books(String mTitle, String mAuthor, String mPublishedDate, String mDescription, String mImageUrl) {
      this.mTitle = mTitle;
      this.mAuthor = mAuthor;
      this.mPublishedDate = mPublishedDate;
      this.mDescription = mDescription;
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

   public String getmDescription() {
      return mDescription;
   }

   public void setmDescription(String mDescription) {
      this.mDescription = mDescription;
   }
}
