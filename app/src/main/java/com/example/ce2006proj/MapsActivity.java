package com.example.ce2006proj;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

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
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        // add location ping

        // add the camera to the current postal code of the student then move the camera
        FindSchool_Control findSchool_control = new FindSchool_Control();
        findSchool_control.setContext(this);
        findSchool_control.coordinates(new PostalCallback() {
            @Override
            public void PostalCallBack(ArrayList<Double> codes) {
                LatLng position = new LatLng(codes.get(0),codes.get(1));
                mMap.addMarker(new MarkerOptions().position(position).title("Current location"));
                float zoomLevel = 15.5f; //This goes up to 21
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, zoomLevel));
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
                }
                else
                {
                    for(Schools schools: school)
                    {
                        // get the postal of each school
                        findSchool_control.coordinates(new PostalCallback() {
                            @Override
                            public void PostalCallBack(ArrayList<Double> codes) {
                                LatLng position = new LatLng(codes.get(0),codes.get(1));
                                mMap.addMarker(new MarkerOptions().position(position).title("Schools"));
                            }
                        },schools.getPostal_code());
                        Log.e("pos",schools.getPostal_code());
                    }
                    Log.e("as","SCHOOL WITH THE SERVIFCE IS FOUIND");
                }
            }

            });



    }

    public void DisplaySchools()
    {

    }







}