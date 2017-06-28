/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package upload;

import javax.servlet.http.Part;

/**
 *
 * @author BARIS
 */
public class UploadControl {
    private long maximumSize = 10485760; //10 Mb
    private long minimumSize = 0;
    private String[] allowedExtensions = new String[0];
    private String[] notAllowedExtensions = new String[0];

    public UploadControl() {
        
    }
    
    public static String getExtension(Part p) {
        int i = p.getSubmittedFileName().lastIndexOf('.');
        if ( (i > 0) && ( i<p.getSubmittedFileName().length() ) ) {
            return p.getSubmittedFileName().substring(i+1).toLowerCase();
        }
        return "";
    }

    public void chek( Part p ) throws UnexpectedUploadException {
        if ( p.getSize() > maximumSize ) throw new UnexpectedUploadException("Uploaded file size is too big");
        if ( p.getSize() < minimumSize ) throw new UnexpectedUploadException("Uploaded file size is too small");
        if ( (allowedExtensions!= null) && ( allowedExtensions.length>0 ) ) {
            for (int i=0; i<allowedExtensions.length; i++) {
                if ( getExtension(p).equals(allowedExtensions[i]) ) return;
            }
            throw new UnexpectedUploadException("Uploaded file extension is not allowed");
        }
        if ( (notAllowedExtensions !=null) && (notAllowedExtensions.length>0) ) {
            for (int i=0; i<allowedExtensions.length; i++) {
                if ( getExtension(p).equals(notAllowedExtensions[i]) ) throw new UnexpectedUploadException("Uploaded file extension is not allowed");
            }
        }
    }

    public void setMaximumSize(long maximumSize) {
        this.maximumSize = maximumSize;
    }

    public void setMinimumSize(long minimumSize) {
        this.minimumSize = minimumSize;
    }

    public void setAllowedExtensions(String[] allowedExtensions) {
        this.allowedExtensions = allowedExtensions;
    }

    public void setNotAllowedExtensions(String[] notAllowedExtensions) {
        this.notAllowedExtensions = notAllowedExtensions;
    }

    public String[] getAllowedExtensions() {
        return allowedExtensions;
    }

    public long getMaximumSize() {
        return maximumSize;
    }

    public long getMinimumSize() {
        return minimumSize;
    }

    public String[] getNotAllowedExtensions() {
        return notAllowedExtensions;
    }    
    
}
