package com.thesis.alome;

import android.Manifest;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.thesis.alome.activity.BaseActivity;
import com.thesis.alome.utils.DateInputMask;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initToolbar(R.id.toolbar,getString(R.string.profile));
        ButterKnife.bind(this);

        new DateInputMask(tvValueBirthDate);
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(this,R.layout.item_gender_spinner,
                                                        getResources().getStringArray(R.array.genders_arrays));
        spinnerGender.setAdapter(genderAdapter);
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
                        //stepViewModel.setImageList(uriList);
                        for (Uri uri : uriList) {
                            ivProfile.setImageURI(uri);
                        }
                    }
                });
    }
}
