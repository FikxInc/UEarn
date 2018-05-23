package com.example.phiiphiroberts.uearn;

public class RecylerItem {
    private int mImageResource;
    private String mText1;

    public RecylerItem(int imageResource, String text1){

        mImageResource = imageResource;
        mText1 = text1;
    }

    public int getmImageResource(){
        return mImageResource;
    }

    public String getmText1() {
        return mText1;
    }
}


