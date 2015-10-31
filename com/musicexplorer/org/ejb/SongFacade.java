/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.musicexplorer.org.ejb;

import com.musicexplorer.org.entity.Artist;
import com.musicexplorer.org.entity.Playlist;
import com.musicexplorer.org.entity.Song;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Babak Tamjidi  baboly@gmail.com
 */
@Stateless
public class SongFacade extends AbstractFacade<Song> {
    @PersistenceContext(unitName = "MusicExplorerPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SongFacade() {
        super(Song.class);
    }

    public EntityManager getEm() {
        return em;
    }
    
    public List<Song> getSongsByArtist(int id){
        Query query = em.createNamedQuery("Song.findByArtist", Song.class);
        query.setParameter("artist", getArtist(id));
        List<Song> ArtistSongList = query.getResultList();
        return ArtistSongList;
    }
    
    public Artist getArtist(int id){
        Artist artist = em.find(Artist.class, id);
        return artist;
    }
    
    public List<Song> getSongsByPlaylist(int id){
        Query query = em.createNamedQuery("Song.findByPlaylistId", Playlist.class);
        query.setParameter("playlistCollection", getPlaylist(id));
        List<Song> PlaylistSongList = (List<Song>) query.getResultList();
        return PlaylistSongList;
    }
    public Playlist getPlaylist(int id){
        Playlist playlist = em.find(Playlist.class, id);
        return playlist;
    }

}
