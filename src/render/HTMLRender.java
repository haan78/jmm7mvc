package render;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletContext;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author BARIS
 */
public final class HTMLRender {

    private final Object scope;
    private final HTMLRenderMethods functions;
    private ServletContext context;

    public HTMLRender(Object scope, ServletContext context) {
        this.scope = scope;
        setContext(context);
        functions = new HTMLRenderMethods();
    }

    public HTMLRender(Object scope, ServletContext context, HTMLRenderMethods functions) {
        this.scope = scope;
        setContext(context);
        this.functions = functions;
    }
    
    public void setContext(ServletContext context) {
        this.context = context;
    }

    public Object getScope() {
        return scope;
    }

    private Object subElement(String varName) {
        String[] arr = varName.split(".");
        
        if ( arr.length == 0 ) {
            arr = new String[1];
            arr[0] = varName;
        }
        
        Object elm = scope;
        for (String fn : arr) {
            try {
                Field f = elm.getClass().getDeclaredField(fn);
                elm = f.get(elm);
            } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException ex) {
                return null;
            }
        }
        return elm;
    }

    private Object getValue(String expression) {
        String exp = expression.trim();
        if (exp.charAt(0) == '"') {
            exp = exp.substring(1, exp.length() - 1).trim();
        }
        if ((exp.charAt(0) == '%') && (exp.charAt(exp.length() - 1) == '%')) {
            exp = exp.substring(1, exp.length() - 1).trim();
            return subElement(exp);
        } else {
            String str = exp;
            Matcher m = Pattern.compile("\\%([_a-zA-Z0-9\\.]+)\\%").matcher(exp);
            while (m.find()) {
                str = str.replaceFirst(m.group(0), subElement(m.group(1) != null ? m.group(1) : m.group(2)).toString());
            }
            return str;
        }
    }

    private String getResult(String[] attributes) throws Exception {

        Object[] args = new Object[attributes.length - 1];        

        String mn = getValue(attributes[0]).toString();
        for (int i = 1; i < attributes.length; i++) {
            args[i-1] = getValue(attributes[i]);
        }

        Method method = functions.getClass().getDeclaredMethod(mn, new Class[0]);
        functions.setParameters(args);
        return method.invoke(functions).toString();

    }
    
    private String getFileAsString(ServletContext contx, String path) throws IOException {
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
    
    public String executeContent(String content) throws Exception {
        String reg1 = "\\<\\!\\-\\-\\[(.*?)\\]\\-\\-\\>|\\/\\*\\[(.*?)\\]\\*\\/";
        String c = content;
        Matcher m1 = Pattern.compile(reg1).matcher(c);
        int i = 0;
        while (m1.find()) {
            String reg2 = "\"(?:\\\\\\\\.|[^\\\\\"])*\"|\\S+";
            String line = m1.group(1) != null ? m1.group(1) : m1.group(2);
            Matcher m2 = Pattern.compile(reg2).matcher(line);
            List<String> l = new ArrayList<>();
            while (m2.find()) {
                l.add(m2.group(0));
            }
            String[] attributes = l.toArray(new String[l.size()]);
            c = c.replace(m1.group(0), getResult( attributes ) );
            i++;
        }

        if (i > 0) {
            c = executeContent(c);
        }

        return c;
    }

    public String executeFile( String file ) throws Exception {
        return executeContent(getFileAsString(context, file));        
    }

}
