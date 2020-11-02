package com.example.edmad.firebasemvp.Maps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.edmad.firebasemvp.R;
import com.example.edmad.firebasemvp.Vista.PrincipalView.VistaPrincipal;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import java.util.Random;

public class MapsActivity2 extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    private int MY_PERMISSIONS_REQUEST_READ_CONTACTS;
    private FusedLocationProviderClient mFusedLocationClient;
    private static final Random rgenerador = new Random();
    private GoogleMap myMap;
    String url = "";
    private Button btnSatelite, btnTerreno, btnHybrido, btnSave;
    TextView txtlat,txtlon;
    double lat=0;
    double lon=0;
    private View popup = null;
    float[] colours = {
            BitmapDescriptorFactory.HUE_AZURE,
            BitmapDescriptorFactory.HUE_CYAN,
            BitmapDescriptorFactory.HUE_MAGENTA,
            BitmapDescriptorFactory.HUE_YELLOW,
            BitmapDescriptorFactory.HUE_BLUE,
            BitmapDescriptorFactory.HUE_GREEN,
            BitmapDescriptorFactory.HUE_ROSE,
            BitmapDescriptorFactory.HUE_RED,
            BitmapDescriptorFactory.HUE_ORANGE,
            BitmapDescriptorFactory.HUE_VIOLET
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        btnSave = findViewById(R.id.btnSave);
        btnSatelite = findViewById(R.id.btnSat);
        btnTerreno = findViewById(R.id.btnTerr);
        btnHybrido = findViewById(R.id.btnHybrid);
        txtlat = findViewById(R.id.tvLat);
        txtlon = findViewById(R.id.tvLon);
        btnSave.setOnClickListener(this);
        btnSatelite.setOnClickListener(this);
        btnTerreno.setOnClickListener(this);
        btnHybrido.setOnClickListener(this);
        capturarLatLon();
    }

    //-------------------------------FUSED_LOCATION_CLIENT
    private void capturarLatLon() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MapsActivity2.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);


            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {

                            Log.e("Latitud : ",+location.getLatitude()+" Longitud : "+location.getLongitude());
                           // Map<String,Object> latlon = new HashMap<>();

                            lat= location.getLatitude();
                            lon=location.getLongitude();

                           // mDatabase.child("ubicaciones").push().setValue(latlon);
                        }
                    }
                });
    }
//-------------------------------FUSED_LOCATION_CLIENT

// --------------------------------TERMINA CLASE ON_MAP_READY
    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;

        myMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @SuppressLint("ResourceType")
            @Override
            public View getInfoContents(Marker marker) {
                if (popup == null) {
                    popup = getLayoutInflater().inflate(R.layout.popup_map_info, null);
                }
                TextView tvTitle = popup.findViewById(R.id.title);
                tvTitle.setText(marker.getTitle());
                TextView tvSnippet = popup.findViewById(R.id.snippet);
                tvSnippet.setText(marker.getSnippet());

                //ImageView ivIcon = popup.findViewById(R.id.icon);
                // ivIcon.setImageBitmap(Bitmap.createBitmap());

                return (popup);
            }
        });

        /*final LatLng current = new LatLng(4.7110061, -74.0730037);
        myMap.addMarker(new MarkerOptions().position(current).title("Marker in Bogota"));
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 17));*/

     /*   myMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Marker marker = myMap.addMarker(new MarkerOptions()
                        .title("Punto de inicio")
                        .snippet("Latitud : "+lat+"\n"+"Longitud : "+lon+"")
                        .icon(BitmapDescriptorFactory.defaultMarker(colours[new Random().nextInt(colours.length)]))
                        .anchor(0.0f, 1.0f)
                        .position(latLng));

            }
        });*/

        /*myMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
                return false;
            }
        });*/
    }
// ------------------------------------------TERMINA CLASE ON_MAP_READY

    @Override
    public void onClick(View vi) {
        switch (vi.getId()) {
            case R.id.btnSat:
                myMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case R.id.btnTerr:
                myMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.btnHybrid:
                myMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            break;
                case R.id.btnSave:
                Intent i = new Intent(MapsActivity2.this, VistaPrincipal.class);
                Toast.makeText(getApplicationContext(), "LatLng capturada", Toast.LENGTH_SHORT).show();
                    i.putExtra("latitud",lat);
                    i.putExtra("longitud",lon);
                startActivity(i);
                default:
        }
    }

}
