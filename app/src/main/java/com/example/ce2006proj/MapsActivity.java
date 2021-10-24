package com.example.ce2006proj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import android.content.DialogInterface;
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
import android.widget.RelativeLayout;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.WeakHashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private FindSchool_Control findSchool_control = new FindSchool_Control();
    private LatLng latLng;
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
                MarkerOptions options = new MarkerOptions().position(latLng).title("Current location");
                Marker marker = googleMap.addMarker(options);
                marker.setTag(null);
                float zoomLevel = 15.5f; //This goes up to 21
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
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
        Handler handler = new Handler();
        for(Schools schools: schoolsArrayList)
        {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    findSchool_control.coordinates(new PostalCallback() {
                        @Override
                        public void PostalCallBack(ArrayList<Double> codes) {
                            LatLng position = new LatLng(codes.get(0),codes.get(1));//
                            MarkerOptions options = new MarkerOptions().position(position).title(schools.getCentre_name());
                            Marker marker = googleMap.addMarker(options);
                            marker.setTag(schools);
                        }
                    },schools.getPostal_code());
                }
            },1500);
            // get the postal of each school

        }
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
        }

    }


    public void Filter(View view)
    {
        // make the layout visible,
//        LayoutInflater inflator=getLayoutInflater();
//        View layoutRight =  inflator.inflate(
//                R.layout.filter_layout, null);
//        LinearLayout linearLayout = layoutRight.findViewById(R.id.filter_layout);
//        linearLayout.setVisibility(View.VISIBLE);
//        Log.e("asd","helo there");
        LinearLayout linearLayout = findViewById(R.id.filter_layout);
        if(linearLayout.getVisibility()==View.VISIBLE)
        {
            linearLayout.setVisibility(View.INVISIBLE);
        }
        else
        {
            linearLayout.setVisibility(View.VISIBLE);
            // once they click on the button to filter schools then we call the functiion
        }

    }
}