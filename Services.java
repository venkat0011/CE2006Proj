package com.example.ce2006proj;

public class Services
{
    private String centre_code;
    private String centre_name;
    private String class_of_license;
    private int fees;
    private String levels_offered;
    private String citizenship;
    private String type_of_service;

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public int getFees() {
        return fees;
    }

    public String getCentre_code() {
        return centre_code;
    }

    public String getClass_of_license() {
        return class_of_license;
    }

    public String getCentre_name() {
        return centre_name;
    }

    public String getLevels_offered() {
        return levels_offered;
    }

    public String getType_of_service() {
        return type_of_service;
    }

    public void setCentre_code(String centre_code) {
        this.centre_code = centre_code;
    }

    public void setCentre_name(String centre_name) {
        this.centre_name = centre_name;
    }

    public void setClass_of_license(String class_of_license) {
        this.class_of_license = class_of_license;
    }

    public void setFees(int fees) {
        this.fees = fees;
    }

    public void setLevels_offered(String levels_offered) {
        this.levels_offered = levels_offered;
    }

    public void setType_of_service(String type_of_service) {
        this.type_of_service = type_of_service;
    }

    public Services () {}
}
