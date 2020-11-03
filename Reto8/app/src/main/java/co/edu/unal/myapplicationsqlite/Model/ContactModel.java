package co.edu.unal.myapplicationsqlite.Model;

import android.content.res.Resources;

import co.edu.unal.myapplicationsqlite.R;

public class ContactModel {
    private Integer id;
    private String enterprise;
    private String URL;
    private String phoneNumber;
    private String email;
    private String productsNServices;
    private Classification classification;
    public enum Classification {CONSULTANCY,CUSTOM_DEVELOPMENT,SOFTWARE_FACTORY}

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProductsNServices() {
        return productsNServices;
    }

    public void setProductsNServices(String productsNServices) {
        this.productsNServices = productsNServices;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Classification getClassification() {
        return classification;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }
    public static Classification stringToClass(String type) {
        if (type.equals("Software factory")){
            return Classification.SOFTWARE_FACTORY;
        }else if(type.equals("Consultancy")){
            return Classification.CONSULTANCY;
        }else if (type.equals("Custom development")){
            return Classification.CUSTOM_DEVELOPMENT;
        }
        return null;
    }

    public static String classToString(Classification classification){
        if (classification == Classification.CONSULTANCY){
            return "Consultancy";
        }else if (classification == Classification.SOFTWARE_FACTORY){
            return "Software factory";
        }else {
            return "Custom development";
        }
    }
}
