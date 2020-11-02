package com.example.edmad.firebasemvp.Vista.LoginView;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.edmad.firebasemvp.Presentador.LoginPresenter.PresentadorLogin;
import com.example.edmad.firebasemvp.R;
import com.example.edmad.firebasemvp.Vista.RegistroView.VistaRegistro;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VistaLogin extends AppCompatActivity implements View.OnClickListener {
    private EditText etEmail, etPassword;
    private PresentadorLogin presentadorLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_login);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        //trae la logica del presentador
        presentadorLogin = new PresentadorLogin(this, mAuth, mDatabase);

        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.pass);
        Button mBtnLogin = findViewById(R.id.btnIngresar);
        mBtnLogin.setOnClickListener(this);
        Button mBtnReg = findViewById(R.id.btnRegUserLogin);
        mBtnReg.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnIngresar:
                String email = etEmail.getText().toString().trim();
                String pass = etPassword.getText().toString().trim();
                presentadorLogin.logeandoUsuario(email, pass);
                break;

            case R.id.btnRegUserLogin:
                Intent intent = new Intent(VistaLogin.this, VistaRegistro.class);
                startActivity(intent);
                break;
        }
    }
}



