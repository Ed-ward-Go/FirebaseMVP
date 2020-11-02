package com.example.edmad.firebasemvp.Vista.PrincipalView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.edmad.firebasemvp.Presentador.PrincipalPresenter.PresenterPrincipal;
import com.example.edmad.firebasemvp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;


public class VistaPrincipal extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    private PresenterPrincipal presenterPrincipal;
    private Dialog dialog;
    private EditText nombreProd, precioProd;
    private ProgressDialog progressDialog;
    private final int PICK = 1;
    private ImageView imagenProducto;
    private Uri filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_principal);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        FloatingActionButton mfab = findViewById(R.id.fab);
        mfab.setOnClickListener(this);
        presenterPrincipal = new PresenterPrincipal(this, mDatabase, mAuth, mStorageRef);
        presenterPrincipal.welcomeMessage();
        initRecycler();
    }

    private void initRecycler() {
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        presenterPrincipal.cargarRecyclerView(mRecyclerView);

    }

    public void cargarProducto() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_row);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        nombreProd = dialog.findViewById(R.id.etNombreProducto);
        precioProd = dialog.findViewById(R.id.etPrecioProducto);
        Button cargarProd = dialog.findViewById(R.id.btnAddProd);
        ImageView cargarFoto = dialog.findViewById(R.id.imageViewProducto);
        imagenProducto = dialog.findViewById(R.id.imageViewProducto);
        cargarProd.setOnClickListener(this);
        cargarFoto.setOnClickListener(this);
        dialog.show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                cargarProducto();
                break;
            case R.id.btnAddProd:
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Añadiendo producto...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                String nombreProducto = nombreProd.getText().toString().trim();
                float precioProducto = Float.parseFloat(precioProd.getText().toString().trim());
                presenterPrincipal. cargarProductoFirebase(nombreProducto, precioProducto, dialog, progressDialog, filePath);
                break;
            case R.id.imageViewProducto:
                cargarFotoGaleria();
                break;
        }
    }

    private void cargarFotoGaleria() {
        Intent inten = new Intent();
        inten.setType("image/*");
        inten.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(inten, "Seleccione imagen"), PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    if(requestCode == PICK && resultCode == RESULT_OK && data != null && data.getData() != null);
         filePath = data.getData();
        try {
            Bitmap bitmapImagen = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
        imagenProducto.setImageBitmap(bitmapImagen);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
