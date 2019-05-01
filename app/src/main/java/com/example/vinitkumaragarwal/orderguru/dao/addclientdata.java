package com.example.vinitkumaragarwal.orderguru.dao;

/**
 * Created by Vinit Kumar Agarwal on 28/11/2017.
 */

public class addclientdata {

    private int id_login_user,id_seller;
    private String mobile_number,firm_name,owner_name;


    public int getId_login_user() {
        return id_login_user;
    }

    public void setId_login_user(int id_login_user) {
        this.id_login_user = id_login_user;
    }

    public int getId_seller() {
        return id_seller;
    }

    public void setId_seller(int id_seller) {
        this.id_seller = id_seller;
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

    public addclientdata(int id_login_user, int id_seller, String mobile_number, String firm_name, String owner_name) {
        this.id_login_user = id_login_user;
        this.id_seller = id_seller;
        this.mobile_number = mobile_number;

        this.firm_name = firm_name;
        this.owner_name = owner_name;
    }
}
