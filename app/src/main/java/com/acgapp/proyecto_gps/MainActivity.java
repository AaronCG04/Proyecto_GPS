package com.acgapp.proyecto_gps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {
    TextView lon,lat;
    SQLiteDatabase db_escribir;
    SQLiteDatabase db_leer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lon=findViewById(R.id.longitud);
        lat=findViewById(R.id.latitud);
        Base_de_Datos db_crear=new Base_de_Datos(this);
        db_escribir=db_crear.getWritableDatabase();
        db_leer=db_crear.getReadableDatabase();
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
        LocationManager adminloc = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean GPSActivado= adminloc.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!GPSActivado){
            Toast.makeText(this, "Activa el GPS", Toast.LENGTH_SHORT).show();
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        adminloc.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,10000,10,this);
        adminloc.requestLocationUpdates(LocationManager.GPS_PROVIDER,10000,10,this);
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
        Insertar_Datos("2018150480195",latitud,longitud,optener_fecha());

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
    public void Insertar_Datos(String num_control,String latitud,String longitud,String fecha){
        if (db_escribir!=null){
            Toast.makeText(this, "Se Creo Base de Datos", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "No se Creo Base de Datos", Toast.LENGTH_SHORT).show();
        }
        ContentValues datos_sep = new ContentValues();
        datos_sep.put("num_control",num_control);
        datos_sep.put("latitud",latitud);
        datos_sep.put("longitud",longitud);
        datos_sep.put("fecha",fecha);
        db_escribir.insert("cordenadas",null,datos_sep);
    }
    public String optener_fecha(){
        String fecha;
        SimpleDateFormat formato=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        Date d=new Date();
        fecha= formato.format(d);
        System.out.print(fecha);
        Toast.makeText(this, "Se Optubo Fecha", Toast.LENGTH_SHORT).show();
        return fecha;
    }
}