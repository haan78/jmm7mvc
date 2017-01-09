/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ModelBase;

/**
 *
 * @author BARIS
 */
public class JspView extends  ViewBase {
    
    private final String jsp;

    public JspView(ModelBase m,String jsp) {
        super(m);
        this.jsp = jsp;
    }
    
    public JspView(String jsp) {
        super(null);
        this.jsp = jsp;
    }

    @Override
    protected void showContent(Object data) {
        HttpServletRequest request = getRequest();
        HttpServletResponse response = getResponse();
        request.setAttribute("DATA", data);
        RequestDispatcher rd = request.getRequestDispatcher(jsp);
        try {
            rd.forward(request, response);
        } catch (ServletException | IOException ex) {
            showError(ex);
        }
    }    
}
