package com.example.clone_project_01;

public class TestClass {
    public String key;
    public String storeType;
    public String storeHash;
    public String storeName;
    public String storeAddr;
    public String storeAddr2;
    public String storeTell;
    public String storeDate;

    public TestClass(String storeType, String storeHash, String storeName, String storeAddr, String storeAddr2, String storeTell, String storeDate) {
        this.storeType = storeType;
        this.storeHash = storeHash;
        this.storeName = storeName;
        this.storeAddr = storeAddr;
        this.storeAddr2 = storeAddr2;
        this.storeTell = storeTell;
        this.storeDate = storeDate;
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
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
