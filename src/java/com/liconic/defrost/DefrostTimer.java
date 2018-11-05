/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liconic.defrost;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.TimerTask;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import static org.firebirdsql.jdbc.FBEscapedFunctionHelper.rand;

/**
 *
 * @author Rucha Deshpande
 */
public class DefrostTimer extends TimerTask{
     private Defrost defrost;

    public DefrostTimer(Defrost defrost) {
        this.defrost = defrost;
    }
    
    
    @Override
    public void run() {
        
        int cmdId = 11 + (int)(Math.random() * ((1111 - 11) + 1));
        System.out.println("Defrost job id: "+ cmdId);
        String cmd = "x"+cmdId;
        String xmlRequest ="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?> <STXRequest xmlns=\"http://regeneron.liconic.com\">\n" +
"     <Command>\n" +
"         <ID>"+cmd+"</ID>\n" +
"         <Cmd>Defrost</Cmd>\n" +
"         <User>ADMIN</User>\n" +
"     </Command>\n" +
"</STXRequest>";
        String xmlResponse="";
        java.io.StringWriter inputXML = new StringWriter();
        HttpClient httpClient = new DefaultHttpClient();
        System.out.println("Defrost request: "+xmlRequest);
    try {
      HttpPost postRequest = new HttpPost("http://localhost:8080/Scheduler/webresources/defrost");
      postRequest.setHeader("Content-Type", "application/xml");
      
     // String xmlRequest = inputXML.toString() ;
      HttpEntity entity = new ByteArrayEntity(xmlRequest.getBytes("UTF-8"));
      postRequest.setEntity(entity);
      HttpResponse httpResponse = httpClient.execute(postRequest);
      byte[] buffer = new byte[1024];
      if (httpResponse != null) {
        InputStream inputStream = httpResponse.getEntity().getContent();
        try {
          int bytesRead = 0;
          BufferedInputStream bis = new BufferedInputStream(inputStream);
          StringBuilder stringBuilder = new StringBuilder();
          while ((bytesRead = bis.read(buffer)) != -1) {
          stringBuilder.append(new String(buffer, 0, bytesRead));
          }
          //String Response
          System.out.println(stringBuilder.toString());
          xmlResponse = stringBuilder.toString();
          
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          try { inputStream.close(); } catch (Exception ignore) {}
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      httpClient.getConnectionManager().shutdown();
    }
       System.out.println("Defrost response: " + xmlResponse);
    }
    
}
