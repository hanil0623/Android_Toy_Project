package com.example.clone_project_01;
/* 쿼리에 필요한 결괏값만 새로 모아놓은 Class, Info 클래스의 값과 비슷함 */
public class TestClass {
    public String key;
    public int mode;
    public String storeName;
    public String storeAddr;

    TestClass(){}
    public TestClass(String storeName, String storeAddr) {
        this.storeName = storeName;
        this.storeAddr = storeAddr;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return "TestClass{" +
                "key='" + key + '\'' +
                ", mode=" + mode +
                ", storeName='" + storeName + '\'' +
                ", storeAddr='" + storeAddr + '\'' +
                '}';
    }
}
