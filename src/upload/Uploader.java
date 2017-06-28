/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package upload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 *
 * @author BARIS
 */
public class Uploader {
    private final Collection<Part> uploads;
    private UploadControl control;
    

    public Uploader(HttpServletRequest request, UploadControl control ) throws IOException, ServletException {
        this.uploads = request.getParts();        
        this.control = control;
    }

    public void setControl(UploadControl control) {
        this.control = control;
    }

    public UploadControl getControl() {
        return control;
    }    
    
    private Part getPart(String pName) {
        for ( Part p : uploads ) {
            if (p.getName().equals(pName)) return p;
        }
        return null;
    }
    
    public String getName(String pName) {
        return getPart(pName).getSubmittedFileName();
    }
    
    public long getSize(String pName) {
        return getPart(pName).getSize();
    }
    
    private void save(Part p,String fName) throws FileNotFoundException, IOException, UnexpectedUploadException {
        
        control.chek(p);
        
        OutputStream out = new FileOutputStream(fName);        
        InputStream content = p.getInputStream();
        
        int read;
        byte[] bytes = new byte[1024];
        while ((read = content.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }
        
        out.close();
        content.close();
    }
    
    public void saveBySubmittedName(String pName,String folder) throws IOException, FileNotFoundException, UnexpectedUploadException {
        save( getPart(pName) ,folder + File.separator + getName(pName) );
    }
    
    public String saveByTimeStamp(String pName,String folder,String prefix) throws IOException, FileNotFoundException, UnexpectedUploadException {
        String name = folder + File.separator + prefix;
        
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        
        name+=timeStamp;
        
        String fname = getName(pName);
        String ext = "";

        int i = fname.lastIndexOf('.');
        if ( (i > 0) && ( i<fname.length() ) ) {
            ext = fname.substring(i+1);
        }       
       
        name+=".";
        name += ext.toLowerCase();
        
        save(getPart(pName), name);
        
        return name;
    }
    
    
    
}