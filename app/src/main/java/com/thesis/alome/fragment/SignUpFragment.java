package com.thesis.alome.fragment;

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
import android.widget.Toast;

import com.thesis.alome.R;
import com.thesis.alome.activity.SignInSignUpActivity;
import com.thesis.alome.config.ApiServices;
import com.thesis.alome.config.ApiClient;
import com.thesis.alome.dao.ReqSignUp;
import com.thesis.alome.dao.RespBase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFragment extends Fragment {

    Button btnSignUp;
    EditText edtFullName,edtEmail,edtPassword,edtConfirmPassword;
    TextView tvFullNameError,tvEmailError,tvPasswordError,tvConfirmPasswordError;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragnment_sign_up,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mapping(view);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullName = edtFullName.getText().toString().trim();
                final String email = edtEmail.getText().toString().trim();
                final String password = edtPassword.getText().toString().trim();
                String passConfirm = edtConfirmPassword.getText().toString().trim();

                if(!validateFullName(fullName) | !validateEmail(email)
                        | !validatePassword(password) | !validatePassConfirm(passConfirm,password)){
                    return;
                }else{
                    ApiServices apiServices = ApiClient.getClient(getContext()).create(ApiServices.class);
                    Call<RespBase> callSignup = apiServices.signUp(new ReqSignUp(email,password));
                    callSignup.enqueue(new Callback<RespBase>() {
                        @Override
                        public void onResponse(Call<RespBase> call, Response<RespBase> response) {

                            if(response.body().getStatus() == true && response.body().getMessage() == null){
                                Toast.makeText(getActivity(), "Register success", Toast.LENGTH_SHORT).show();
                                ((SignInSignUpActivity) getActivity()).switchSignInFragment();
                            }else {
                                tvEmailError.setText("Email is already registered");
                            }
                        }
                        @Override
                        public void onFailure(Call<RespBase> call, Throwable t) {
                            Toast.makeText(getActivity(), "Please check the internet", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

        edtFullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                tvFullNameError.setText("");
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

        edtConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                tvConfirmPasswordError.setText("");
            }
        });
    }

    private boolean validateFullName(String fullName){

        if(fullName.isEmpty()){
            tvFullNameError.setText(R.string.signup_page_validate_fullname_text);
            return false;
        }
        return true;
    }

    private boolean validateEmail(String email) {

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

    private boolean validatePassword(String password){

        if(password.isEmpty()){
            tvPasswordError.setText(R.string.signup_page_validate_empty_password_text);
            return false;
        }
        if(password.length() < 6 ){
            tvPasswordError.setText(R.string.signup_page_validate_password_text);
            return false;
        }
        return true;
    }

    private boolean validatePassConfirm(String passConfirm, String pass){

        if(passConfirm.isEmpty()){
            tvConfirmPasswordError.setText(R.string.signup_page_validate_empty_confirm_password_text);
            return false;
        }
        if(!passConfirm.equals(pass)){
            tvConfirmPasswordError.setText(R.string.signup_page_validate_confirm_password_text);
            return false;
        }
        return true;
    }

    private void mapping(View view){
        btnSignUp = (Button) view.findViewById(R.id.btnSignUp);
        edtFullName = (EditText) view.findViewById(R.id.edtFullName);
        edtEmail = (EditText) view.findViewById(R.id.edtEmail);
        edtPassword = (EditText) view.findViewById(R.id.edtPassword);
        edtConfirmPassword = (EditText) view.findViewById(R.id.edtConfirmPassword);
        tvFullNameError= (TextView) view.findViewById(R.id.tvFullNameError);
        tvEmailError = (TextView) view.findViewById(R.id.tvEmailError);
        tvPasswordError = (TextView) view.findViewById(R.id.tvPasswordError);
        tvConfirmPasswordError = (TextView) view.findViewById(R.id.tvConfirmPasswordError);
    }
}
