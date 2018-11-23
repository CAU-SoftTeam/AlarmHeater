package com.causoft.heatersetter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;

public class LoginDialog extends Dialog implements View.OnClickListener{

    LoginDialogListener LoginDialogListener;

    LoginHttpConnection myLoginWebServer;



    private Context context;
    private EditText idEditText;
    private EditText pwEditText;

    private Button loginButton;
    private Button cancleButton;
    private Button registerButton;

    private String macAddr = "ab:cd:ef";

    private Handler handler;

    private TextView view;

    private String id;
    private String pw;
    public LoginDialog(Context context, String macAddr, Handler handler, TextView debugEditText){
        super(context);
        this.context = context;
        this.macAddr = macAddr;
        this.handler = handler;
        this.view = debugEditText;
    }

    void setLoginDialogListener(LoginDialogListener loginDialogListener){
        this.LoginDialogListener = loginDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_login);

        idEditText = (EditText) findViewById(R.id.idEditText);
        pwEditText = (EditText) findViewById(R.id.pwEditText);
        loginButton = (Button) findViewById(R.id.loginButton);
        cancleButton = (Button) findViewById(R.id.cancleButon);
        registerButton = (Button) findViewById(R.id.registerButon);

        loginButton.setOnClickListener(this);
        cancleButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginButton:
                try {
                    id = idEditText.getText().toString();
                    pw = pwEditText.getText().toString();
                    myLoginWebServer = new LoginHttpConnection("http://18.225.32.120:5000/login", handler, view);
                    myLoginWebServer.Login(id,pw);
                    myLoginWebServer.login.join();
                    while(myLoginWebServer.login.getState() != Thread.State.TERMINATED);
                    if(myLoginWebServer.readData.equals("true")){
                        LoginDialogListener.onPositiveClicked(id,pw);
                        dismiss();
                    } else {
                        Toast.makeText(context, "로그인에 실패했습니다." + myLoginWebServer.readData + "#", Toast.LENGTH_LONG).show();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.cancleButon:
//                LoginDialogListener.onNegativeClicked();
                cancel();
                break;
            case R.id.registerButon:
                id = idEditText.getText().toString();
                pw = pwEditText.getText().toString();
                try {
                    myLoginWebServer = new LoginHttpConnection("http://18.225.32.120:5000/register",handler,view);
                    macAddr="asf";
                    if(macAddr.equals("")){
                        Toast.makeText(context, "장치를 연결한 후 회원가입해주세요.", Toast.LENGTH_LONG).show();
                    }
                    myLoginWebServer.Register(id, pw, macAddr);
                    myLoginWebServer.register.join();
                    while(myLoginWebServer.register.getState() != Thread.State.TERMINATED);
                    if(myLoginWebServer.readData.equals("true")){
                        LoginDialogListener.onPositiveClicked(id,pw);
                        dismiss();
                    } else {
                        Toast.makeText(context, "로그인에 실패했습니다.", Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}