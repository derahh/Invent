package com.infiniteam.invent.akunActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.infiniteam.invent.R;

public class PilihRegistrasiActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_registrasi);

        Button btnSignUpUser = findViewById(R.id.btn_daftar_user);
        btnSignUpUser.setOnClickListener(this);

        Button btnSignUpEO = findViewById(R.id.btn_daftar_eo);
        btnSignUpEO.setOnClickListener(this);

        TextView tvSingIn = findViewById(R.id.tv_masuk);
        tvSingIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_daftar_user:
                Intent signUpUserIntent = new Intent(PilihRegistrasiActivity.this, RegistrasiUserActivity.class);
                startActivity(signUpUserIntent);
                break;
            case R.id.btn_daftar_eo:
                Intent signUpEOIntent = new Intent(PilihRegistrasiActivity.this, RegistrasiEOActivity.class);
                startActivity(signUpEOIntent);
                break;
            case R.id.tv_masuk:
                Intent signInIntent = new Intent(PilihRegistrasiActivity.this, LoginActivity.class);
                startActivity(signInIntent);
                break;
        }
    }
}
