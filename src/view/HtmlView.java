/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javax.servlet.http.HttpServletResponse;
import model.ModelBase;
import render.HTMLRenderBase;
import render.HTMLRenderDefault;
import render.Scope;

/**
 *
 * @author BARIS
 */
public class HtmlView extends ViewBase {

    private final String file;
    private HTMLRenderBase render;
    
    public HtmlView(String htmlFilePath) {
        super(null);
        file = htmlFilePath;
        setRender( new HTMLRenderDefault() );
    }
    
    public HtmlView(String htmlFilePath,ModelBase model) {        
        super(model);
        file = htmlFilePath;
        setRender( new HTMLRenderDefault() );
    }
    
    public HtmlView(String htmlFilePath,ModelBase model,HTMLRenderBase render) {
        super(model);
        file = htmlFilePath;
        setRender(render);
    }        

    public final void setRender( HTMLRenderBase render ) {
        this.render = render;
    }
    
    @Override
    protected void showContent(Object data) {
        
        Scope scope = new Scope();
        
        scope.data = data;
        scope.file = file;
        scope.url = getRequest().getRequestURL().toString();
        scope.query = getRequest().getQueryString();
                
        render.setContext( getController().getServletContext() );
        render.setScope(scope);
        
        
        HttpServletResponse r = getResponse();
        r.setContentType("text/html;charset=UTF-8");        
        try {            
            r.getWriter().print( render.executeFile(file ) );
        } catch (Exception ex) {
            showError(ex);
        }
    }
    
}
