/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package render;

import javax.servlet.http.HttpSession;



/**
 *
 * @author BARIS
 */
public class Scope {
    public String url;
    public String query;
    public String file;    
    public Object data;
    public HttpSession session;
}
