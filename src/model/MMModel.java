package model;



import java.lang.reflect.Method;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author BARIS
 */
public class MMModel extends ModelBase {

    private final String methodName;
    
    public MMModel(String methodName) {
        this.methodName = methodName;
    }
    
    private final Class noparams[] = {};
    
    @Override
    public Object perform() throws Exception {
        Method method = this.getClass().getDeclaredMethod(this.methodName, noparams);
        //return method.invoke(this, (Object) null);
        return method.invoke(this);
    }
    
}
