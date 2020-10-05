package com.example.bookopoisk;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends Fragment implements View.OnClickListener {

    private ProgressBar progressBar; // ProgressBar
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
        progressBar = view.findViewById(R.id.progress_bar);

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
            progressBar.setVisibility(View.VISIBLE);
            btnRegistration.setVisibility(View.INVISIBLE);

            FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {  // Успешно зарегистрирован
                                progressBar.setVisibility(View.INVISIBLE);
                                btnRegistration.setVisibility(View.VISIBLE);

                                EditText etLogin = getActivity().findViewById(R.id.text_login);
                                etLogin.setText(email.getText().toString());

                                EditText etPassword = getActivity().findViewById(R.id.text_password);
                                etPassword.setText(password.getText().toString());

                                Toast.makeText(getContext(), "Успешно зарегистрирован.", Toast.LENGTH_SHORT).show();

                                SharedPreferences.Editor editor = Objects.requireNonNull(getActivity()).getSharedPreferences("auth", MODE_PRIVATE).edit();
                                String goodLogin = email.getText().toString().replace(".", "-");
                                editor.putString("login", goodLogin).apply();
                                getActivity().finish();

                                /*ViewPager pager = getActivity().findViewById(R.id.pager);
                                pager.setCurrentItem(0);*/
                            } else {
                                progressBar.setVisibility(View.INVISIBLE);
                                btnRegistration.setVisibility(View.VISIBLE);

                                Toast.makeText(getContext(), "Проверьте правильность введённых данных.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
