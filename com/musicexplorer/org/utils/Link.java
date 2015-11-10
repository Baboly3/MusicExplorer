/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.musicexplorer.org.utils;

import java.util.Objects;

/**
 *
 * @author Babak Tamjidi baboly@gmail.com
 */
public class Link {

    private String uri;
    private String rel;

    public Link() {
    }

    public Link(String uri, String rel) {
        this.uri = uri;
        this.rel = rel;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.uri);
        hash = 97 * hash + Objects.hashCode(this.rel);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Link other = (Link) obj;
        return true;
    }
    
    
}
