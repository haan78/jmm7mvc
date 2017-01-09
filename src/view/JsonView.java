package view;

import com.google.gson.Gson;
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
public class JsonView extends ViewBase {

    public JsonView(ModelBase m) {
        super(m);
    }

    @Override
    protected void showContent(Object data) {
        Gson g = new Gson();
        try {
            HttpServletResponse r = getResponse();
            r.setHeader("Content-Type", "application/json; charset=utf-8");
            r.getWriter().print(g.toJson(data));
        } catch (IOException ex) {
            this.showError(ex);
        }
    }
    
}
