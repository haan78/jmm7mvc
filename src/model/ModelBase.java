package model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.io.BufferedReader;
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

    private boolean aborted = false;
    private HttpServletRequest reguest;

    public boolean isRejected() {
        return aborted;
    }

    protected void reject() {
        this.aborted = true;
    }

    protected final HttpServletRequest getRequest() {
        return reguest;
    }

    protected final String getStringRequest() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();


        InputStream inputStream = getRequest().getInputStream();
        if (inputStream != null) {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
                char[] charBuffer = new char[128];
                int bytesRead;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            }
        } else {
            stringBuilder.append("");
        }
        
        return stringBuilder.toString();                
    }
    
    protected final JsonElement getJsonRequest() throws IOException {
        Gson g = new Gson();
        return (JsonElement)g.fromJson(getStringRequest(), JsonElement.class );
    }
    
    protected final JsonElement getJsonRequest(String dateFormat) throws IOException {
        Gson g = new com.google.gson.GsonBuilder().setDateFormat(dateFormat).create();
        return (JsonElement)g.fromJson(getStringRequest(), JsonElement.class );        
    }

    public final void setRequest(HttpServletRequest request) {
        this.reguest = request;
    }

    public abstract Object perform() throws Exception;
}
