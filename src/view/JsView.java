/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import model.ModelBase;

/**
 *
 * @author BARIS
 */
public class JsView extends ViewBase {
    private final String file;
    private String encoding = "UTF-8";
    private Gson gson;
    private String jsVariable;

    public JsView(String jsFilePath,Gson gson,String jsVariable,ModelBase m) {
        super(m);
        this.gson = gson;
        this.file = jsFilePath;
        this.jsVariable = jsVariable;
    }
    
    public JsView(String jsFilePath,ModelBase m) {
        super(m);
        gson = GsonView.CreateGson(true, "yyyy-MM-dd HH:mm:ss");
        this.file = jsFilePath;
        jsVariable = getClass().getName();
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public void setJsVariable(String jsVariable) {
        this.jsVariable = jsVariable;
    }
    
    private String getFileAsString(ServletContext contx, String path) throws IOException {
        StringBuilder sb = new StringBuilder();        
        InputStream is = contx.getResourceAsStream(path);
        InputStreamReader sr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(sr);
        String line;
        while ( (line = br.readLine() ) != null ) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    @Override
    protected void showContent(Object data) {              
        
        HttpServletResponse r = getResponse();
        r.setCharacterEncoding(encoding);
        r.setContentType("Content-type: application/javascript");
        try {            
            r.getWriter().println( getFileAsString( getController().getServletContext(),file ) );
            r.getWriter().println( "var "+jsVariable+" = "+gson.toJson(data));
            
        } catch (Exception ex) {
            showError(ex);
        }
    }
    
}
