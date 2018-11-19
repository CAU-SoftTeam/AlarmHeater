package com.causoft.heatersetter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SettingDialog extends Dialog implements View.OnClickListener{
    DialogListener dialogListener;

    private Context context;

    private EditText IPEditText;

    private Button saveButton;
    private Button cancleButton;


    public SettingDialog(Context context){
        super(context);
        this.context = context;
    }

    void setDialogListener(DialogListener dialogListener){
        this.dialogListener = dialogListener;
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.saveButton:
                dialogListener.onPositiveClicked(IPEditText.getText().toString());
                dismiss();
                break;
            case R.id.cancleButon:
                cancel();
                break;
        }
    }
}