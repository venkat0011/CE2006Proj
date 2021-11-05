package com.example.ce2006proj;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Filter_Control
{
    public ArrayList<Schools> FilterByFood(ArrayList<Schools> schools, String food)
    {
        Log.e("size of array", String.valueOf(schools.size()));
        ArrayList<Schools> schoolsArrayList= new ArrayList<>();
        for(Schools schools1: schools)
        {
            if(schools1.getFood_offered().equals(food))
            {
                schoolsArrayList.add(schools1);
            }
        }
        return schoolsArrayList;
    }

    public ArrayList<Schools> FilterByTransport(ArrayList<Schools> schools, String transport)
    {
        ArrayList<Schools> schoolsArrayList= new ArrayList<>();
        for(Schools schools1: schools)
        {
            if(schools1.getProvision_of_transport().equals(transport))
            {
                schoolsArrayList.add(schools1);
            }
        }
        return schoolsArrayList;
    }


    public ArrayList<Schools> FilterByLanguage(ArrayList<Schools> schools, String Language)
    {
        ArrayList<Schools> schoolsArrayList= new ArrayList<>();
        for(Schools schools1: schools)
        {
            Log.e("Language",schools1.getSecond_languages_offered());
            if(schools1.getSecond_languages_offered().contains(Language))
            {
                schoolsArrayList.add(schools1);
            }
        }
        return schoolsArrayList;
    }

    public ArrayList<Schools> FilterBySpark(ArrayList<Schools> schools, String Spark)
    {
        ArrayList<Schools> schoolsArrayList= new ArrayList<>();
        for(Schools schools1: schools)
        {
            if(schools1.getSpark_certified().equals(Spark))
            {
                schoolsArrayList.add(schools1);
            }
        }
        return schoolsArrayList;
    }


    public ArrayList<Schools> FilterByOperating(ArrayList<Schools> schools, String hour)
    {
        ArrayList<Schools> schoolsArrayList= new ArrayList<>();
        for(Schools schools1: schools)
        {
            for(Services services: schools1.getService_list())
            {
                if(services.getType_of_service().equals(hour))
                {
                    schoolsArrayList.add(schools1);
                    break;
                }
            }
        }
        return schoolsArrayList;
    }
}
