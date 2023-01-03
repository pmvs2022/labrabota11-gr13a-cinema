package com.ivanova.cinema.View.LoginView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ivanova.cinema.R;
import com.ivanova.cinema.View.CinemaListView.CinemaList;

public class Login extends AppCompatActivity implements FragmentNavigation {

    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        pref = getSharedPreferences("user_login", MODE_PRIVATE);
        if (pref.contains("login")) {
            startActivity(new Intent(Login.this, CinemaList.class));
        }

        getSupportFragmentManager().beginTransaction().add(R.id.login_container, new LoginFragment()).commit();
    }

    @Override
    public void navigateFragments(Fragment fragment, boolean toHomePage) {
        if (toHomePage) {
            startActivity(new Intent(Login.this, CinemaList.class));
        } else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().replace(R.id.login_container, fragment);
            transaction.commit();
        }
    }
}
