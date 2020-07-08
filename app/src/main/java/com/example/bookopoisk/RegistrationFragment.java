package com.example.bookopoisk;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends Fragment implements View.OnClickListener {

    private EditText login;
    private EditText email;
    private TextInputLayout til_password;
    private EditText password;
    private Button btnRegistration;

    public RegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        login = view.findViewById(R.id.login);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        til_password = view.findViewById(R.id.til_password);
        btnRegistration = view.findViewById(R.id.button_reg);

        btnRegistration.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (login.length() == 0) {
            login.requestFocus();
            login.setError(getResources().getString(R.string.empty_login_error));
        } else if (email.length() == 0) {
            email.requestFocus();
            email.setError(getResources().getString(R.string.empty_email_error));
        } else if (password.length() < 8) {
            password.requestFocus();
            til_password.setError(getResources().getString(R.string.empty_password_error));
        } else {
            if(true) { // Успешно зарегистрирован
                EditText etLogin = getActivity().findViewById(R.id.text_login);
                etLogin.setText(login.getText().toString());

                EditText etPassword = getActivity().findViewById(R.id.text_password);
                etPassword.setText(password.getText().toString());

                Toast.makeText(getContext(),"Успешно зарегистрирован.", Toast.LENGTH_SHORT).show();

                ViewPager pager = getActivity().findViewById(R.id.pager);
                pager.setCurrentItem(0);
            } else {
                Toast.makeText(getContext(),"Проверьте правильность введённых данных.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
