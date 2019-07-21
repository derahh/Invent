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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.infiniteam.invent.AkunUser;
import com.infiniteam.invent.R;

public class RegistrasiEOActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtUserName, edtEmail, edtPassword, edtToken;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi_eo);

        edtUserName = findViewById(R.id.edt_nama_pengguna);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_kata_sandi);
        //TODO validasi dengan token
        edtToken = findViewById(R.id.edt_token);

        Button btnSignUp = findViewById(R.id.btn_daftar_user);
        btnSignUp.setOnClickListener(this);

        TextView tvSignIn = findViewById(R.id.tv_masuk);
        tvSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_daftar_user){
            createAccount();
        }else if (v.getId() == R.id.tv_masuk){
            Intent signInIntent = new Intent(this, LoginActivity.class);
            startActivity(signInIntent);
        }
    }

    private void createAccount(){
        if (validate()) {
            final String userName = edtUserName.getText().toString();
            final String email = edtEmail.getText().toString();
            final String password = edtPassword.getText().toString();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        showMessage("Pembuatan akun");

                        AkunUser userAccount = new AkunUser(userName, email, password);
                        FirebaseDatabase.getInstance().getReference("EO").child(
                                FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(
                                userAccount).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    showMessage("Pendaftaran Berhasil");
                                }
                            }
                        });
                        Intent sigInInten = new Intent(RegistrasiEOActivity.this, LoginActivity.class);
                        startActivity(sigInInten);

                    }else {
                        showMessage("Pendaftaran Gagal");
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

        String userName = edtUserName.getText().toString();
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        if (userName.isEmpty()){
            edtUserName.setError("Masukkan Nama Pengguna");
            edtUserName.requestFocus();
            isValid = false;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtEmail.setError("Masukkan Email dengan benar");
            isValid = false;
        }else if (password.isEmpty()){
            edtPassword.setError("Masukkan Kata Sandi");
            if (password.length() > 5){
                isValid = true;
            }else {
                edtPassword.setError("Password harus lebih dari 5 huruf");
                isValid = false;
            }
        }else {
            isValid = true;
        }
        return isValid;
    }
}
