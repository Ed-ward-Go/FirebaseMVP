package com.example.edmad.firebasemvp.Presentador.LoginPresenter;

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


public class PresentadorLogin {

    private Context mContext;
    private FirebaseAuth mAuth;
    private String TAG = "presentadorLogin";

    public PresentadorLogin(Context mContext, FirebaseAuth mAuth, DatabaseReference mDatabase) {
        this.mContext = mContext;
        this.mAuth = mAuth;
    }

public void logeandoUsuario(String email, String password){

    final ProgressDialog dialog = new ProgressDialog(mContext);
    dialog.setMessage("Ingresando....");
    dialog.setCancelable(false);
    dialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:Login exitoso");
                        Intent intent_vista_principal  = new Intent(mContext, VistaPrincipal.class);
                        mContext.startActivity(intent_vista_principal);
                        dialog.dismiss();


                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(mContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }

                    // ...
                }
            });

}

}
