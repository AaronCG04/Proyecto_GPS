package com.acgapp.proyecto_gps;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.location.LocationManagerCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.security.Permission;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private LocationManager adminloc;
    //private LocationListener escuchaloc;
    /*private Context context;
    TextView lonlat;
    String lon,lat;*/
    TextView lon,lat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*lonlat=(TextView) findViewById(R.id.lon_lat);
        validarPermisos();
        adminloc=(LocationManager) getSystemService(Context.LOCATION_SERVICE);*/
        lon=(TextView)findViewById(R.id.longitud);
        lat=(TextView)findViewById(R.id.latitud);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            localizar();
        }
    }
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1000){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                localizar();
            }
        }
    }
    private void localizar(){
        adminloc=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean GPSActivado=adminloc.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!GPSActivado){
            Toast.makeText(this, "Activa el GPS", Toast.LENGTH_SHORT).show();
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        adminloc.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000,1,this);
        adminloc.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1,this);
    }

///------------------------------------------------------------------
    @Override
    public void onLocationChanged(@NonNull Location location) {

        location.getLongitude();
        String latitud,longitud;
        latitud=String.valueOf(location.getLatitude());
        longitud=String.valueOf(location.getLongitude());
        lon.setText("Longitud: "+longitud);
        lat.setText("Latitud: "+latitud);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        switch (status) {
            case LocationProvider.AVAILABLE:
                Log.d("debug", "LocationProvider.AVAILABLE");
                break;
            case LocationProvider.OUT_OF_SERVICE:
                Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                break;
        }
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        lat.setText("GPS Activado");
        lon.setText("GPS Activado");
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        lat.setText("GPS Desactivado");
        lon.setText("GPS Desactivado");
    }
}