package com.photowall.net;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import android.util.Log;

public class TestNet

{
    
    public static String post(String actionUrl, Map<String, String> params, 
            Map<String, File> files) throws IOException { 
          StringBuilder sb2 = new StringBuilder(); 
          String BOUNDARY = java.util.UUID.randomUUID().toString();
          String PREFIX = "--" , LINEND = "\r\n";
          String MULTIPART_FROM_DATA = "multipart/form-data"; 
          String CHARSET = "UTF-8";

          URL uri = new URL(actionUrl); 
          HttpURLConnection conn = (HttpURLConnection) uri.openConnection(); 
          conn.setReadTimeout(5 * 1000); 
          conn.setDoInput(true);
          conn.setDoOutput(true);
          conn.setUseCaches(false); 
          conn.setRequestMethod("POST"); 
          conn.setRequestProperty("connection", "keep-alive"); 
          conn.setRequestProperty("Charsert", "UTF-8"); 
          conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY); 

          StringBuilder sb = new StringBuilder(); 
          for (Map.Entry<String, String> entry : params.entrySet()) { 
            sb.append(PREFIX); 
            sb.append(BOUNDARY); 
            sb.append(LINEND); 
            sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINEND);
            sb.append("Content-Type: text/plain; charset=" + CHARSET+LINEND);
            sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
            sb.append(LINEND);
            sb.append(entry.getValue()); 
            sb.append(LINEND); 
          } 

          DataOutputStream outStream = new DataOutputStream(conn.getOutputStream()); 
          outStream.write(sb.toString().getBytes()); 
          if(files!=null){
            //int i = 0;
            for (Map.Entry<String, File> file: files.entrySet()) { 
              StringBuilder sb1 = new StringBuilder(); 
              sb1.append(PREFIX); 
              sb1.append(BOUNDARY); 
              sb1.append(LINEND); 
              //sb1.append("Content-Disposition: form-data; name=\"file"+(i++)+"\"; filename=\""+file.getKey()+"\""+LINEND);
              sb1.append("Content-Disposition: form-data; name=\"postfilename\"; filename=\""+file.getKey()+"\""+LINEND);
              sb1.append("Content-Type: application/octet-stream; charset="+CHARSET+LINEND);
              sb1.append(LINEND);
              outStream.write(sb1.toString().getBytes()); 

              InputStream is = new FileInputStream(file.getValue());
              byte[] buffer = new byte[1024]; 
              int len = 0; 
              while ((len = is.read(buffer)) != -1) { 
                outStream.write(buffer, 0, len); 
              }

              is.close(); 
              outStream.write(LINEND.getBytes()); 
            } 
          }
          
          byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes(); 
          outStream.write(end_data); 
          outStream.flush(); 

          int res = conn.getResponseCode(); 
          InputStream in = null;
          if (res == 200) {
            in = conn.getInputStream(); 
            int ch; 
            
            while ((ch = in.read()) != -1) { 
              sb2.append((char) ch); 
            } 
            Log.i("CAMERA", sb2.toString());
          }
          
          return in == null ? null : sb2.toString(); 
        }
}