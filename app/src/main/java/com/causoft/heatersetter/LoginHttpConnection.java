package com.causoft.heatersetter;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.widget.EditText;
import android.widget.TextView;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;


public class LoginHttpConnection extends HttpConnection {
    LoginInitConnection login;
    RegisterInitConnection register;
    public LoginHttpConnection(String sURL, Handler handler, TextView view) throws IOException {
        super(sURL,handler,view);
    }
    public void Login(String username, String password) {
        JSONObject id = new JSONObject();
        id.put("id",username);
        id.put("pw",password);
        login = new LoginInitConnection(id.toString());
        login.start();
    }


    public void Register(String username, String password, String macAddress) {
        JSONObject id = new JSONObject();
        id.put("id",username);
        id.put("pw",password);
        id.put("macAddr",macAddress);
        register = new RegisterInitConnection(id.toString());
        register.start();
    }

/*
    public boolean Register2(String username, String password, String macAddress) throws IOException, ParseException {
        JSONObject id = new JSONObject();
        id.put("id",username);
        id.put("pw",password);
        id.put("macAddr",username);

        JSONParser parser = new JSONParser();
        JSONObject pass = new JSONObject();

        // 데이터
        String param = id.toString();
        InitServer initServer = new InitServer(param);
        initServer.run();


        return false;

    }
    */

    class RegisterInitConnection extends InitServer {
        String data;

        RegisterInitConnection(String data) {
            super(data);

        }

        @Override
        void afterGetData(){
            final JSONObject[] jsonObject = {null};
            try {
                jsonObject[0] = (JSONObject) parser.parse(serverView.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //jsonObject[0] = (JSONObject) parser.parse(serverView.getText().toString());
            if(jsonObject[0] != null){
                String strPass = (String) jsonObject[0].get("OX");
                if(strPass.equals("O")){
                    serverView.setText("true");
                }
                else {
                    serverView.setText("false"); }
            }
        }

    }




/*
    class RegisterInitConnection extends InitServer{
        RegisterInitConnection(String data) {
            super(data);
        }
        @Override
        public void run(){
            super.run();
            try {
              JSONObject jsonObject = null;
              jsonObject = (JSONObject) parser.parse(getData);
              if(jsonObject != null){
                  String strPass = (String) jsonObject.get("OX");
                  if(strPass.equals("O")){
                      serverView.setText("true");
                  }
                  else {
                      serverView.setText("false");
                  }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
*/
class LoginInitConnection extends InitServer{
    LoginInitConnection(String data) {
        super(data);
    }

    @Override
    void afterGetData(){
        try {
            JSONObject jsonObject = null;
            jsonObject = (JSONObject) parser.parse(super.getData);
            if(jsonObject != null){
                String strPass = (String) jsonObject.get("OX");
                if(strPass.equals("O")){
                    readData = "true";
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            serverView.setText("true");
                        }
                    });
                }
                else {
                    readData = "false";
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            serverView.setText("false");
                        }
                    });
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

        /*
 class RegisterInitConnection extends InitServer{
        RegisterInitConnection(String data) {
            super(data);
        }
        @Override
        public void run(){
            super.run();
        }
        @Override
        void afterGetData() {
            JSONObject jsonObject = null;
            try {
                jsonObject = (JSONObject) parser.parse(getData);
                if(jsonObject != null){
                    String strPass = (String) jsonObject.get("OX");
                    if(strPass.equals("O")){
                        serverView.setText("true");
                    }
                    else {
                        serverView.setText("false");
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
    class LoginInitConnection

            */

}
    }



