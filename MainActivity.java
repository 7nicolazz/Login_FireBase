package com.example.login_test;

import static com.example.login_test.Sesion.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    EditText etemail, etpassword;
    Button btn1, btn2,btn3;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        etemail = (EditText) findViewById(R.id.edtemail);
        etpassword = (EditText) findViewById(R.id.edtcontra);
        btn1 = (Button) findViewById(R.id.btnlogin);
        btn2 = (Button) findViewById(R.id.btnregistrar);
        btn3 = (Button) findViewById(R.id.login_button);

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),Sesion.class);
                startActivity(intent);

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegistrarUsuario();

            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IniciarSesion();

            }
        });


    }


    private void IniciarSesion() {

        String email=etemail.getText().toString().trim();
        String pass=etpassword.getText().toString().trim();
        //Validamos que los EditText no es vacios
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Se debe ingresar un Email",
                    Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Falta ingresar contraseña",
                    Toast.LENGTH_LONG).show();
            return;
        }
        //logearme (Inicio de Sesión)
        firebaseAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Bienvenido "+email+" a  Prueba de login", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(),Sesion.class);

                            intent.putExtra(user,email);
                            startActivity(intent);
                        }else{
                            if(task.getException()instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(MainActivity.this, "El Usuario registrado ya Existe", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Error de Email o Contraseña  Incorrecta", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }



    private void RegistrarUsuario() {

        String email=etemail.getText().toString().trim();
        String pass=etpassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Se debe ingresar un Email", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Falta ingresar contraseña", Toast.LENGTH_LONG).show();
            return;
        }
        //Creando a mi usuario
        firebaseAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "El Email se ha registrado Correctamente", Toast.LENGTH_LONG).show();
                        }else{ if(task.getException()instanceof FirebaseAuthUserCollisionException){
                            Toast.makeText(MainActivity.this, "El Email registrado ya Existe", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "No se puede registrar el Email", Toast.LENGTH_LONG).show();
                        }
                        }
                    }
                });
    }


}
