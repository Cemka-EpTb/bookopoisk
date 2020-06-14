package com.example.bookopoisk;

import android.content.Intent;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class AuthorizationFragment extends Fragment implements OnClickListener {

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
        View view = inflater.inflate(R.layout.fragment_authorization, container, false);
        username = view.findViewById(R.id.text_login);
        password = view.findViewById(R.id.text_password);
        btnLogin = view.findViewById(R.id.login_button);
        forgotPassword = view.findViewById(R.id.forgot_password);

        btnLogin.setOnClickListener(AuthorizationFragment.this);
        forgotPassword.setOnClickListener(AuthorizationFragment.this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:

                break;
            case R.id.forgot_password:
                Uri address = Uri.parse("http://192.168.12.12/password/reset");
                Intent intent = new Intent(Intent.ACTION_VIEW, address);
                startActivity(intent);
                break;
        }
    }
}
