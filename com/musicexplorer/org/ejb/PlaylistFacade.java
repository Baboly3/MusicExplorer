/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.musicexplorer.org.ejb;

import com.musicexplorer.org.entity.Playlist;
import com.musicexplorer.org.entity.Profile;
import com.musicexplorer.org.entity.Song;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Babak Tamjidi baboly@gmail.com
 */
@Stateless
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

    public List<Playlist> getPlaylistByProfileId(int id) {
        Query query = em.createNamedQuery("Playlist.findByProfileId", Playlist.class);
        query.setParameter("profileid", getProfile(id));
        List<Playlist> list = query.getResultList();
        return list;
    }
 
    public Profile getProfile(int id){
        Profile profile = em.find(Profile.class, id);
        return profile;
    }
    public Song getSong(int id){
        Song song = em.find(Song.class, id);
        return song;      
    }
}
