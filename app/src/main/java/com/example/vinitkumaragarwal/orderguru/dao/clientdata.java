package com.example.vinitkumaragarwal.orderguru.dao;

/**
 * Created by Vinit Kumar Agarwal on 28/11/2017.
 */

public class clientdata {

    private int id_client,id_BUYER_ID_LOGIN_USER;
    private String mobile_number,firm_name,owner_name,address_line1,pincode,is_blocked;

    public int getId_BUYER_ID_LOGIN_USER() {
        return id_BUYER_ID_LOGIN_USER;
    }

    public void setId_BUYER_ID_LOGIN_USER(int id_BUYER_ID_LOGIN_USER) {
        this.id_BUYER_ID_LOGIN_USER = id_BUYER_ID_LOGIN_USER;
    }

    public clientdata(int id_client, String mobile_number, String firm_name, String owner_name, String address_line1, String pincode, String is_blocked,int id_BUYER_ID_LOGIN_USER) {
        this.id_client = id_client;
        this.mobile_number = mobile_number;
        this.id_BUYER_ID_LOGIN_USER = id_BUYER_ID_LOGIN_USER;
        this.firm_name = firm_name;
        this.owner_name = owner_name;
        this.address_line1 = address_line1;
        this.pincode = pincode;
        this.is_blocked = is_blocked;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getFirm_name() {
        return firm_name;
    }

    public void setFirm_name(String firm_name) {
        this.firm_name = firm_name;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getAddress_line1() {
        return address_line1;
    }

    public void setAddress_line1(String address_line1) {
        this.address_line1 = address_line1;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getIs_blocked() {
        return is_blocked;
    }

    public void setIs_blocked(String is_blocked) {
        this.is_blocked = is_blocked;
    }
}
