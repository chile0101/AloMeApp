package com.thesis.alome.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.thesis.alome.R;
import com.thesis.alome.activity.JobDetailsActivity;
import com.thesis.alome.config.ApiClient;
import com.thesis.alome.config.ApiServices;
import com.thesis.alome.config.PrefUtils;
import com.thesis.alome.model.Comment;
import com.thesis.alome.model.ReqSignUp;
import com.thesis.alome.model.RespBase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewDialog {
    public static void showDialog(Context context, Long providerId){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.abc_alert_dialog_review);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        //set background
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        RatingBar ratingBar = (RatingBar) dialog.findViewById(R.id.ratingBar);
        EditText edtComment = (EditText) dialog.findViewById(R.id.edtComment);
        Button btnSend = (Button) dialog.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comment cmt = new Comment(edtComment.getText().toString(), PrefUtils.getFullName(context), providerId,ratingBar.getRating());
                ApiServices apiServices = ApiClient.getClient(context).create(ApiServices.class);
                Call<RespBase> call = apiServices.postComment(PrefUtils.getId(context),cmt,PrefUtils.getApiKey(context));
                call.enqueue(new Callback<RespBase>() {
                    @Override
                    public void onResponse(Call<RespBase> call, Response<RespBase> response) {
                        if(response.body()!= null && response.body().getStatus()){
                            Toast.makeText(context, "Bạn đã đánh giá cho công việc này", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<RespBase> call, Throwable t) {
                        Toast.makeText(context, "Something Wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);

    }
}
