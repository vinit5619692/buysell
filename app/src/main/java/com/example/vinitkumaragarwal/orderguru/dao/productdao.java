package com.example.vinitkumaragarwal.orderguru.dao;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vinit Kumar Agarwal on 28/11/2017.
 */

public class productdao implements Parcelable {

    private String  productName,productDescription,productMaker,productImageLink;


    private int idProductMaster,idProductSubCategory,idProductCategory,idLoginUser;

    public int getIdLoginUser() {
        return idLoginUser;
    }

    public void setIdLoginUser(int idLoginUser) {
        this.idLoginUser = idLoginUser;
    }

    public productdao(int idProductMaster, int idProductSubCategory, int idProductCategory, String productName, String productDescription, String productMaker, String productImageLink,int idLoginUser) {
        this.idProductMaster = idProductMaster;
        this.idProductSubCategory = idProductSubCategory;
        this.idProductCategory = idProductCategory;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productMaker = productMaker;
        this.productImageLink = productImageLink;
        this.idLoginUser = idLoginUser;
    }

    public int getIdProductMaster() {

        return idProductMaster;
    }

    public void setIdProductMaster(int idProductMaster) {
        this.idProductMaster = idProductMaster;
    }

    public int getIdProductSubCategory() {
        return idProductSubCategory;
    }

    public void setIdProductSubCategory(int idProductSubCategory) {
        this.idProductSubCategory = idProductSubCategory;
    }

    public int getIdProductCategory() {
        return idProductCategory;
    }

    public void setIdProductCategory(int idProductCategory) {
        this.idProductCategory = idProductCategory;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductMaker() {
        return productMaker;
    }

    public void setProductMaker(String productMaker) {
        this.productMaker = productMaker;
    }

    public String getProductImageLink() {
        return productImageLink;
    }

    public void setProductImageLink(String productImageLink) {
        this.productImageLink = productImageLink;
    }



    protected productdao(Parcel in) {
        idProductMaster = in.readInt();
        idProductSubCategory = in.readInt();
        idProductCategory = in.readInt();
        productName = in.readString();
        productDescription = in.readString();
        productMaker = in.readString();
        productImageLink = in.readString();
        idLoginUser = in.readInt();

    }


    public static final Creator<productdao> CREATOR = new Creator<productdao>() {
        @Override
        public productdao createFromParcel(Parcel in) {
            return new productdao(in);
        }

        @Override
        public productdao[] newArray(int size) {
            return new productdao[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idProductMaster);
        parcel.writeInt(idProductCategory);
        parcel.writeInt(idProductSubCategory);
        parcel.writeString(productName);
        parcel.writeString(productDescription);
        parcel.writeString(productMaker);
        parcel.writeString(productImageLink);
        parcel.writeInt(idLoginUser);
    }


}
