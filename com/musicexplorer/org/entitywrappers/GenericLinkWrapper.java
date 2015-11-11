/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.musicexplorer.org.entitywrappers;

import com.musicexplorer.org.utils.Link;
import java.util.Objects;

/**
 *
 * @author Babak Tamjidi  baboly@gmail.com
 */
public class GenericLinkWrapper<T> {

    private T entity;
    private Link link;

    public GenericLinkWrapper() {
    }

    public GenericLinkWrapper(T entity, Link link) {
        this.entity = entity;
        this.link = link;
    }
    
    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }  

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.entity);
        hash = 41 * hash + Objects.hashCode(this.link);
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
        final GenericLinkWrapper<?> other = (GenericLinkWrapper<?>) obj;
        return true;
    }
    
    
}
