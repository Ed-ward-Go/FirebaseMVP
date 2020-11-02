package com.example.edmad.firebasemvp.Maps;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.example.edmad.firebasemvp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double lat = 0;
    double lon = 0;
    String locacion = "";
    String ubicacion = "";
    String url = "";
    private Button mTypeBtn;
    private View popup = null;
    Bitmap bitmap;
    ImageView img;
    String URLIMAGE = "https://firebasestorage.googleapis.com/v0/b/fir-mvp-9f9a5.appspot.com/o/Fotos%2F7r4HQhbVdeO5UTWv1ZviSWQni2C2%2Fimage%3A5628?alt=media&token=9768295e-a84a-488f-80e2-d04bfdcd440b";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

       mTypeBtn = findViewById(R.id.btnSatell);

       mTypeBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
        });

        /*img = findViewById(R.id.icon);
        new getImageFromUrl(img).execute(URLIMAGE);*/
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
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


        lat = getIntent().getDoubleExtra("latitud", lat);
        lon = getIntent().getDoubleExtra("longitud", lon);
        locacion = getIntent().getStringExtra("locacion");
        ubicacion = getIntent().getStringExtra("ubicacion");
        url = getIntent().getStringExtra("url");
        Log.e("lat :", String.valueOf((lat)));

        LatLng coord = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().draggable(true).position(coord).title(locacion).snippet(ubicacion+"\n"+" latitud : "+lat+"\n"+" Longitud :"+lon));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coord, 17));
    }
   /* public class getImageFromUrl extends AsyncTask<String, Void, Bitmap> {
        ImageView imgV;

        public getImageFromUrl(ImageView imgV) {
            this.imgV = imgV;
        }

        @Override
        protected Bitmap doInBackground(String... url) {
            String urldisplay = url[0];
            bitmap = null;
            try {
                InputStream srt = new java.net.URL(urldisplay).openStream();
                bitmap = BitmapFactory.decodeStream(srt);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imgV.setImageBitmap(bitmap);
        }
    }*/

}

