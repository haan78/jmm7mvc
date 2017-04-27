package view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
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
public final class JsonView extends ViewBase {

    private String dateformat = "yyyy-MM-dd HH:mm:ss";
    
    public JsonView(ModelBase m) {
        super(m);
    }
    
    public JsonView(ModelBase m, String dateFormat) {
        super(m);
        setDateformat(dateFormat);
    }

    public void setDateformat(String dateformat) {
        this.dateformat = dateformat;
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
