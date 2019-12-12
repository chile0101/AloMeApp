package com.thesis.alome.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.thesis.alome.R;
import com.thesis.alome.activity.SignInSignUpActivity;
import com.thesis.alome.config.ApiServices;
import com.thesis.alome.config.ApiClient;
import com.thesis.alome.model.ReqSignUp;
import com.thesis.alome.model.RespBase;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class SignUpFragment extends Fragment {

    Button btnSignUp;
    EditText edtFullName,edtEmail,edtPassword, edtPhone;
    TextView tvFullNameError,tvEmailError,tvPasswordError,tvConfirmPasswordError, tvPhoneError, tvAddress;
    private String latitude, longitude;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mapping(view);

        tvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Places.isInitialized()) {
                    Places.initialize(getContext().getApplicationContext(), getString(R.string.google_maps_key_active));
                }

                // Set the fields to specify which types of place data to return.
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);

                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields)
                        .build(getActivity());
                startActivityForResult(intent, 1);
            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullName = edtFullName.getText().toString().trim();
                final String email = edtEmail.getText().toString().trim();
                final String password = edtPassword.getText().toString().trim();
                String phone = edtPhone.getText().toString().trim();
                String address = tvAddress.getText().toString().trim();

                if(!validateFullName(fullName) | !validateEmail(email)
                        | !validatePassword(password) | !validatePhone(phone) | !validateAddress(address)){
                    return;
                }else{
                    ApiServices apiServices = ApiClient.getClient(getContext()).create(ApiServices.class);
                    Call<RespBase> callSignup = apiServices.signUp(new ReqSignUp(email,password,fullName, phone, tvAddress.getText().toString(),longitude, latitude));
                    callSignup.enqueue(new Callback<RespBase>() {
                        @Override
                        public void onResponse(Call<RespBase> call, Response<RespBase> response) {

                            if(response.body().getStatus() == true && response.body().getMessage() == null){
                                Toast.makeText(getActivity(), getString(R.string.register_success), Toast.LENGTH_SHORT).show();
                                ((SignInSignUpActivity) getActivity()).switchSignInFragment();
                            }else {
                                tvEmailError.setText(getString(R.string.email_is_already_registered));
                            }
                        }
                        @Override
                        public void onFailure(Call<RespBase> call, Throwable t) {
                            Toast.makeText(getActivity(), getString(R.string.please_check_the_internet), Toast.LENGTH_SHORT).show();
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
        edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                tvPhoneError.setText("");
            }
        });



    }

    private boolean validateFullName(String fullName){

        if(fullName.isEmpty()){
            tvFullNameError.setText(R.string.validate_fullname_text);
            return false;
        }
        return true;
    }

    private boolean validateAddress(String address){

        if(address.isEmpty()){
            tvConfirmPasswordError.setText(getString(R.string.addressEmpty));
            return false;
        }
        return true;
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
        if(password.length() < 6 ){
            tvPasswordError.setText(R.string.validate_password_text);
            return false;
        }
        return true;
    }

    private boolean validatePassConfirm(String passConfirm, String pass){

        if(passConfirm.isEmpty()){
            tvConfirmPasswordError.setText(R.string.validate_empty_confirm_password_text);
            return false;
        }
        if(!passConfirm.equals(pass)){
            tvConfirmPasswordError.setText(R.string.validate_confirm_password_text);
            return false;
        }
        return true;
    }

    public boolean validatePhone(String number)
    {
        if (number.isEmpty()) {
            tvPhoneError.setText(R.string.validate_phone_text);
            return false;
        }
        if (!android.util.Patterns.PHONE.matcher(number).matches())
        {
            tvPhoneError.setText(R.string.validate_pattern_phone_text);
            return false ;
        }
        return true;
    }

    private void mapping(View view){
        btnSignUp = (Button) view.findViewById(R.id.btnSignUp);
        edtFullName = (EditText) view.findViewById(R.id.edtFullName);
        edtEmail = (EditText) view.findViewById(R.id.edtEmail);
        edtPassword = (EditText) view.findViewById(R.id.edtPassword);
        tvAddress = (TextView) view.findViewById(R.id.tvAddress);
        edtPhone = (EditText) view.findViewById(R.id.edtPhone);
        tvFullNameError= (TextView) view.findViewById(R.id.tvFullNameError);
        tvEmailError = (TextView) view.findViewById(R.id.tvEmailError);
        tvPasswordError = (TextView) view.findViewById(R.id.tvPasswordError);
        tvConfirmPasswordError = (TextView) view.findViewById(R.id.tvConfirmPasswordError);
        tvPhoneError = (TextView) view.findViewById(R.id.tvPhoneError);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Toast.makeText(getActivity(), place.getAddress() , Toast.LENGTH_SHORT).show();
                longitude = String.valueOf(place.getLatLng().longitude);
                latitude = String.valueOf(place.getLatLng().latitude);
                tvAddress.setText(place.getAddress());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i("jjj", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

    }
}
