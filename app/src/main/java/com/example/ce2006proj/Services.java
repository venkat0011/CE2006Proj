package com.example.ce2006proj;

public class Services
{
    private String centre_code;
    private String centre_name;
    private String class_of_licence;
    private String fees;
    private String levels_offered;
    private String type_of_citizenship;
    private String type_of_service;
    private String id;
    private String remarks;

    public void setType_of_citizenship(String citizenship) {
        this.type_of_citizenship = citizenship;
    }

    public String getType_of_citizenship() {
        return type_of_citizenship;
    }

    public String getFees() {
        return fees;
    }

    public String getCentre_code() {
        return centre_code;
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


    public void setFees(String fees) {
        this.fees = fees;
    }

    public void setLevels_offered(String levels_offered) {
        this.levels_offered = levels_offered;
    }

    public void setType_of_service(String type_of_service) {
        this.type_of_service = type_of_service;
    }

    public void setClass_of_licence(String class_of_licence) {
        this.class_of_licence = class_of_licence;
    }

    public String getId() {
        return id;
    }

    public String getClass_of_licence() {
        return class_of_licence;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }



    public Services () {}
}
