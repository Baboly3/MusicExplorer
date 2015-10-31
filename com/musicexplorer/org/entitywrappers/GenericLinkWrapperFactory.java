/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.musicexplorer.org.entitywrappers;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Babak Tamjidi baboly@gmail.com
 */
@Stateless
public class GenericLinkWrapperFactory<T> {

    @PersistenceContext(unitName = "MusicExplorerPU")
    private EntityManager em;

    public List<GenericLinkWrapper> getAll(T entity) {

        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entity.getClass()));
        List<T> entityList = em.createQuery(cq).getResultList();
        List<GenericLinkWrapper> wrappedEntityList = new ArrayList<GenericLinkWrapper>();
        for (Object b : entityList) {
            wrappedEntityList.add(new GenericLinkWrapper((T) b, null));
        }
        return wrappedEntityList;
    }
    public List<GenericLinkWrapper> getById(List<T> list) {
        List<GenericLinkWrapper> wrappedEntityListById = new ArrayList<GenericLinkWrapper>();
        for(Object b : list){
            wrappedEntityListById.add(new GenericLinkWrapper((T) b, null));
        }
        return wrappedEntityListById;
    }
}
