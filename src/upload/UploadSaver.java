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
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.Part;

/**
 *
 * @author BARIS
 */
public class UploadSaver {
    private final Part part;
    private UploadControl control;
    

    public UploadSaver(Part part, UploadControl control ) throws IOException, ServletException {
        this.part = part;        
        this.control = control;
    }

    public void setControl(UploadControl control) {
        this.control = control;
    }

    public UploadControl getControl() {
        return control;
    }
    
    private void save(String fName) throws FileNotFoundException, IOException, IllegalUploadException {
        
        control.chek(part);
        
        OutputStream out = new FileOutputStream(fName);        
        InputStream content = part.getInputStream();
        
        int read;
        byte[] bytes = new byte[1024];
        while ((read = content.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }
        
        out.close();
        content.close();
    }
    
    public String saveBySubmittedName(String folder) throws IOException, FileNotFoundException, IllegalUploadException {
        String fname = part.getSubmittedFileName();
        save( folder + File.separator + fname );
        return fname;
    }
    
    public String saveByTimeStamp(String folder,String prefix) throws IOException, FileNotFoundException, IllegalUploadException {        
        
        String fname = prefix + (new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()))+"."+UploadControl.getExtension(part);
        save(folder + File.separator +fname);
        
        return fname;
    }
    
    public String saveByNewName(String folder,String newName) throws IOException, FileNotFoundException, IllegalUploadException {
        String fName = newName+"."+UploadControl.getExtension(part);
        save( folder + File.separator + fName );
        return fName;
    }
    
    
    
}
