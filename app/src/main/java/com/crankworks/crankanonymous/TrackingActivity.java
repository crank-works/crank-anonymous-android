package com.crankworks.crankanonymous;

import android.app.Activity;
import android.os.Bundle;

// Msgbox
import android.app.AlertDialog;
import android.view.View;
import android.content.DialogInterface;


public class TrackingActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tracking);
    }

    public void onCancelClicked(View view) {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);                      
        dlgAlert.setTitle("Cancel"); 
        dlgAlert.setMessage("You clicked the cancel button"); 
        dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                finish();
            }
        });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }
}
