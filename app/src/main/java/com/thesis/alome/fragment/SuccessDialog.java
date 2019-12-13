package com.thesis.alome.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.thesis.alome.R;
import com.thesis.alome.activity.MainActivity;
import com.thesis.alome.activity.StepActivity;

public class SuccessDialog {
    public static void showDialog(Context context, String msg){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.abc_alert_dialog_success);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(context instanceof StepActivity){
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                    dialog.dismiss();
                }

            }
        });

        dialog.show();

    }
}
