package com.example.edmad.firebasemvp.Vista.RegistroView;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.edmad.firebasemvp.Presentador.RegistroPresenter.PresentadorRegistro;
import com.example.edmad.firebasemvp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VistaRegistro extends AppCompatActivity implements View.OnClickListener {
    private EditText etNombre, etUsuario, etEmail, etPass, etPassConfirm;
    private PresentadorRegistro presentadorRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_registro);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        presentadorRegistro = new PresentadorRegistro(this, mAuth, mDatabase);
        etNombre = findViewById(R.id.nombre);
        etUsuario = findViewById(R.id.nick);
        etEmail = findViewById(R.id.emai);
        etPass = findViewById(R.id.passw);
        etPassConfirm = findViewById(R.id.passwconfirm);
        Button RegBtn = findViewById(R.id.btnRegNueUser);
        RegBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegNueUser:
                String nombre = etNombre.getText().toString().trim();
                String usuario = etUsuario.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String pass = etPass.getText().toString().trim();
                String passconf = etPassConfirm.getText().toString().trim();
                if(pass.equals(passconf)){
                    presentadorRegistro.RegistroUser(email,pass,usuario,nombre);
                }else{
                    Toast.makeText(this,"Las contraseñas no son iguales", Toast.LENGTH_SHORT).show();
                }

                break;

        }
    }
}
