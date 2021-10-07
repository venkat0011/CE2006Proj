package com.example.ce2006proj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindSchool_Control {
    public static Student getStudent()
    {
        return StudentDatabase.student;
    }



    // this method is to find which district the child is in
    public List<String> FindDistrict(String postal_code)
    {
        ArrayList<List<String>> districts = new ArrayList<>();
        districts.add(Arrays.asList("01", "02!", "03", "04", "05","06"));
        districts.add(Arrays.asList("07","08"));
        districts.add(Arrays.asList("14","15","16"));
        districts.add(Arrays.asList("09","10"));
        districts.add(Arrays.asList("11","12","13"));
        districts.add(Arrays.asList("17"));
        districts.add(Arrays.asList("18","19"));
        districts.add(Arrays.asList("20","21"));
        districts.add(Arrays.asList("22","23"));
        districts.add(Arrays.asList("24","25","26","27"));
        districts.add(Arrays.asList("28","29","30"));
        districts.add(Arrays.asList("31","32","33"));
        districts.add(Arrays.asList("34","35","36","37"));
        districts.add(Arrays.asList("38","39","40","41"));
        districts.add(Arrays.asList("42","43","44","45"));
        districts.add(Arrays.asList("46","47","48"));
        districts.add(Arrays.asList("49","50","81"));
        districts.add(Arrays.asList("51","52"));
        districts.add(Arrays.asList("53","54","55","82"));
        districts.add(Arrays.asList("56","57"));
        districts.add(Arrays.asList("58","59"));
        districts.add(Arrays.asList("60","61","62","63","64"));
        districts.add(Arrays.asList("65","66","67","68"));
        districts.add(Arrays.asList("69","70","71"));
        districts.add(Arrays.asList("72","73"));
        districts.add(Arrays.asList("77","78"));
        districts.add(Arrays.asList("75","76"));
        districts.add(Arrays.asList("79","80"));

        String district_code = postal_code.substring(0,1);
        for(List<String> lists: districts)
        {
            if(lists.contains(district_code))
            {
                return lists;
            }
        }
        return null;

    }
}
