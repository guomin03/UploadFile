package com.example.dell.uploadfile;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by dell on 2017/9/22.
 */

public class UploadThread extends Thread {
    private String fileName;
    private String url;

    public UploadThread(String url,String fileName) {
        this.fileName = fileName;
        this.url = url;
    }

    @Override
    public void run() {
        String boundary="---------------------------7e139a1390772";
        String prefix="--";
        String end = "\r\n";

        URL httpUrl = null;
        try {
            httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);
            //输出流，向服务器写数据
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.writeBytes(prefix+boundary+end);
            out.writeBytes("Content-Disposition: form-data;"+" name=\"file\"; filename=\""+"a4.png"+"\""+end);
            out.writeBytes("Content-Type: text/plain; charset="+ "utf-8" + end+end); //web端设置
            FileInputStream fileInputStream = new FileInputStream(new File(fileName));
            byte[] bytes = new byte[1024*4];
            int len;
            while((len=fileInputStream.read(bytes))!=-1){
                out.write(bytes,0,len);
            }
            out.writeBytes(end);
            out.writeBytes(prefix+boundary+prefix+end+end);
            out.flush();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String str ;
            while((str=reader.readLine())!= null){
                sb.append(str);
            }
            System.out.println("respone:"+sb.toString());
            if(out!=null){
                out.close();
            }
            if(reader!=null){
                reader.close();
            }




        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
