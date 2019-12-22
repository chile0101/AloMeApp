package com.thesis.alome.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ipaulpro.afilechooser.utils.FileUtils;
import com.squareup.picasso.Picasso;
import com.thesis.alome.R;
import com.thesis.alome.config.ApiClient;
import com.thesis.alome.config.ApiServices;
import com.thesis.alome.config.PrefUtils;
import com.thesis.alome.model.Profile;
import com.thesis.alome.model.RespBase;
import com.thesis.alome.utils.DateInputMask;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thesis.alome.fragment.StepTwoFragment.RC_EXTERNAL;

public class ProfileActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private List<Uri> selectedUriList;
    @BindView(R.id.uploadImg) FrameLayout uploadImg;
    @BindView(R.id.ivProfile) ImageView ivProfile;
    @BindView(R.id.imgPlus) ImageView imgPlus;
    @BindView(R.id.edtValueFullname) EditText edtValueFullname;
    @BindView(R.id.tvErrorFullname) TextView tvErrorFullname;
    @BindView(R.id.tvValueBirthDate) EditText tvValueBirthDate;
    @BindView(R.id.tvErrorBirthDate) TextView tvErrorBirthDate;
    @BindView(R.id.spinnerGender) Spinner spinnerGender;
    @BindView(R.id.tvErrorGender) TextView tvErrorGender;
    @BindView(R.id.tvValuePhone) EditText tvValuePhone;
    @BindView(R.id.tvPhone) TextView tvPhone;
    @BindView(R.id.tvValueEmail) EditText tvValueEmail;
    @BindView(R.id.btnSave) Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initToolbar(R.id.toolbar,getString(R.string.profile));
        ButterKnife.bind(this);

        //gender spinner
        String[] genderArr = {getString(R.string.male),getString(R.string.female)};
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(this,R.layout.item_gender_spinner,genderArr);
        spinnerGender.setAdapter(genderAdapter);

        // load data
        String avatarUrl = PrefUtils.getAvatar(this);
        if(!avatarUrl.equals("")){
            Picasso.get().load(avatarUrl).into(ivProfile);
        }

        edtValueFullname.setText(PrefUtils.getFullName(this));
        edtValueFullname.setSelection(edtValueFullname.getText().length());
        tvValueBirthDate.setText(PrefUtils.getDateOfBirth(this));
        String gender = PrefUtils.getGender(this);
        if(!gender.equals("")){
            int spinnerPosition = genderAdapter.getPosition(gender);
            spinnerGender.setSelection(spinnerPosition);
        }
        tvValuePhone.setText(PrefUtils.getPhoneNumber(this));
        tvValueEmail.setText(PrefUtils.getUserName(this)); // is Email

        //mask edit
        new DateInputMask(tvValueBirthDate);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call api
                ApiServices apiServices = ApiClient.getClient(getApplicationContext()).create(ApiServices.class);
                Profile profile = new Profile(tvValueBirthDate.getText().toString(),
                        edtValueFullname.getText().toString(),
                        spinnerGender.getSelectedItemPosition() == 0 ? true : false,
                        tvValuePhone.getText().toString());
                Call<RespBase> call = apiServices.updateProfile(PrefUtils.getId(getApplicationContext()),profile,PrefUtils.getApiKey(getApplicationContext()));
                call.enqueue(new Callback<RespBase>() {
                    @Override
                    public void onResponse(Call<RespBase> call, Response<RespBase> response) {
                        if(response.body()!= null && response.body().getStatus()){
                            PrefUtils.storeShortName( getApplicationContext(),profile.getFullName().split(" ",2)[0]);
                            Intent intent = new Intent(ProfileActivity.this, LaunchActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(ProfileActivity.this, getString(R.string.somthing_wrong), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RespBase> call, Throwable t) {
                        Toast.makeText(ProfileActivity.this, getString(R.string.please_check_the_internet), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRequestPermissions();
            }
        });





    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_EXTERNAL)
    private void onRequestPermissions() {
        if (EasyPermissions.hasPermissions(this, perms)) {
            onOpenBottomSheetGallery();
        } else {
            ActivityCompat.requestPermissions(this, perms, RC_EXTERNAL);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        onOpenBottomSheetGallery();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(this , "Permission Denied\n" + perms.toString(), Toast.LENGTH_SHORT).show();
    }

    public void onOpenBottomSheetGallery(){
        TedBottomPicker.with(this)
                .setPeekHeight(1600)
                .showTitle(false)
                .setCompleteButtonText("Done")
                .setEmptySelectionText("No Select")
                .setSelectedUriList(selectedUriList)
                .setSelectMaxCount(1)
                .setSelectMaxCountErrorText(R.string.not_select_more_than_1)
                .showMultiImage(new TedBottomSheetDialogFragment.OnMultiImageSelectedListener() {
                    @Override
                    public void onImagesSelected(List<Uri> uriList) {
                        selectedUriList = uriList;
                        if(!uriList.isEmpty()){
                            uploadAvatar(uriList.get(0));
                        }
                    }
                });
    }

    private void uploadAvatar(Uri uri){
        ivProfile.setImageURI(uri);
        ApiServices apiServices = ApiClient.getClient(getApplicationContext()).create(ApiServices.class);
        Call<RespBase> call = apiServices.uploadAvatar(PrefUtils.getId(getApplicationContext()),
                                                        PrefUtils.getApiKey(getApplicationContext()),
                                                    prepareFilePart("avatar",uri));
        call.enqueue(new Callback<RespBase>() {
            @Override
            public void onResponse(Call<RespBase> call, Response<RespBase> response) {
                if(response.body() != null && response.body().getStatus()){
                    //Toast.makeText(ProfileActivity.this, "image uploaded", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ProfileActivity.this, getString(R.string.somthing_wrong), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespBase> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NotNull
    private MultipartBody.Part prepareFilePart(String partName, Uri uri){
        File file = FileUtils.getFile(ProfileActivity.this,uri);
        File compressedImageFile = null ;
        try {
            compressedImageFile = new Compressor(this).compressToFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create( MediaType.parse("multipart/form-dataImg"), compressedImageFile);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestBody);

    }
}
