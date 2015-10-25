/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.musicexplorer.org.ejb;

import com.musicexplorer.model.Playlist;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Babak Tamjidi  baboly@gmail.com
 */
@Stateless
@TransactionAttribute(REQUIRED)
public class PlaylistFacade extends AbstractFacade<Playlist> {
    @PersistenceContext(unitName = "MusicExplorerPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PlaylistFacade() {
        super(Playlist.class);
    }

    public EntityManager getEm() {
        return em;
    }
 
}
