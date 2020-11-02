package com.example.edmad.firebasemvp.Presentador.PrincipalPresenter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Toast;

import com.example.edmad.firebasemvp.Adaptadores.RecyclerProductAdapter;
import com.example.edmad.firebasemvp.Modelo.ProductoModel;
import com.example.edmad.firebasemvp.Modelo.UserModel;
import com.example.edmad.firebasemvp.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PresenterPrincipal {
    private Context mContext;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private RecyclerProductAdapter mAdapter;
    private StorageReference mStorageRef;



    public PresenterPrincipal(Context mContext, DatabaseReference mDatabase, FirebaseAuth mAuth, StorageReference mStorageRef) {
        this.mContext = mContext;
        this.mDatabase = mDatabase;
        this.mAuth = mAuth;
        this.mStorageRef = mStorageRef;
    }

    public void welcomeMessage() {

        mDatabase.child("Usuarios").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel usermodel = dataSnapshot.getValue(UserModel.class);
                Toast.makeText(mContext, "Bienvenido " + usermodel.getUsuario(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void cargarRecyclerView(final RecyclerView mRecyclerView){

        mDatabase.child("Usuarios").child(mAuth.getCurrentUser().getUid()).child("Productos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<ProductoModel> arrayListProductos = new ArrayList<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ProductoModel productoModel = snapshot.getValue(ProductoModel.class);

                    String nombreProd = productoModel.getNombreProducto();
                    float precioProd = productoModel.getPrecioProducto();
                    productoModel.setNombreProducto(nombreProd);
                    productoModel.setPrecioProducto(precioProd);

                    arrayListProductos.add(productoModel);
                }
            mAdapter = new RecyclerProductAdapter(mContext,R.layout.producto_row,arrayListProductos);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void cargarProductoFirebase(final String nombreProducto, final float precioProducto, final Dialog dialog, final ProgressDialog progressDialog, Uri filePath) {

        if(filePath != null){
            final StorageReference fotoRef = mStorageRef.child("Fotos").child(mAuth.getCurrentUser().getUid()).child(filePath.getLastPathSegment());
            fotoRef.putFile(filePath).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                  if(!task.isSuccessful()){
                      throw new Exception();
                  }

                    return fotoRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri downloadLink = task.getResult();
                        Map<String, Object> producto = new HashMap<>();
                        producto.put("nombreProducto", nombreProducto);
                        producto.put("precioProducto", precioProducto);
                        producto.put("imagen",downloadLink.toString());
                        mDatabase.child("Usuarios").child(mAuth.getCurrentUser().getUid()).child("Productos").push().updateChildren(producto).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                dialog.dismiss();
                                progressDialog.dismiss();
                                Toast.makeText(mContext,"Se cargo el producto correctamente", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(mContext,"Error al cargar producto"+e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }



    }
}

