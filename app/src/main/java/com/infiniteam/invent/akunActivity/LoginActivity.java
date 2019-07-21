package com.infiniteam.invent.akunActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.infiniteam.invent.MainActivity;
import com.infiniteam.invent.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtEmail, edtPassword;

    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();

        //TODO login pake email untuk sementara
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_kata_sandi);

        Button btnSignIn = findViewById(R.id.btn_masuk);
        btnSignIn.setOnClickListener(this);

        TextView tvSignUp = findViewById(R.id.tv_daftar);
        tvSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_masuk:
                signIn();
                break;
            case R.id.tv_daftar:
                Intent daftarIntent = new Intent(LoginActivity.this, PilihRegistrasiActivity.class);
                startActivity(daftarIntent);
        }
    }

    //Login akun
    private void signIn(){
        if (validate()) {
            String email = edtEmail.getText().toString();
            String password = edtPassword.getText().toString();

            mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        String info = mFirebaseAuth.getCurrentUser().getUid();
                        FirebaseDatabase.getInstance().getReference("users").child(info).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            });
        }
    }

    //untuk menampilkan pesan
    private void showMessage(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    //untuk memvalidasi peenginputan
    private boolean validate(){
        boolean isValid;

        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        if  (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtEmail.setError("Masukkan Email dengan benar");
            isValid = false;
        }else if (password.isEmpty()){
            edtPassword.setError("Masukkan Kata Sandi");
            isValid = false;
        }else {
            isValid = true;
        }

        return isValid;
    }
}
