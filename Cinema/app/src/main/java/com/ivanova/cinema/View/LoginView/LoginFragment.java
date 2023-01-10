package com.ivanova.cinema.View.LoginView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;

import com.ivanova.cinema.Model.DBConnector;
import com.ivanova.cinema.R;

public class LoginFragment extends Fragment {

    private EditText login;
    private EditText password;

    private Button loginBtn;

    private DBConnector dbConnector;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        login = view.findViewById(R.id.log_login);
        password = view.findViewById(R.id.log_password);
        loginBtn = view.findViewById(R.id.btn_enter);

        view.findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentNavigation navRegister = (FragmentNavigation) getActivity();
                navRegister.navigateFragments(new RegisterFragment(), false);
            }
        });

        view.findViewById(R.id.btn_enter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateEmptyForm();
            }
        });

        dbConnector = new DBConnector(getActivity().getApplicationContext());
        return view;
    }

    private void validateEmptyForm() {
        Drawable icon = AppCompatResources.getDrawable(requireContext(), R.drawable.warning);
        icon.setBounds(0, 0, 60, 60);

        if (TextUtils.isEmpty(login.getText().toString().trim())) {
            login.setError(getString(R.string.enter_login), icon);
        } else if (TextUtils.isEmpty(password.getText().toString().trim())) {
            password.setError(getString(R.string.enter_password), icon);
        }

        if (!login.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
            if (login.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                if (password.getText().toString().length() >= 8) {
                    signIn();

                } else {
                    password.setError(getString(R.string.enter_valid_password), icon);
                }

            } else {
                login.setError(getString(R.string.enter_valid_email), icon);
            }
        }
    }

    private void signIn() {
        loginBtn.setEnabled(false);
        loginBtn.setAlpha(0.5f);

        String loginStr = login.getText().toString();
        String passwordStr = password.getText().toString();

        if (dbConnector.userExist(loginStr, passwordStr)) {
            FragmentNavigation navRegister = (FragmentNavigation) getActivity();
            navRegister.navigateFragments(null, true);
        } else {
            loginBtn.setEnabled(true);
            loginBtn.setAlpha(1.0f);
            Toast.makeText(getContext(), getString(R.string.not_valid_credentials), Toast.LENGTH_SHORT).show();
        }
    }
}