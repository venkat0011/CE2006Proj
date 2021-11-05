package com.example.ce2006proj;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.common.base.FinalizablePhantomReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.LogRecord;

public class FindSchool_Control {


    private Context context;
    public  Filter_Control filter_control = new Filter_Control();
    public void setContext(Context context) {
        this.context = context;
    }

    // this method is to find which district the child is in
    public static List<String> FindDistrict()
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

        String district_code = StudentDatabase.student.getLocation().substring(0,2);
        for(List<String> lists: districts)
        {
            if(lists.contains(district_code))
            {
                return lists;
            }
        }
        return null;

    }

    public static void  FindSchool(SchoolCallBack callback) // by right this should a return of schools
    {
        // call the school database to get the list of schools that are in the viscinity
        // then call the services that match eachj of these centre codes
        // when quering based on centre codes -> compare the centre code
        // if it is NA then use the TA to query

        SchoolDatabase.FetchSchools(new SchoolCallBack() {
            @Override
            public void onCallback(ArrayList<Schools> school) {
                Log.e("Control","It is in control callback");
                ArrayList<Schools> UpdatedSchools =new ArrayList<>();
                for (Schools school1: school)
                {
                    //Log.e("Finding service for",school1.getCentre_name());
                    String code = "";
                    if(school1.getCentre_code().equals("na"))
                    {
                        code = school1.getTp_code();
                    }
                    else
                    {
                        code = school1.getCentre_code();
                    }
                    // query for the service that this school provides

                    ServiceDatabase.FetchServices(new ServiceCallBack() {
                        @Override
                        public void onResponse(ArrayList<Services> services) {
                            if(services != null)
                            {
                                school1.setService_list(services);
                                // here check if any of the services match the age group
                                for(Services services1 : services)
                                {
                                    if(services1.getLevels_offered().equals(AgeGroup(StudentDatabase.student.getAge())))
                                    {
                                        UpdatedSchools.add(school1);
                                        Log.e("Services","One of the services match");
                                        break;
                                    }
                                }
                            }
                            if(school1.getPostal_code().equals(school.get(school.size()-1).getPostal_code()))
                            {
                                SchoolDatabase.schoolsArrayList = UpdatedSchools;
                                callback.onCallback(UpdatedSchools);

                            }





                        }
                    },code);

                }


            }
        });



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




    public void FindServices(SchoolCallBack schoolCallBack)
    {
        ServiceDatabase.FetchServices(new ServiceCallBack() {
            @Override
            public void onResponse(ArrayList<Services> services) {
                // for each of the service get the centre code
                // then go to the school database and get the relevant school
                for(Services services1:services)
                {
                    SchoolDatabase.FetchSchools(new SchoolCallBack() {
                        @Override
                        public void onCallback(ArrayList<Schools> school) {
                            if(school!=null)
                            {
                                ArrayList<Services>services2 = new ArrayList<>();
                                services2.add(services1);
                                school.get(0).setService_list(services2);
                                SchoolDatabase.schoolsArrayList.add(school.get(0));

                            }

                            if(services.indexOf(services1)==services.size()-1)
                            {
                                schoolCallBack.onCallback(SchoolDatabase.schoolsArrayList);

                            }
                        }
                    },services1.getCentre_code());
                }
            }
        });
    }
    public void coordinates(PostalCallback postalCallback, String postal_code)  {
// ...

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://developers.onemap.sg/commonapi/search?searchVal=" +postal_code+
                "&returnGeom=Y&getAddrDetails=N";

// Request a string response from the provided URL.

                JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray array = response.getJSONArray("results");
                                    JSONObject jsonObject = (JSONObject) array.get(0);
                                    ArrayList<Double> coordinates = new ArrayList<>();
                                    coordinates.add(Double.parseDouble(jsonObject.getString("LATITUDE")));
                                    coordinates.add(Double.parseDouble(jsonObject.getString("LONGITUDE")));
                                    postalCallback.PostalCallBack(coordinates);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("The code","That didn't work!");
                    }
                });

// Add the request to the RequestQueue.
                queue.add(stringRequest);
            }

    public String FavSchools(Schools schools)
    {
        ArrayList<Schools> schools1 = UserDatabase.user.getFav_school();
        if(schools1.isEmpty())
        {
            schools1.add(schools);
            UserDatabase.user.setFav_school(schools1);
            UserDatabase.UpdateUser(UserDatabase.user);
            return "School added to your favourite list";
        }
        else
        {
            if(schools1.contains(schools))
            {
                return "School already exist in your favourites";
            }
            else
            {
                schools1.add(schools);
                UserDatabase.user.setFav_school(schools1);
                UserDatabase.UpdateUser(UserDatabase.user);
                return "School added to your favourite list";
            }
        }
    }


    }





