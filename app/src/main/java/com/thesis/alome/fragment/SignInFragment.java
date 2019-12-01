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
import android.widget.Toast;

import com.thesis.alome.R;
import com.thesis.alome.activity.MainActivity;
import com.thesis.alome.config.PrefUtils;
import com.thesis.alome.model.RespSignIn;
import com.thesis.alome.config.ApiClient;
import com.thesis.alome.config.ApiServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                if(!validateEmail(email) | !validatePassword(password)){
                    return;
                }else {
                    ApiServices apiServices1 = ApiClient.getAuthClient().create(ApiServices.class);
                    Call<RespSignIn> callLogin = apiServices1.signIn(email,password,"password","CUSTOMER");
                    callLogin.enqueue(new Callback<RespSignIn>() {
                        @Override
                        public void onResponse(Call<RespSignIn> callLogin, Response<RespSignIn> resp) {
                            if(resp.code() == 400){
                                Toast.makeText(getActivity(), getString(R.string.email_or_password_is_invalid), Toast.LENGTH_SHORT).show();
                            }else{
                                //Toast.makeText(getActivity(), getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                                PrefUtils.storeApiKey(getContext(),resp.body().getAccessToken());

                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);

                            }
                        }
                        @Override
                        public void onFailure(Call<RespSignIn> call, Throwable t) {
                            Toast.makeText(getActivity(), getString(R.string.please_check_the_internet), Toast.LENGTH_SHORT).show();
                        }
                    });
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
    private boolean validateEmail(String email) {

        if (email.isEmpty()) {
            tvEmailError.setText(R.string.validate_email_text);
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            tvEmailError.setText(R.string.validate_pattern_email_text);
            return false ;
        }
        return true;
    }

    private boolean validatePassword(String password){
        if(password.isEmpty()){
            tvPasswordError.setText(R.string.validate_empty_password_text);
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
