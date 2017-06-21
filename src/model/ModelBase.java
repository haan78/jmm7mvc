package model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.servlet.http.HttpServletRequest;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author BARIS
 */
public abstract class ModelBase {

    private boolean rejected = false;
    private HttpServletRequest reguest;
    private String defaultJsonDateFormat = "yyyy-MM-dd HH:mm:ss";

    public boolean isRejected() {
        return rejected;
    }

    public String getDefaultJsonDateFormat() {
        return defaultJsonDateFormat;
    }

    public void setDefaultJsonDateFormat(String defaultJsonDateFormat) {
        this.defaultJsonDateFormat = defaultJsonDateFormat;
    }

    protected void reject() {
        this.rejected = true;
    }

    protected final HttpServletRequest getRequest() {
        return reguest;
    }

    protected final String getStringRequest() throws IOException {
        //StringBuilder stringBuilder = new StringBuilder();


        if ( getRequest().getAttribute("com.xp.input")!= null ) {
            return getRequest().getAttribute("com.xp.input").toString();
        }
        String sr;
        InputStream inputStream = getRequest().getInputStream();
        if (inputStream != null) {
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            sr = result.toString("UTF-8");
            /*try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
                char[] charBuffer = new char[128];
                int bytesRead;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            }*/
        } else {
            //stringBuilder.append("");
            sr = "";
        }       
        getRequest().setAttribute("com.xp.input",sr);
        return sr;
        //return stringBuilder.toString();                
        
    }   
    
    protected final JsonElement getJsonRequest() throws IOException {
        Gson g = new com.google.gson.GsonBuilder().setDateFormat(defaultJsonDateFormat).create();
        return (JsonElement)g.fromJson(getStringRequest(), JsonElement.class );        
    }
    
    protected final Object getJsonRequest(Class T) throws IOException {
        Gson g = new com.google.gson.GsonBuilder().setDateFormat(defaultJsonDateFormat).create();
        return g.fromJson(getStringRequest(), T );        
    }

    public final void setRequest(HttpServletRequest request) {
        this.reguest = request;
    }

    public abstract Object perform() throws Exception;
}
