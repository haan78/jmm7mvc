package view;


import controller.ControllerBase;
import model.ModelBase;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author BARIS
 */
public abstract class ViewBase {

    private boolean autView = false;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private ModelBase model = null;
    private ControllerBase controller;

    public ViewBase(ModelBase m) {
        setModel(m);
    }
    
    protected final ControllerBase getController() {
        return controller;
    }
    
    protected final HttpServletResponse getResponse() {
        return response;
    }
    
    protected final HttpServletRequest getRequest() {
        return request;
    }

    public final void setController(ControllerBase c) {
        controller = c;
    }

    public final boolean isAutView() {
        return autView;
    }

    public final void setAutView() {
        autView = true;
    }

    public final void setRequest(HttpServletRequest req) {
        request = req;
    }

    public final void setResponse(HttpServletResponse res) {
        response = res;
    }

    public final void setModel(ModelBase m) {
        model = m;        
    }

    public final boolean perform() {

        try {
            if (model != null) {
                model.setRequest(request);
                Object data = model.perform();

                if (autView) {
                    if (model.isRejected()) {
                        showContent(data);
                        return false;
                    }
                } else {
                    showContent(data);
                }
            } else {
                showContent(null);
            }
        } catch (Exception ex) {
            showError(ex);
        }

        return true;
    }

    protected void showError(Exception ex) {
        PrintWriter w = null;
        try {
            w = getResponse().getWriter();
            w.println(ex.toString());
            w.println(ex.getMessage());            
        } catch (IOException ex1) {
            if (w != null) {
                w.println(ex1.toString());
                w.println(ex1.getMessage());
            }
        }
    }

    protected abstract void showContent(Object data);

}
