package com.example.edmad.firebasemvp.Adaptadores;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.edmad.firebasemvp.Entidades.LocacionEntity;
import com.example.edmad.firebasemvp.R;

import java.util.ArrayList;

public class RecyclerLocacionAdapter extends RecyclerView.Adapter<RecyclerLocacionAdapter.LocacionViewHolder>
        implements View.OnClickListener {

    private Context mContext;
    private int layoutResource;
    private ArrayList<LocacionEntity> arrayListLocacion;
    private View.OnClickListener listener;

    public RecyclerLocacionAdapter(Context mContext, int layoutResource, ArrayList<LocacionEntity> arrayListLocaciones) {
        this.mContext = mContext;
        this.layoutResource = layoutResource;
        this.arrayListLocacion = arrayListLocaciones;
    }

    @NonNull
    @Override
    public LocacionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(layoutResource, viewGroup, false);
        view.setOnClickListener(this);
        return new LocacionViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final LocacionViewHolder locacionViewHolder, int position) {
        final LocacionEntity locacionModel = arrayListLocacion.get(position);
        locacionViewHolder.mNombreLocacion.setText("Locación :  " + locacionModel.getNombreLocacion());
        locacionViewHolder.mUbicacion.setText("Ubicación :  " + locacionModel.getUbicacion());
        locacionViewHolder.mlatLocacionRow.setText("Latitud : " + locacionModel.getLatitud());
        locacionViewHolder.mlonLocacionRow.setText("Longitud : " + locacionModel.getLongitud());
        locacionViewHolder.mUrl.setText("imagen :  " + locacionModel.getImagen());

        //Cargar foto firebase con Glide
        Glide
                .with(mContext)
                .load(locacionModel.getImagen())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        locacionViewHolder.mProgressBar.setVisibility(View.GONE);
                        locacionViewHolder.mImagenLocacion.setVisibility(View.VISIBLE);
                        locacionViewHolder.mImagenLocacion.setImageResource(R.drawable.ic_error_outline_black_48dp);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        locacionViewHolder.mProgressBar.setVisibility(View.GONE);
                        locacionViewHolder.mImagenLocacion.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                .into(locacionViewHolder.mImagenLocacion);

        //productViewHolder.mImagenProducto.setImageResource(R.drawable.ic_image_blue_48dp);
    }

    @Override
    public int getItemCount() {
        if (arrayListLocacion.size() > 0) {
            return arrayListLocacion.size();
        }

        return 0;
    }

    public void setOnClickEscuchador(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);

        }

    }

    public class LocacionViewHolder extends RecyclerView.ViewHolder {
        private View.OnClickListener listener;

        TextView mNombreLocacion, mUbicacion, mlatLocacionRow, mlonLocacionRow, mUrl;
        ImageView mImagenLocacion;
        ProgressBar mProgressBar;


        public LocacionViewHolder(@NonNull View itemView) {
            super(itemView);

            mNombreLocacion = itemView.findViewById(R.id.nombreLocacionRow);
            mUbicacion = itemView.findViewById(R.id.UbicacionRow);
            mImagenLocacion = itemView.findViewById(R.id.imagenLocacionRow);
            mlatLocacionRow = itemView.findViewById(R.id.latLocacionRow);
            mlonLocacionRow = itemView.findViewById(R.id.lonLocacionRow);
            mProgressBar = itemView.findViewById(R.id.progress);
            mUrl = itemView.findViewById(R.id.urlLocacionRow);

        }

    }

}

