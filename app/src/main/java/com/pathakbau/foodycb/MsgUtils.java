package com.pathakbau.foodycb;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

public class MsgUtils {
    private Context context;
    private AlertDialog progressDialog;
    private AlertDialog confirmationDialog;

    public MsgUtils(Context context) {
        this.context = context;
        progressDialog = new AlertDialog.Builder(context)
                .setView(R.layout.progress_view)
                .setCancelable(false)
                .create();
        confirmationDialog = new AlertDialog.Builder(context)
                .setTitle("Confirm Delete")
                .setNegativeButton("Cancel", (dialog, which) -> confirmationDialog.cancel())
                .create();
    }

    public void showConfirmationDialog(String message, DialogInterface.OnClickListener onDeleteClick,
                                       DialogInterface.OnCancelListener onDialogCancel){
        confirmationDialog.setMessage(message);
        confirmationDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Delete", onDeleteClick);
        confirmationDialog.setOnCancelListener(onDialogCancel);
        confirmationDialog.show();
    }

    public void dismissConfirmationDialog(){
        confirmationDialog.dismiss();
    }

    public void showProgressDialog(){
        progressDialog.show();
    }

    public void dismissProgressDialog(){
        progressDialog.dismiss();
    }

    public void showToast(String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
