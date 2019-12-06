package com.thesis.alome.fragment;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.thesis.alome.R;
import com.thesis.alome.config.FragmentLifecycle;
import com.thesis.alome.viewmodel.StepViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class StepTwoFragment extends Fragment implements FragmentLifecycle,EasyPermissions.PermissionCallbacks {

    String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public static final int RC_EXTERNAL = 999;
    private RequestManager requestManager;
    private List<Uri> selectedUriList;
    private StepViewModel stepViewModel;

    @BindView(R.id.edtDesc) EditText edtDesc;
    @BindView(R.id.selected_photos_container) ViewGroup mSelectedImagesContainer;
    @BindView(R.id.btnUpload) ImageView btnUpload;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stepViewModel = ViewModelProviders.of(getActivity()).get(StepViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_step_two,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requestManager = Glide.with(this);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRequestPermissions();
            }
        });
        edtDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                stepViewModel.setDescription(s.toString());
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
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            onOpenBottomSheetGallery();
        } else {
            ActivityCompat.requestPermissions(getActivity(), perms, RC_EXTERNAL);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        onOpenBottomSheetGallery();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(getActivity(), "Permission Denied\n" + perms.toString(), Toast.LENGTH_SHORT).show();
    }

    public void onOpenBottomSheetGallery(){
        TedBottomPicker.with(getActivity())
                .setPeekHeight(1600)
                .showTitle(false)
                .setCompleteButtonText("Done")
                .setEmptySelectionText("No Select")
                .setSelectedUriList(selectedUriList)
                .showMultiImage(new TedBottomSheetDialogFragment.OnMultiImageSelectedListener() {
                    @Override
                    public void onImagesSelected(List<Uri> uriList) {
                        selectedUriList = uriList;
                        stepViewModel.setImageList(uriList);
                        showUriList(uriList);
                    }
                });
    }

    private void showUriList(List<Uri> uriList) {
        // Remove all views before
        // adding the new ones.
        mSelectedImagesContainer.removeAllViews();
        mSelectedImagesContainer.setVisibility(View.VISIBLE);

        int widthPixel = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        int heightPixel = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());

        for (Uri uri : uriList) {

            View imageHolder = LayoutInflater.from(getActivity()).inflate(R.layout.item_image_type_image_inside_job_detail, null);
            ImageView thumbnail = imageHolder.findViewById(R.id.imageView);

            requestManager
                    .load(uri.toString())
                    .apply(new RequestOptions().fitCenter())
                    .into(thumbnail);

            mSelectedImagesContainer.addView(imageHolder);
            thumbnail.setLayoutParams(new FrameLayout.LayoutParams(widthPixel, heightPixel));
        }
    }

    @Override
    public void onPauseFragment() {}

    @Override
    public void onResumeFragment() {}
}
