package com.example.edmad.firebasemvp.Presentador.RegistroPresenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.edmad.firebasemvp.Vista.PrincipalView.VistaPrincipal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

public class PresentadorRegistro {

    private Context mContext;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String TAG = "PresentadorRegistro";

    public PresentadorRegistro(Context mContext, FirebaseAuth mAuth, DatabaseReference mDatabase) {
        this.mContext = mContext;
        this.mAuth = mAuth;
        this.mDatabase = mDatabase;
    }

    public void RegistroUser(final String email, String password, final String nombre, final String nick){
        final ProgressDialog dialog = new ProgressDialog(mContext);
        dialog.setMessage("Registrando usuario");
        dialog.setCancelable(false);
        dialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            dialog.dismiss();
                            Map<String,Object> crearUsuario = new HashMap<>();
                            crearUsuario.put("nombre",nombre);
                            crearUsuario.put("nick",nick);
                            crearUsuario.put("email",email);
                            mDatabase.child("Usuarios").child(task.getResult().getUser().getUid()).updateChildren(crearUsuario);
                            Intent intt = new Intent(mContext, VistaPrincipal.class);
                            mContext.startActivity(intt);

                        } else {
                            dialog.dismiss();
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(mContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
}
