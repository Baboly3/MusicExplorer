/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.musicexplorer.org.entitywrappers;

import com.musicexplorer.org.utils.Link;

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
}
