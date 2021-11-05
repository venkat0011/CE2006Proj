package com.example.ce2006proj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.ce2006proj.databinding.ActivityMapsBinding;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.WeakHashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private FindSchool_Control findSchool_control = new FindSchool_Control();
    private LatLng latLng = new LatLng(1.287953,103.851784);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findSchool_control.setContext(this);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button button1 = findViewById(R.id.Change_Student);
        ImageButton button2 = findViewById(R.id.Filter_Options);
        button1.setText("Finding School for "+ FindSchool_Control.getStudent().getName());

    }






    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setInfoWindowAdapter(new SchoolPinAdapter(this));
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        // add location ping
        // add the camera to the current postal code of the student then move the camera
        findSchool_control.coordinates(new PostalCallback() {
            @Override
            public void PostalCallBack(ArrayList<Double> codes) {
                latLng = new LatLng(codes.get(0),codes.get(1));
                DisplayCurrentLocation(googleMap,latLng,15.5f);
            }
        },StudentDatabase.student.getLocation());

        // call the function to get the list of school

        FindSchool_Control.FindSchool(new SchoolCallBack() {
            @Override
            public void onCallback(ArrayList<Schools> school)
            {
                if(school.isEmpty())
                {
                    Log.e("as","NO SCHOOL FOUND");
                    // create a popup asking user to view other areas
                    // we need to display the list of schools
                    ViewDistricts();
                }
                else
                {
                    DisplaySchool(googleMap,school);
                }
            }

            });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        // few things to include is the fav button and the compare button
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
        marker.showInfoWindow();

        return true;
    }



    public void DisplaySchool(GoogleMap googleMap, ArrayList<Schools>schoolsArrayList)
    {
        for(Schools schools: schoolsArrayList)
        {

            findSchool_control.coordinates(new PostalCallback() {
                @Override
                public void PostalCallBack(ArrayList<Double> codes) {
                    LatLng position = new LatLng(codes.get(0), codes.get(1));//
                    MarkerOptions options = new MarkerOptions().position(position).title(schools.getCentre_name());
                    Marker marker = googleMap.addMarker(options);
                    marker.setTag(schools);
                }
            }, schools.getPostal_code());
        }
            // get the postal of each school
        Log.e("as","SCHOOL WITH THE SERVIFCE IS FOUIND");

    }

    public void ViewDistricts()
    {
        //in this method we display a popup
        // on positive button press we will call the method that prioritises the age group instead
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View customLayout = LayoutInflater.from(MapsActivity.this).inflate(R.layout.noschoolfound, null);
        builder.setView(customLayout);
        TextView text = customLayout.findViewById(R.id.School_Error);
        text.setText("OOPS it seems that no school in your area match to your child's age group," +
                "Would you like to view schools that are further away ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // call the method from services first
                // then call the method to school
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, (float) 10));
                findSchool_control.FindServices(new SchoolCallBack() {
                    @Override
                    public void onCallback(ArrayList<Schools> school) {

                        DisplaySchool(mMap,school);
                    }
                });
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }


    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {
        if(!marker.getTitle().equals("Current location"))
        {
            Log.e("asd","asdasd");
            // create the builder to show the new information
            Schools schools = (Schools) marker.getTag();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View customLayout = LayoutInflater.from(MapsActivity.this).inflate(R.layout.more_information, null);
            builder.setView(customLayout);
            builder.setTitle("More Information");
            //setting all the text values with the necessary texts
            TextView textView = customLayout.findViewById(R.id.name);
            textView.setText("NAME: "+schools.getCentre_name());
            TextView textView1 = customLayout.findViewById(R.id.address);
            textView1.setText("Address: "+schools.getCentre_address());
            TextView textView2 = customLayout.findViewById(R.id.food_offered);
            textView2.setText("Food Offered: "+schools.getFood_offered());
            TextView textView3 = customLayout.findViewById(R.id.language_offered);
            textView3.setText("Language offered :"+schools.getSecond_languages_offered());
            TextView textView4 = customLayout.findViewById(R.id.working_hours);
            textView4.setText("Working Hours: "+schools.getWeekday_full_day());
            TextView textView5 = customLayout.findViewById(R.id.sat_work);
            textView5.setText("Working Hours During Saturday : "+schools.getSaturday());
            TextView textView6 = customLayout.findViewById(R.id.extended_work);
            textView6.setText("Extended Working Hours : "+schools.getExtended_operating_hours());
            TextView textView7 = customLayout.findViewById(R.id.gov_subs);
            textView7.setText("Goverment Subsidised : "+schools.getGovernment_subsidy());
            TextView textView8 = customLayout.findViewById(R.id.sparl_cert);
            textView8.setText("Spark Certified : "+schools.getSpark_certified());
            TextView textView9 = customLayout.findViewById(R.id.transport_prov);
            textView9.setText("Transport Provided: "+schools.getProvision_of_transport());
            TextView textView10 = customLayout.findViewById(R.id.website);
            textView10.setText("Website: "+schools.getCentre_website());
            TextView textView11 = customLayout.findViewById(R.id.contact_number);
            textView11.setText("Contact Number: "+schools.getCentre_contact_no());
            ArrayList<String> list = new ArrayList<>();
            for(int i = 0;i<schools.getService_list().size();i++)
            {
                 list.add( schools.getService_list().get(i).getLevels_offered());
            }
            Set<String> list1 = new HashSet<String>(list);
            TextView textView12 = customLayout.findViewById(R.id.levels_prov);
            textView12.setText("Levels Offered: "+list1.toString());

            //setting up the image button
            ImageButton imageButton = customLayout.findViewById(R.id.favourite_btn);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String message = findSchool_control.FavSchools(schools);
                    Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                            .show();
                }
            });

            // setting up the comparison table;

            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

    }


    public void Filter(View view)
    {
        ScrollView scrollView = findViewById(R.id.scroll);

        if(scrollView.getVisibility()==View.VISIBLE)
        {
            scrollView.setVisibility(View.INVISIBLE);
        }
        else
        {
            scrollView.setVisibility(View.VISIBLE);
            // once they click on the button to filter schools then we call the functiion
            if(SchoolDatabase.schoolsArrayList.size()>3)
            {
                // set up the buttons
                Button filter_button = findViewById(R.id.Filter_button);
                // get the list of radio buttons selected
                RadioGroup operating_hour = findViewById(R.id.Operating_Hour);
                RadioGroup food = findViewById(R.id.Food);
                RadioGroup transport = findViewById(R.id.transport);
                RadioGroup spark = findViewById(R.id.Spark);
                RadioGroup language = findViewById(R.id.language);

                filter_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // we need to go through the list of options selected
                        // pass it to the fuindschool control to filter
                        // get the list back then call Display school

                        // we will first create an array list of schools
                        // pass the filter to filter class
                        // getting the radio button for food
                        ArrayList<Schools>schools = SchoolDatabase.schoolsArrayList;
                        RadioButton button;
                        if(!(food.getCheckedRadioButtonId()==-1))
                        {
                            button =  findViewById(food.getCheckedRadioButtonId());
                            if(button.getText().toString().equals("MUIS Certified"))
                            {
                                schools = findSchool_control.filter_control.FilterByFood(schools,"Halal Food With Beef (with Certification from MUIS)") ;
                            }
                            else if (button.getText().toString().equals("Vegetarian"))
                            {
                                schools = findSchool_control.filter_control.FilterByFood(schools,"Vegetarian");
                            }
                            else if(button.getText().toString().contains("Halal"))
                            {
                                schools = findSchool_control.filter_control.FilterByFood(schools,"No Pork No Lard with No Beef (without Certification from MUIS but from Halal Sources)");
                            }
                        }
                        if(transport.getCheckedRadioButtonId()!=-1)
                        {
                            button = findViewById(transport.getCheckedRadioButtonId());
                            schools = findSchool_control.filter_control.FilterByTransport(schools,button.getText().toString());
                        }
                        if(spark.getCheckedRadioButtonId()!=-1)
                        {
                            button = findViewById(spark.getCheckedRadioButtonId());
                            schools = findSchool_control.filter_control.FilterBySpark(schools,button.getText().toString());

                        }
                        if(language.getCheckedRadioButtonId()!=-1)
                        {
                            button = findViewById(language.getCheckedRadioButtonId());
                            schools = findSchool_control.filter_control.FilterByLanguage(schools,button.getText().toString());
                        }
                        if(operating_hour.getCheckedRadioButtonId()!=-1)
                        {
                            button = findViewById(operating_hour.getCheckedRadioButtonId());
                            schools = findSchool_control.filter_control.FilterByOperating(schools,button.getText().toString());
                        }
                        mMap.clear();
                        DisplayCurrentLocation(mMap,latLng,11f);
                        scrollView.setVisibility(View.INVISIBLE);
                        if(schools.size()>0)
                        {
                            Log.e("fitler school", String.valueOf(schools.size()));
                            DisplaySchool(mMap,schools);
                        }
                        else
                        {
                            DisplayError("We cannot find any schools that match your preference please reset your filters and try again");
                        }

                    }
                });


                // resetting the filter buttons
                Button reset = findViewById(R.id.Reset_Filter);
                reset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        operating_hour.clearCheck();
                        food.clearCheck();
                        transport.clearCheck();
                        spark.clearCheck();
                        language.clearCheck();
                    }
                });

            }
            else
            {
                DisplayError("Sorry it seems that there are leser than 3 schools that fit your child's age group so" +
                        "so you cannot filter it anymore");
            }
        }

    }


    public void DisplayCurrentLocation(GoogleMap googleMap, LatLng latLng1,float zoom)
    {
        MarkerOptions options = new MarkerOptions().position(latLng1).title("Current location");
        Marker marker = googleMap.addMarker(options);
        marker.setTag(null);
        float zoomLevel = 15.5f; //This goes up to 21
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng1, zoom));
    }


    public void DisplayError(String Message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View customLayout = LayoutInflater.from(MapsActivity.this).inflate(R.layout.noschoolfound, null);
        builder.setView(customLayout);
        builder.setTitle("OOPS, No School Found");
        TextView text = customLayout.findViewById(R.id.School_Error);
        text.setText(Message);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}