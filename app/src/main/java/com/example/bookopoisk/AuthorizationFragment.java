package com.example.bookopoisk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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
public class AuthorizationFragment extends Fragment implements OnClickListener {

    private ProgressBar progressBar; // ProgressBar
    View view;
    private EditText login;
    private EditText password;
    private Button btnLogin;
    private TextView forgotPassword;

    public AuthorizationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_authorization, container, false);
        login = view.findViewById(R.id.text_login);
        password = view.findViewById(R.id.text_password);
        btnLogin = view.findViewById(R.id.login_button);
        forgotPassword = view.findViewById(R.id.forgot_password);
        progressBar = view.findViewById(R.id.progress_bar);

        btnLogin.setOnClickListener(AuthorizationFragment.this);
        forgotPassword.setOnClickListener(AuthorizationFragment.this);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String email = bundle.getString("login");
            String bundle_password = bundle.getString("password");
            login.setText(email);
            password.setText(bundle_password);
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                if (login.length() == 0) {
                    login.requestFocus();
                    login.setError(getResources().getString(R.string.empty_login_error));
                } else if (password.length() == 0) {
                    TextInputLayout til_password = view.findViewById(R.id.til_password);
                    password.requestFocus();
                    til_password.setError(getResources().getString(R.string.empty_password_error));
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    btnLogin.setVisibility(View.INVISIBLE);

                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    if (auth.getCurrentUser() != null) {
                        progressBar.setVisibility(View.INVISIBLE);
                        btnLogin.setVisibility(View.VISIBLE);

                        SharedPreferences.Editor editor = Objects.requireNonNull(getActivity()).getSharedPreferences("auth", MODE_PRIVATE).edit();
                        editor.putString("login", login.getText().toString()).apply();

                        Toast.makeText(getContext(), "Вход в аккаунт уже выполнен.", Toast.LENGTH_SHORT).show();
                    } else {
                        auth.signInWithEmailAndPassword(login.getText().toString(), password.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            progressBar.setVisibility(View.INVISIBLE);
                                            btnLogin.setVisibility(View.VISIBLE);

                                            SharedPreferences.Editor editor = Objects.requireNonNull(getActivity()).getSharedPreferences("auth", MODE_PRIVATE).edit();
                                            String goodLogin = login.getText().toString().replace(".", "-");
                                            editor.putString("login", goodLogin).apply();
                                            getActivity().finish();
                                        } else {
                                            progressBar.setVisibility(View.INVISIBLE);
                                            btnLogin.setVisibility(View.VISIBLE);

                                            Toast.makeText(getContext(), "Проверьте правильность введённых данных.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
                break;
            case R.id.forgot_password:
                Uri address = Uri.parse("http://192.168.12.12/password/reset");
                Intent intent = new Intent(Intent.ACTION_VIEW, address);
                startActivity(intent);
                break;
        }
    }
}
