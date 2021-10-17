package com.example.ce2006proj;

import java.util.ArrayList;
import java.util.logging.LoggingMXBean;

public class Schools {
    private String tp_code;
    private String centre_code;
    private String centre_name;
    private String organisation_code;
    private String organisation_description;
    private String service_model;
    private String centre_contact_no;
    private String centre_email_address;
    private String centre_address;
    private String postal_code;
    private String centre_website;
    private String infant_vacancy ;
    private String pg_vacancy;
    private String 	n1_vacancy;
    private String n2_vacancy;
    private String k1_vacancy;
    private String k2_vacancy;
    private String food_offered;
    private String second_languages_offered;
    private String spark_certified;
    private String weekday_full_day;
    private String 	saturday;
    private String scheme_type;
    private String extended_operating_hours;
    private String 	provision_of_transport;
    private String government_subsidy;
    private String gst_regisration;
    private Long _id;
    private String last_updated;
    private String remarks;
    private ArrayList<Services> service_list = new ArrayList<>();

    public String getInfant_vacancy() {
        return infant_vacancy;
    }

    public String getCentre_name() {
        return centre_name;
    }

    public String getCentre_code() {
        return centre_code;
    }

    public String getCentre_address() {
        return centre_address;
    }

    public String getCentre_contact_no() {
        return centre_contact_no;
    }

    public String getCentre_email_address() {
        return centre_email_address;
    }

    public String getCentre_website() {
        return centre_website;
    }

    public String getFood_offered() {
        return food_offered;
    }

    public String getExtended_operating_hours() {
        return extended_operating_hours;
    }

    public String getGovernment_subsidy() {
        return government_subsidy;
    }

    public String getGst_regisration() {
        return gst_regisration;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public String getOrganisation_description() {
        return organisation_description;
    }

    public String getProvision_of_transport() {
        return provision_of_transport;
    }

    public String getSaturday() {
        return saturday;
    }

    public String getScheme_type() {
        return scheme_type;
    }

    public String getSecond_languages_offered() {
        return second_languages_offered;
    }

    public String getSpark_certified() {
        return spark_certified;
    }

    public String getWeekday_full_day() {
        return weekday_full_day;
    }

    public void setCentre_name(String centre_name) {
        this.centre_name = centre_name;
    }

    public void setCentre_code(String centre_code) {
        this.centre_code = centre_code;
    }

    public String getN1_vacancy() {
        return n1_vacancy;
    }

    public String getOrganisation_code() {
        return organisation_code;
    }

    public String getService_model() {
        return service_model;
    }

    public String getTp_code() {
        return tp_code;
    }

    public void setCentre_address(String centre_address) {
        this.centre_address = centre_address;
    }

    public void setTp_code(String tp_code) {
        this.tp_code = tp_code;
    }

    public void setCentre_contact_no(String centre_contact_no) {
        this.centre_contact_no = centre_contact_no;
    }

    public void setCentre_email_address(String centre_email_address) {
        this.centre_email_address = centre_email_address;
    }

    public String getK1_vacancy() {
        return k1_vacancy;
    }

    public String getPg_vacancy() {
        return pg_vacancy;
    }

    public String getN2_vacancy() {
        return n2_vacancy;
    }

    public void setCentre_website(String centre_website) {
        this.centre_website = centre_website;
    }

    public void setOrganisation_code(String organisation_code) {
        this.organisation_code = organisation_code;
    }

    public String getK2_vacancy() {
        return k2_vacancy;
    }

    public void setExtended_operating_hours(String extended_operating_hours) {
        this.extended_operating_hours = extended_operating_hours;
    }

    public ArrayList<Services> getService_list() {
        return service_list;
    }

    public void setOrganisation_description(String organisation_description) {
        this.organisation_description = organisation_description;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public void setFood_offered(String food_offered) {
        this.food_offered = food_offered;
    }

    public void setInfant_vacancy(String infant_vacancy) {
        this.infant_vacancy = infant_vacancy;
    }

    public void setK1_vacancy(String k1_vacancy) {
        this.k1_vacancy = k1_vacancy;
    }

    public void setService_model(String service_model) {
        this.service_model = service_model;
    }

    public void setN1_vacancy(String n1_vacancy) {
        this.n1_vacancy = n1_vacancy;
    }

    public void setGovernment_subsidy(String government_subsidy) {
        this.government_subsidy = government_subsidy;
    }

    public void setPg_vacancy(String pg_vacancy) {
        this.pg_vacancy = pg_vacancy;
    }

    public void setGst_regisration(String gst_regisration) {
        this.gst_regisration = gst_regisration;
    }

    public void setK2_vacancy(String k2_vacancy) {
        this.k2_vacancy = k2_vacancy;
    }

    public void setN2_vacancy(String n2_vacancy) {
        this.n2_vacancy = n2_vacancy;
    }

    public void setProvision_of_transport(String provision_of_transport) {
        this.provision_of_transport = provision_of_transport;
    }

    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }

    public void setScheme_type(String scheme_type) {
        this.scheme_type = scheme_type;
    }

    public void setSecond_languages_offered(String second_languages_offered) {
        this.second_languages_offered = second_languages_offered;
    }

    public void setService_list(ArrayList<Services> service_list) {
        this.service_list = service_list;
    }

    public void setSpark_certified(String spark_certified) {
        this.spark_certified = spark_certified;
    }

    public void setWeekday_full_day(String weekday_full_day) {
        this.weekday_full_day = weekday_full_day;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarks() {
        return remarks;
    }

    public Long get_id() {
        return _id;
    }

    public String getLast_updated() {
        return last_updated;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public void setLast_updated(String last_updated) {
        this.last_updated = last_updated;
    }



    public Schools() {};
}

