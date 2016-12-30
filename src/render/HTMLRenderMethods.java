package render;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author BARIS
 */

import com.google.gson.Gson;

public class HTMLRenderMethods {   
    
    private Object[] parameters;
    
    public void setParameters( Object[] parameters ) {
        this.parameters = parameters;
    }
    
    protected Object[] getParameters() {
        return parameters;
    }
    
    public String JVAR() {
        
        String name = (String)getParameters()[0];
        Object data = getParameters()[1];
        
        Gson gson = new Gson();
        
        return "var "+name+" = "+gson.toJson(data)+";";
    }
    
    public String ECHO() {
        return (String)getParameters()[0];
    }
    
    public String LOAD(String file) {
        return file;
    }
}
