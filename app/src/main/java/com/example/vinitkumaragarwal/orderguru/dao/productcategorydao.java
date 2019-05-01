package com.example.vinitkumaragarwal.orderguru.dao;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vinit Kumar Agarwal on 28/11/2017.
 */

public class productcategorydao implements Parcelable {


    private int idProductCategory;
    private String productCategory,productCategoryLink;


    public productcategorydao(int idProductCategory, String productCategory, String productCategoryLink) {
        this.idProductCategory = idProductCategory;
        this.productCategory = productCategory;
        this.productCategoryLink = productCategoryLink;
    }

    public int getIdProductCategory() {
        return idProductCategory;

    }

    public void setIdProductCategory(int idProductCategory) {
        this.idProductCategory = idProductCategory;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductCategoryLink() {
        return productCategoryLink;
    }

    public void setProductCategoryLink(String productCategoryLink) {
        this.productCategoryLink = productCategoryLink;
    }

    protected productcategorydao(Parcel in) {


        idProductCategory = in.readInt();
        productCategory = in.readString();
        productCategoryLink = in.readString();
    }


    public static final Creator<productcategorydao> CREATOR = new Creator<productcategorydao>() {
        @Override
        public productcategorydao createFromParcel(Parcel in) {
            return new productcategorydao(in);
        }

        @Override
        public productcategorydao[] newArray(int size) {
            return new productcategorydao[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idProductCategory);
        parcel.writeString(productCategory);
        parcel.writeString(productCategoryLink);
    }


}
