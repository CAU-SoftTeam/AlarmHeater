package com.causoft.heatersetter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.widget.EditText;
import android.widget.TextView;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.*;



public class HttpConnection{


    URL myURL;
    OutputStreamWriter osw;
    InputStream isr;
    HttpURLConnection conn;
    JSONParser parser = new JSONParser();
    Handler handler;

    TextView serverView;
    BufferedReader reader; // 서버로부터 온 메시지를 읽어드릴 읽기버퍼
    String readData = "";


    public HttpConnection(String sURL, Handler handler, TextView serverView) {
        try {
            this.serverView = serverView;
            myURL = new URL(sURL);
            this.handler = handler;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public void writeData(String data) {
        try {
            osw.write(data);
            osw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    void close() {
        try {
            osw.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        conn.disconnect();
    }

    abstract class InitServer extends Thread {
        String data;
        String getData;
        InitServer(String data) {
            this.data = data;
        }
        public void run() {
            try {
                conn = (HttpURLConnection) myURL.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST"); // 보내는 타입
                conn.setRequestProperty("Accept-Language", "ko-kr,ko;q=0.8,en-us;q=0.5,en;q=0.3");
                conn.connect();
                osw = new OutputStreamWriter(conn.getOutputStream());
                writeData(data);
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
                    reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                else
                    reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                String line = "";
                if ((line = reader.readLine()) != null) {
                    String finalLine1 = line;
                    getData = finalLine1;
                    /*
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            serverView.setText(finalLine1);
                            afterGetData();
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    */
                }
               // finalLine = buffer.toString();
               // getData = finalLine;
                close();
            } catch (ProtocolException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            // reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
        }
        abstract void afterGetData();

    }


}
    /*
    public boolean Register(String username, String password) throws IOException, ParseException {
        JSONObject id = new JSONObject();
        id.put("username",username);
        id.put("password",password);
        id.put("name",username);

        JSONParser parser = new JSONParser();
        JSONObject pass = new JSONObject();

        // 데이터
        String param = id.toString();
        String sUrl = "http://192.168.43.206:12345/register";
        URL url = new URL(sUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST"); // 보내는 타입
        conn.setRequestProperty("Accept-Language", "ko-kr,ko;q=0.8,en-us;q=0.5,en;q=0.3");
        // 전송
        OutputStreamWriter osw = new OutputStreamWriter(
                conn.getOutputStream());

        try {
            osw.write(param);
            osw.flush();

            // 응답
            BufferedReader br = null;
            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            String line = null;
            if ((line = br.readLine()) != null) {
                Object obj = parser.parse(line);
                JSONObject jsonObject = (JSONObject) obj;
                String strPass = (String) jsonObject.get("OX");
                if(strPass.equals("O")){
                    osw.close();
                    br.close();
                    conn.disconnect();
                    return true;
                } else {
                    osw.close();
                    br.close();
                    conn.disconnect();
                    return false;
                }
            }

            // 닫기

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;

    }


    public String sendMessage(String text) throws IOException {
        String returnMessage = "";
        JSONObject id = new JSONObject();
        id.put("username",userId);
        id.put("message",text);
        id.put("serverCode",chatRooomId);



        JSONParser parser = new JSONParser();
        JSONObject pass = new JSONObject();

        // 데이터
        String param = id.toString();
        String sUrl = "http://192.168.43.206:12345/message";
        URL url = new URL(sUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST"); // 보내는 타입
        conn.setRequestProperty("Accept-Language", "ko-kr,ko;q=0.8,en-us;q=0.5,en;q=0.3");
        // 전송
        OutputStreamWriter osw = new OutputStreamWriter(
                conn.getOutputStream());

        try {
            osw.write(param);
            osw.flush();

            // 응답
            BufferedReader br = null;
            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            String line = null;
            if ((line = br.readLine()) != null) {
                Object obj = parser.parse(line);
                JSONObject jsonObject = (JSONObject) obj;
                returnMessage = (String) jsonObject.get("O");
                osw.close();
                br.close();
                conn.disconnect();

            }

            // 닫기

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return returnMessage;

    }
    */



   /*
            JSONArray phoneNum = (JSONArray) jsonObject.get("phoneNumbers");
            Iterator<String> iterator = phoneNum.iterator();
            while (iterator.hasNext()) {
                System.out.println("phoneNumbers :: " + iterator.next());
            }
            */
