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

public class RegisterFragment extends Fragment {

    private EditText login;
    private EditText password;
    private EditText confirmPassword;

    private Button registerBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        login = view.findViewById(R.id.reg_login);
        password = view.findViewById(R.id.reg_password);
        confirmPassword = view.findViewById(R.id.reg_confirmPassword);
        registerBtn = view.findViewById(R.id.btn_register_reg);

        view.findViewById(R.id.btn_enter_reg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentNavigation navRegister = (FragmentNavigation) getActivity();
                navRegister.navigateFragments(new LoginFragment(), false);
            }
        });

        view.findViewById(R.id.btn_register_reg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateEmptyForm();
            }
        });

        return view;
    }

    private void validateEmptyForm() {
        Drawable icon = AppCompatResources.getDrawable(requireContext(), R.drawable.warning);
        icon.setBounds(0, 0, 60, 60);

        if (TextUtils.isEmpty(login.getText().toString().trim())) {
            login.setError(getString(R.string.enter_login), icon);
        } else if (TextUtils.isEmpty(password.getText().toString().trim())) {
            password.setError(getString(R.string.enter_password), icon);
        } else if (TextUtils.isEmpty(confirmPassword.getText().toString().trim())) {
            confirmPassword.setError(getString(R.string.confirm_password), icon);
        }

        if (!login.getText().toString().isEmpty() && !password.getText().toString().isEmpty() && !confirmPassword.getText().toString().isEmpty()) {
            if (login.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                if (password.getText().toString().length() >= 8) {
                    if (password.getText().toString().equals(confirmPassword.getText().toString())) {
                        signUp();

                    } else {
                        confirmPassword.setError(getString(R.string.passwords_do_not_match), icon);
                    }

                } else {
                    password.setError(getString(R.string.enter_valid_password), icon);
                }

            } else {
                login.setError(getString(R.string.enter_valid_email), icon);
            }
        }
    }

    private void signUp() {
        registerBtn.setEnabled(false);
        registerBtn.setAlpha(0.5f);

        String loginStr = login.getText().toString();
        String passwordStr = password.getText().toString();

        if (DBConnector.registerUser(loginStr, passwordStr)) {
            FragmentNavigation navRegister = (FragmentNavigation) getActivity();
            navRegister.navigateFragments(null, true);
        } else {
            registerBtn.setEnabled(true);
            registerBtn.setAlpha(1.0f);
            Toast.makeText(getContext(), getString(R.string.not_valid_data), Toast.LENGTH_SHORT).show();
        }
    }
}