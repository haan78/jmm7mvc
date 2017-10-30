/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.google.gson.Gson;
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

    public JsView(ModelBase m,String jsFilePath,Gson gson,String jsVariable) {
        super(m);
        this.gson = gson;
        this.file = jsFilePath;
        this.jsVariable = jsVariable;
    }    
    public JsView(ModelBase m,String jsFilePath) {
        super(m);
        gson = GsonView.CreateGson(true, "yyyy-MM-dd HH:mm:ss");
        this.file = jsFilePath;
        jsVariable = getClass().getName();
    }
    
    public JsView(ModelBase m) {
        super(m);        
        gson = GsonView.CreateGson(true, "yyyy-MM-dd HH:mm:ss");
        this.file = "";
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

    @Override
    protected void showContent(Object data) {              
        
        HttpServletResponse r = getResponse();
        r.setCharacterEncoding(encoding);
        r.setContentType("application/javascript");
        try {            
            r.getWriter().println( "var "+jsVariable+" = "+gson.toJson(data)+";");
            if ( !"".equals(file) ) {
                r.getWriter().println( controller.ControllerBase.getFileAsString( getController().getServletContext(),file ) );
            }
            
        } catch (Exception ex) {
            showError(ex);
        }
    }
    
}
