package com.example.bookopoisk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class AuthorizationFragment extends Fragment implements OnClickListener {

    View view;
    private EditText username;
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
        username = view.findViewById(R.id.text_login);
        password = view.findViewById(R.id.text_password);
        btnLogin = view.findViewById(R.id.login_button);
        forgotPassword = view.findViewById(R.id.forgot_password);

        btnLogin.setOnClickListener(AuthorizationFragment.this);
        forgotPassword.setOnClickListener(AuthorizationFragment.this);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String login = bundle.getString("login");
            String bundle_password = bundle.getString("password");
            username.setText(login);
            password.setText(bundle_password);
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                if (username.length() == 0){
                    username.requestFocus();
                    username.setError(getResources().getString(R.string.empty_login_error));
                } else if (password.length() == 0) {
                    TextInputLayout til_password = view.findViewById(R.id.til_password);
                    password.requestFocus();
                    til_password.setError(getResources().getString(R.string.empty_password_error));
                } else if (true) { // Верные логин и пароль
                    SharedPreferences.Editor editor = Objects.requireNonNull(getActivity()).getSharedPreferences("auth",MODE_PRIVATE).edit();
                    editor.putString("login", username.getText().toString()).apply();
                    getActivity().finish();
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
