package com.example.edmad.firebasemvp.Adaptadores;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.edmad.firebasemvp.Modelo.ProductoModel;
import com.example.edmad.firebasemvp.R;
import com.example.edmad.firebasemvp.Vista.ProductoView.VistaProductoDetalle;

import java.util.ArrayList;

public class RecyclerProductAdapter extends RecyclerView.Adapter<RecyclerProductAdapter.ProductViewHolder> {

    private Context mContext;
    private int layoutResource;
    private ArrayList<ProductoModel> arrayListProductos;

    public RecyclerProductAdapter(Context mContext, int layoutResource, ArrayList<ProductoModel> arrayListProductos) {
        this.mContext = mContext;
        this.layoutResource = layoutResource;
        this.arrayListProductos = arrayListProductos;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(layoutResource, viewGroup, false);
        return new ProductViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int position) {
    ProductoModel productoModel = arrayListProductos.get(position);
    productViewHolder.mNombreProducto.setText("Nombre: "+productoModel.getNombreProducto());
    productViewHolder.mPrecioProducto.setText("Precio: $"+(int)productoModel.getPrecioProducto());
    productViewHolder.mImagenProducto.setImageResource(R.drawable.ic_image_blue_48dp);
    }

    @Override
    public int getItemCount() {
        if(arrayListProductos.size()>0){
            return arrayListProductos.size();
        }

        return 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mNombreProducto, mPrecioProducto;
        ImageView mImagenProducto;


    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        mNombreProducto = itemView.findViewById(R.id.nombreProductoRow);
        mPrecioProducto = itemView.findViewById(R.id.valorProductoRow);
        mImagenProducto = itemView.findViewById(R.id.imagenProductoRow);
        itemView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent in =new Intent(mContext, VistaProductoDetalle.class);
        mContext.startActivity(in);
    }
}

}

