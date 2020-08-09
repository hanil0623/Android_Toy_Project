package com.example.clone_project_01;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class Info implements Parcelable {
    public String key;
    public String storeType;
    public String storeHash;
    public String storeName;
    public String storeAddr;
    public String storeAddr2;
    public String storeTell;
    public String storeDate;

    public Info(){}

    public Info(String storeType, String storeHash, String storeName, String storeAddr, String storeAddr2, String storeTell, String storeDate, String key) {
        this.storeType = storeType;
        this.storeHash = storeHash;
        this.storeName = storeName;
        this.storeAddr = storeAddr;
        this.storeAddr2 = storeAddr2;
        this.storeTell = storeTell;
        this.storeDate = storeDate;
        this.key = key;
    }

    protected Info(Parcel in) {
        storeType = in.readString();
        storeHash = in.readString();
        storeName = in.readString();
        storeAddr = in.readString();
        storeAddr2 = in.readString();
        storeTell = in.readString();
        storeDate = in.readString();
    }

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public String getStoreHash() {
        return storeHash;
    }

    public void setStoreHash(String storeHash) {
        this.storeHash = storeHash;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddr() {
        return storeAddr;
    }

    public void setStoreAddr(String storeAddr) {
        this.storeAddr = storeAddr;
    }

    public String getStoreAddr2() {
        return storeAddr2;
    }

    public void setStoreAddr2(String storeAddr2) {
        this.storeAddr2 = storeAddr2;
    }

    public String getStoreTell() {
        return storeTell;
    }

    public void setStoreTell(String storeTell) {
        this.storeTell = storeTell;
    }

    public String getStoreDate() {
        return storeDate;
    }

    public void setStoreDate(String storeDate) {
        this.storeDate = storeDate;
    }

    public static final Creator<Info> CREATOR = new Creator<Info>() {
        @Override
        public Info createFromParcel(Parcel in) {
            return new Info(in);
        }

        @Override
        public Info[] newArray(int size) {
            return new Info[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(storeType);
        dest.writeString(storeHash);
        dest.writeString(storeName);
        dest.writeString(storeAddr);
        dest.writeString(storeAddr2);
        dest.writeString(storeTell);
        dest.writeString(storeDate);
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public String toString(){
        return "이름은 : " + storeName + " 업종명은 : " + storeType + "주소는 : " + storeAddr + "전화번호는 : " + storeTell;
    }
}
