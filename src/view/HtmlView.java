/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javax.servlet.http.HttpServletResponse;
import model.ModelBase;
import render.HTMLRender;
import render.ScopeBase;

/**
 *
 * @author BARIS
 */
public class HtmlView extends ViewBase {

    private final String file;
    
    public HtmlView(String htmlFilePath,ModelBase m) {        
        super(m);
        file = htmlFilePath;
    }
    
    public HtmlView(String htmlFilePath) {
        super(null);
        file = htmlFilePath;
    }

    @Override
    protected void showContent(Object data) {
        
        ScopeBase scope = new ScopeBase();
        
        scope.data = data;
        scope.file = file;
        scope.url = getRequest().getRequestURL().toString();
        scope.query = getRequest().getQueryString();
                
        HTMLRender render = new HTMLRender(scope,getController().getServletContext());
        
        HttpServletResponse r = getResponse();
        r.setContentType("text/html;charset=UTF-8");        
        try {            
            r.getWriter().print( render.executeFile(file ) );
        } catch (Exception ex) {
            showError(ex);
        }
    }
    
}
