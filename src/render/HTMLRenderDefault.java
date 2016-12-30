/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package render;

/**
 *
 * @author BARIS
 */
public class HTMLRenderDefault extends HTMLRenderBase {

    @Override
    protected String customReplacementMethod(String mn, Object[] args) {
        return "Unknown method : "+mn;
    }
    
}
