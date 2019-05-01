package com.example.vinitkumaragarwal.orderguru.dao;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vinit Kumar Agarwal on 28/11/2017.
 */

public class productmasterdao implements Parcelable {

    private int idProductMaster;
    private String productName,productDesctription,productSubCategory,productCategory,productMake,productImageLink;


    public productmasterdao(int idProductMaster, String productName, String productDesctription, String productSubCategory, String productCategory, String productMake, String productImageLink) {
        this.idProductMaster = idProductMaster;
        this.productName = productName;
        this.productDesctription = productDesctription;
        this.productSubCategory = productSubCategory;
        this.productCategory = productCategory;
        this.productMake = productMake;
        this.productImageLink = productImageLink;
    }

    public int getIdProductMaster() {
        return idProductMaster;
    }

    public void setIdProductMaster(int idProductMaster) {
        this.idProductMaster = idProductMaster;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesctription() {
        return productDesctription;
    }

    public void setProductDesctription(String productDesctription) {
        this.productDesctription = productDesctription;
    }

    public String getProductSubCategory() {
        return productSubCategory;
    }

    public void setProductSubCategory(String productSubCategory) {
        this.productSubCategory = productSubCategory;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductMake() {
        return productMake;
    }

    public void setProductMake(String productMake) {
        this.productMake = productMake;
    }

    public String getProductImageLink() {
        return productImageLink;
    }

    public void setProductImageLink(String productImageLink) {
        productImageLink = productImageLink;
    }


    protected productmasterdao(Parcel in) {


        idProductMaster = in.readInt();
        productName = in.readString();
        productDesctription = in.readString();
        productSubCategory = in.readString();
        productCategory = in.readString();
        productMake = in.readString();
        productImageLink = in.readString();
    }


    public static final Creator<productmasterdao> CREATOR = new Creator<productmasterdao>() {
        @Override
        public productmasterdao createFromParcel(Parcel in) {
            return new productmasterdao(in);
        }

        @Override
        public productmasterdao[] newArray(int size) {
            return new productmasterdao[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idProductMaster);
        parcel.writeString(productName);
        parcel.writeString(productDesctription);
        parcel.writeString(productSubCategory);
        parcel.writeString(productCategory);
        parcel.writeString(productMake);
        parcel.writeString(productImageLink);
    }


}
