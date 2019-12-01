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

import com.thesis.alome.R;
import com.thesis.alome.activity.JobDetailsActivity;
import com.thesis.alome.activity.MainActivity;
import com.thesis.alome.activity.StepActivity;

public class WarningDialog  {
    public static void showDialog(Context context, String msg){
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
                    //Intent intent = new Intent(context, ServiceListFragment.class);
                    //context.startActivity(intent);
                    ((JobDetailsActivity) context).onSupportNavigateUp();
                    dialog.dismiss();

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
