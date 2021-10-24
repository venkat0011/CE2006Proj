package com.example.ce2006proj;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.model.Marker;

import org.w3c.dom.Text;

public class SchoolPinAdapter implements GoogleMap.InfoWindowAdapter {
    private Context context;
    private final View Window;

    public SchoolPinAdapter(Context context)
    {
        this.context = context;
        Window = LayoutInflater.from(context).inflate(R.layout.school_info,null);

    }

    public void renderWindowText(Marker marker, View view)
    {
        String title = marker.getTitle();
        TextView textView = view.findViewById(R.id.title);
        textView.setText(title);

        if(!marker.getTitle().equals("Current location"))
        {
            Schools schools = (Schools) marker.getTag();
            TextView textView1 = view.findViewById(R.id.Address);
            textView1.setText("Address: " + schools.getCentre_address());

            TextView textView2 = view.findViewById(R.id.Operating_Hour);
            textView2.setText("Weekday: "+ schools.getWeekday_full_day() +"\n"
                                +"Saturday: " +schools.getSaturday());
            Button button = view.findViewById(R.id.more_info);
            button.setTextColor(Color.BLUE);

        }
    }




    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        renderWindowText(marker,Window);
        return Window;
    }


    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        renderWindowText(marker,Window);
        return Window;
    }


}
