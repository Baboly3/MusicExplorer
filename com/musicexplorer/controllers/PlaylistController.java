/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.musicexplorer.controllers;

import com.musicexplorer.model.Playlist;
import com.musicexplorer.model.Song;
import com.musicexplorer.org.ejb.PlaylistFacade;
import com.musicexplorer.org.ejb.ProfileFacade;
import com.musicexplorer.org.ejb.SongFacade;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Babak Tamjidi baboly@gmail.com
 */
@Path("profiles/{id}/playlists")
public class PlaylistController {

    @EJB
    PlaylistFacade playlistManager;
    @EJB
    ProfileFacade profileManager;
    @EJB
    SongFacade songManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{pid}/")
    public Collection<Song> getPlaylist(@PathParam("pid") int id) {
        Playlist playlist = playlistManager.find(id);
        Collection<Song> songs = playlist.getSongCollection();
        List<Song> song = new ArrayList();
        for (Song s : songs) {
            song.add(songManager.find(s.getId()));
        }
        return songs;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Playlist> getPlaylist() {
        return playlistManager.findAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Playlist addPlaylist(Playlist playlist, @PathParam("id") int id) {
        playlist.setProfileid(profileManager.find(id));    
        List<Song> so = new ArrayList<>();
        Collection<Song> songs = playlist.getSongCollection();
        for (Song s : songs) {
            so.add(songManager.find(s.getId()));
        }
        playlist.setSongCollection(so);
        playlistManager.edit(playlist);
        return playlist;
    }

    @PUT
    @Path("{pid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Song> addSongsToPlaylist(Playlist playlist, @PathParam("pid") int pid, @PathParam("id") int id) {
        playlist.setId(pid);
        playlist.setProfileid(profileManager.find(id));
        List<Song> so = new ArrayList<>();
        Collection<Song> songs = playlist.getSongCollection();
        for (Song s : songs) {
            so.add(songManager.find(s.getId()));
        }
        playlist.setSongCollection(so);
        playlistManager.edit(playlist);
        return so;
    }

    @DELETE
    @Path("{pid}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void delPlaylist(@PathParam("pid") int id) {
        if (playlistManager.find(id) != null) {
            playlistManager.remove(playlistManager.find(id));
        }
    }
}
