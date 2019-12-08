package com.thesis.alome.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thesis.alome.R;
import com.thesis.alome.activity.JobDetailsActivity;
import com.thesis.alome.activity.MainActivity;
import com.thesis.alome.activity.StepActivity;
import com.thesis.alome.config.ApiClient;
import com.thesis.alome.config.ApiServices;
import com.thesis.alome.config.PrefUtils;
import com.thesis.alome.model.RespBase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WarningDialog  {
    public static void showDialog(Context context, String msg,Long n){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.abc_alert_dialog_warning);

        //set background
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(context instanceof JobDetailsActivity){
                    //delete job
                    ApiServices apiServices = ApiClient.getClient(context).create(ApiServices.class);
                    Call<RespBase> call = apiServices.deleteJob(n, PrefUtils.getApiKey(context));
                    call.enqueue(new Callback<RespBase>() {
                        @Override
                        public void onResponse(Call<RespBase> call, Response<RespBase> response) {
                            ((JobDetailsActivity) context).onSupportNavigateUp();
                            dialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<RespBase> call, Throwable t) {
                            Toast.makeText(context, context.getString(R.string.somthing_wrong), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

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

    }
}
