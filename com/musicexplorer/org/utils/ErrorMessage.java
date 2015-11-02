/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.musicexplorer.org.utils;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Babak Tamjidi  baboly@gmail.com
 */
@XmlRootElement
public class ErrorMessage {

    private String errorMessage;
    private int errorCode;
    private String documentationLink;

    public ErrorMessage() {
    }

    public ErrorMessage(String errorMessage, int errorCode, String documentationLink) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.documentationLink = documentationLink;
        System.out.println("Errormessage xxx");
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getDocumentationLink() {
        return documentationLink;
    }

    public void setDocumentationLink(String documentationLink) {
        this.documentationLink = documentationLink;
    }
    
    
}
