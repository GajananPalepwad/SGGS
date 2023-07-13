package com.sggs.sggs.loadingAnimation;

import android.app.Activity;
import android.app.Dialog;


import com.sggs.sggs.R;

public class LoadingDialog {
    public LoadingDialog(Activity activity) {
        this.activity = activity;
    }

    private Activity activity;
    private Dialog dialog;

    public void startLoading(){

        dialog = new Dialog(activity, R.style.AppBottomSheetDialogTheme);
        dialog.setContentView(R.layout.custom_loading_dialog);
        dialog.setCancelable(false);
        dialog.show();

    }

    public void stopLoading(){
        dialog.dismiss();
    }




}
