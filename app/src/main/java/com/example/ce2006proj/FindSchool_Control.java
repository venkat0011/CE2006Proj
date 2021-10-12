package com.example.ce2006proj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindSchool_Control {



    // this method is to find which district the child is in
    public List<String> FindDistrict(String postal_code)
    {
        ArrayList<List<String>> districts = new ArrayList<>();
        districts.add(Arrays.asList("01", "02", "03", "04", "05","06"));
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

    public static void  FindSchool(SchoolList_Callback callback) // by right this should a return of schools
    {
        // first call the service database to get the list of services that match the
        // the age group of the student

        // after we get the list of centre codes then we will send to the school database
        // to find the list of school objects

        ServiceDatabase.FetchServices(new ServiceCallBack() {
            @Override
            public void onResponse(ArrayList<Services> services) {
                // we will do the for loop for each services here and display the list of schools
               ArrayList<Schools> schools = new ArrayList<>();
                for(Services services1: services)
                {
                    SchoolDatabase.FetchSchools(new SchoolCallBack() {
                        @Override
                        public void onCallback(Schools school) {
                            schools.add(school);
                        }
                    },services1.getCentre_code());

                }
                callback.onCallBack(schools);
            }
        },AgeGroup(StudentDatabase.student.getAge())
                ,Citizenship(StudentDatabase.student.getCitizenship()));



    }

    public static String AgeGroup (String age)
    {
        String agegroup;
        switch (age)
        {
            case "2 to 18 mths":
                agegroup = "Infant (2 to 18 mths)";
                break;
            case "18 mths to 2 yrs old":
                agegroup = "Playgroup (18 mths to 2 yrs old)";
                break;
            case "3 yrs old":
                agegroup = "Pre-Nursery (3 yrs old)";
                break;
            case "4 yrs old":
                agegroup = "Nursery (4 yrs old)";
                break;
            case "5 yrs old":
                agegroup ="Kindergarten 1 (5 yrs old)";
                break;
            case "6 yrs old":
                agegroup = "Kindergarten 2 (6 yrs old)";
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + age);
        }
        return agegroup;
    }

    public static String Citizenship(String citizen)
    {
        String citizenship;
        switch (citizen)
        {
            case "Citizen":
                citizenship = "SC";
                break;
            case "PR":
                citizenship = "SPR";
                break;
            case "Other":
                citizenship = "Others";
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + citizen);
        }
        return citizenship;
    }


    public static Student getStudent()
    {
        return StudentDatabase.student;
    }
    public static void setStudent(Student student )
    {
        StudentDatabase.student = student;
    }


}
