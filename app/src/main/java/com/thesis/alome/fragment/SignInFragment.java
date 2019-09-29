package com.thesis.alome.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.thesis.alome.R;
import com.thesis.alome.activity.MainActivity;

public class SignInFragment extends Fragment {

    Button btnSignIn;
    EditText edtEmail,edtPassword;
    TextView tvEmailError,tvPasswordError;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_in,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mapping(view);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateEmail() | !validatePassword()){
                    return;
                }else {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                tvEmailError.setText("");
            }
        });

        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                tvPasswordError.setText("");
            }
        });
    }
    private boolean validateEmail() {
        String email = edtEmail.getText().toString().trim();
        if (email.isEmpty()) {
            tvEmailError.setText(R.string.signup_page_validate_email_text);
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            tvEmailError.setText(R.string.signup_page_validate_pattern_email_text);
            return false ;
        }
        return true;
    }

    private boolean validatePassword(){
        String password = edtPassword.getText().toString().trim();
        if(password.isEmpty()){
            tvPasswordError.setText(R.string.signup_page_validate_empty_password_text);
            return false;
        }
        return true;
    }

    private void mapping(View view){
        btnSignIn = (Button) view.findViewById(R.id.btnSignIn);
        edtEmail = (EditText) view.findViewById(R.id.edtEmail);
        edtPassword = (EditText) view.findViewById(R.id.edtPassword);
        tvEmailError = (TextView) view.findViewById(R.id.tvEmailError);
        tvPasswordError = (TextView) view.findViewById(R.id.tvPasswordError);
    }

}
