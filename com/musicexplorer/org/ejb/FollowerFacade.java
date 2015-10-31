/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.musicexplorer.org.ejb;

import com.musicexplorer.org.entity.Follower;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Babak Tamjidi  baboly@gmail.com
 */
@Stateless
public class FollowerFacade extends AbstractFacade<Follower> {
    @PersistenceContext(unitName = "MusicExplorerPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FollowerFacade() {
        super(Follower.class);
    }

    public EntityManager getEm() {
        return em;
    }
    
    

}
