package com.example.vinitkumaragarwal.orderguru.dao;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vinit Kumar Agarwal on 28/11/2017.
 */

public class productsubcategorydao implements Parcelable {


    private int idProductSubCategory,idProductCategory;
    private String productSubCategory,productSubCategoryLink;

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

    public String getProductSubCategory() {
        return productSubCategory;
    }

    public void setProductSubCategory(String productSubCategory) {
        this.productSubCategory = productSubCategory;
    }

    public String getProductSubCategoryLink() {
        return productSubCategoryLink;
    }

    public void setProductSubCategoryLink(String productSubCategoryLink) {
        this.productSubCategoryLink = productSubCategoryLink;
    }

    public productsubcategorydao(int idProductSubCategory, int idProductCategory, String productSubCategory, String productSubCategoryLink) {
        this.idProductSubCategory = idProductSubCategory;
        this.idProductCategory = idProductCategory;
        this.productSubCategory = productSubCategory;
        this.productSubCategoryLink = productSubCategoryLink;
    }

    protected productsubcategorydao(Parcel in) {

        idProductSubCategory = in.readInt();
        idProductCategory = in.readInt();
        productSubCategory = in.readString();
        productSubCategoryLink = in.readString();
    }


    public static final Creator<productsubcategorydao> CREATOR = new Creator<productsubcategorydao>() {
        @Override
        public productsubcategorydao createFromParcel(Parcel in) {
            return new productsubcategorydao(in);
        }

        @Override
        public productsubcategorydao[] newArray(int size) {
            return new productsubcategorydao[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idProductCategory);
        parcel.writeInt(idProductSubCategory);
        parcel.writeString(productSubCategory);
        parcel.writeString(productSubCategoryLink);
    }


}
