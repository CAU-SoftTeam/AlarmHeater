package com.causoft.heatersetter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingDialog extends Dialog implements View.OnClickListener{
    SettingDialogListener settingDialogListener;

    private Context context;

    private EditText IPEditText;
    private Button saveButton;
    private Button cancleButton;

    String hostIP;
    public SettingDialog(Context context, String hostIP){
        super(context);
        this.context = context;
        this.hostIP = hostIP;
    }

    void setSettingDialogListener(SettingDialogListener settingDialogListener){
        this.settingDialogListener = settingDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_setting);
        IPEditText = (EditText) findViewById(R.id.ipEditText);
        saveButton = (Button) findViewById(R.id.saveButton);
        cancleButton = (Button) findViewById(R.id.cancleButon);

        saveButton.setOnClickListener(this);
        cancleButton.setOnClickListener(this);

        IPEditText.setText(hostIP);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.saveButton:
                settingDialogListener.onPositiveClicked(IPEditText.getText().toString());
                dismiss();
                break;
            case R.id.cancleButon:
                cancel();
                break;
        }
    }
}