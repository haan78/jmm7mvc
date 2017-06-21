package view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import model.ModelBase;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author BARIS
 */
public class JsonView extends ViewBase {

    private String dateformat = "yyyy-MM-dd HH:mm:ss";
    
    public JsonView(ModelBase m) {
        super(m);
    }
    
    public JsonView(ModelBase m, String dateFormat) {
        super(m);
        this.dateformat = dateFormat;
    }

    public void setDateformat(String dateformat) {
        this.dateformat = dateformat;
    }

    public String getDateformat() {
        return dateformat;
    }    
    
    @Override
    protected void showError(Exception ex) {
        
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
        Gson g;
        if ( (dateformat!=null) && (!dateformat.isEmpty()) ) {
            g = new GsonBuilder().setDateFormat(dateformat).create();
        } else {
            g = new Gson();
        }
        
        try {
            HttpServletResponse r = getResponse();
            r.setHeader("Content-Type", "application/json; charset=utf-8");
            r.getWriter().print(g.toJson(data));
        } catch (IOException ex) {
            this.showError(ex);
        }
    }
    
}
