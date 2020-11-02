package com.example.edmad.firebasemvp.Vista.ProductoView;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.edmad.firebasemvp.Maps.MapsActivity;
import com.example.edmad.firebasemvp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class VistaLocacionDetalle extends AppCompatActivity implements View.OnClickListener {
    DatabaseReference mDatabase;
    private ImageView imgDet;
    double lat = 0;
    double lon = 0;
    String locacion = "";
    String ubicacion = "";
    String url = "";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_locacion_detalle);

        TextView mTvLocacion = findViewById(R.id.tvLocacion);
        //TextView mTvUbicacion = findViewById(R.id.tvUbicacion);
        TextView mTvUrl = findViewById(R.id.tvUrl);
        Button mIbtnMap = findViewById(R.id.ibtnMap);
        TextView mTvLat = findViewById(R.id.tvLat);
        TextView mTvLon = findViewById(R.id.tvLon);
        mIbtnMap.setOnClickListener(this);

        //lat 12,583024, lon -81.68967612-> 3 casitas

        mDatabase = FirebaseDatabase.getInstance().getReference("post").child("key-125");
        imgDet = findViewById(R.id.imageDetalle);

        Bundle xtras = getIntent().getExtras();
        if (xtras != null) {

            locacion = xtras.getString("locacion");
            ubicacion = xtras.getString("ubicacion");
            lat = xtras.getDouble("latitud");
            lon = xtras.getDouble("longitud");
            url = xtras.getString("imagen");
        }

        mTvUrl.setText(url);
        mTvLocacion.setText("Locación  : "+locacion+"\n"+"Ubicación  : "+ubicacion);
        //mTvUbicacion.setText(ubicacion);
        mTvLat.setText(String.valueOf(lat));
        mTvLon.setText(String.valueOf(lon));

        Glide.with(this)
                .load(url)
                .into(imgDet);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtnMap:
                Intent map = new Intent(this, MapsActivity.class);
                map.putExtra("latitud",lat );
                map.putExtra("longitud",lon);
                map.putExtra("locacion",locacion);
                map.putExtra("ubicacion",ubicacion);
                map.putExtra("url",url);
                startActivity(map);
            default:
        }
    }

    private void setLikesDb(DatabaseReference mDatabase) {
        for (int i = 0; i < 100; i++) {
            Map<String, Object> mapUsuario = new HashMap<>();
            mapUsuario.put("Usuario" + i, true);
            mDatabase.child("likes").updateChildren(mapUsuario);
        }
    }

}

