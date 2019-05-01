package com.example.vinitkumaragarwal.orderguru;

import android.app.Application;

/**
 * Created by Vinit Kumar Agarwal on 14/12/2017.
 */


/*{"next":{"$ref":"https://apex.oracle.com/pls/apex/buysell/loginuser/validateloginuser/8800682039/1111?page=1"},
        "items":[{"id_login_user":1,"mobile_number":8800682039,"pin_number":1111,"firm_name":"cybernation","owner_name":
        "vinit","address_line1":"307 akruti","address_line2":"behind gcc club","pincode":401107,"city":"mira road","state":"maharastra",
        "created_by":"ADMIN","created_on":"2017-12-03T10:51:48.743Z","modified_by":"ADMIN","modified_on":"2017-12-05T14:06:47.68Z","is_active":"Y"}]}*/
public class GlobalVariable extends Application {
    private int idLoginUser,pincode;
    private String mobileNumber,pinNumber,firmName,ownerName,addressline1;

    public int getIdLoginUser() {
        return idLoginUser;
    }

    public void setIdLoginUser(int idLoginUser) {
        this.idLoginUser = idLoginUser;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(String pinNumber) {
        this.pinNumber = pinNumber;
    }

    public String getFirmName() {
        return firmName;
    }

    public void setFirmName(String firmName) {
        this.firmName = firmName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getAddressline1() {
        return addressline1;
    }

    public void setAddressline1(String addressline1) {
        this.addressline1 = addressline1;
    }
}


