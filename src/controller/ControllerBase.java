package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import view.ViewBase;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author BARIS
 */
public abstract class ControllerBase extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    private ViewBase autView = null;
    private ViewBase view = null;
    
    
    protected final void setAutView( ViewBase v ) {
        autView = v;
        autView.setAutView();        
    }
    
    protected final void setView(ViewBase v ) {
        view = v;
    }   
    
    protected abstract void configure(String pathInfo);
    
    public static String getFileAsString(ServletContext contx, String path) throws IOException {
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
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        configure( request.getPathInfo() );      
        
        if ( view !=null ) {
            
            if ( autView!=null ) {
                autView.setController(this);
                autView.setRequest(request);
                autView.setResponse(response);
                if ( !autView.perform() ) {
                    return;
                }
            }
            
            view.setController(this);
            view.setRequest(request);
            view.setResponse(response);
            view.perform();
        } else {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/plain");
            response.getWriter().print("No view has been set!");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "MM7 Servlet Class";
    }// </editor-fold>

}
