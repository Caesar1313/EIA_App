package com.example.eia_app.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.eia_app.R;
import com.example.eia_app.config.AppSharedPreferences;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.auth_activity);
        AppSharedPreferences.initSharedPreferences(this);
        init();
    }

    private void init(){
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainerView, new SignupFragment()).commit();
    }
}
