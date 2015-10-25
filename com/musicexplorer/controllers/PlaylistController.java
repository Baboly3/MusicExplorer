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
    @Path("{pid}/")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Song> getPlaylist(@PathParam("pid") int id) {
        Playlist playlist = playlistManager.find(id);
        Collection<Song> songs = playlist.getSongCollection();
        return songs;
    }

    @GET
    @Path("{pid}/{songid}/")
    @Produces(MediaType.APPLICATION_JSON)
    public Song getSongInPlaylist(@PathParam("songid") int songid) {
        return songManager.find(songid);
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
        if (profileManager.find(id) != null) {
            playlist.setProfileid(profileManager.find(id));
        }
        List<Song> so = new ArrayList<>();
        Collection<Song> songs = playlist.getSongCollection();
        for (Song s : songs) {
            so.add(songManager.find(s.getId()));
        }
        playlist.setSongCollection(so);
        playlistManager.create(playlist);
        return playlist;
    }

    @PUT
    @Path("{pid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addSongsToPlaylist(Playlist playlist, @PathParam("pid") int pid) {
        Playlist mPlaylist = new Playlist();

        if (playlistManager.find(pid) != null) {
            mPlaylist = playlistManager.find(pid);
        }
        Collection<Song> updatedPlaylist = mPlaylist.getSongCollection();
        Collection<Song> songs = playlist.getSongCollection();
        for (Song s : songs) {
            updatedPlaylist.add(songManager.find(s.getId()));
        }
        mPlaylist.setSongCollection(updatedPlaylist);
        playlistManager.edit(mPlaylist);
        return "Updated";
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
