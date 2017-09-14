/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import model.ModelBase;

/**
 *
 * @author BARIS
 */
public class GsonView extends ViewBase {

    private final Gson gson;
    
    
    public static Gson CreateGson(boolean serializeNuls, String dateformat) {
        GsonBuilder gb = new GsonBuilder();
        if ( serializeNuls ) {
            gb = gb.serializeNulls();
        }
        
        if ( (dateformat!=null) && (!dateformat.isEmpty()) ) {
            gb = gb.setDateFormat(dateformat);
        }
        
        return gb.create();
    }
    
    public static Gson CreateGson(boolean serializeNuls) {
        return CreateGson(serializeNuls, "yyyy-MM-dd HH:mm:ss") ;
    }
    
    public static Gson CreateGson() {
        return CreateGson(true, "yyyy-MM-dd HH:mm:ss");
    }
    
    public GsonView(ModelBase m, Gson gson ) {
        super(m);
        this.gson = gson;
    }
    
    public GsonView(ModelBase m ) {
        super(m);
        this.gson = CreateGson();
    }
    
    @Override
    protected void showError(Throwable ex) {
        
        String mess;
        
        mess = ex.getCause().getMessage().replaceAll("\n","").replaceAll("\r","").replaceAll("\"","");
        
        HttpServletResponse r = getResponse();
        r.setHeader("Content-Type", "application/json; charset=utf-8");
        try {
            r.getWriter().print("{ \"type\":\"error\", \"message\":\""+mess+"\" }");
        } catch (IOException ex1) {
            Logger.getLogger(JsonView.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }

    @Override
    protected void showContent(Object data) {
        
        try {
            HttpServletResponse r = getResponse();
            r.setHeader("Content-Type", "application/json; charset=utf-8");
            r.getWriter().print(this.gson.toJson(data));
        } catch (IOException ex) {
            this.showError(ex);
        }
    }
    
}
