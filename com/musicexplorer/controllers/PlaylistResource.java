/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.musicexplorer.controllers;

import com.musicexplorer.org.ejb.PlaylistFacade;
import com.musicexplorer.org.ejb.ProfileFacade;
import com.musicexplorer.org.ejb.SongFacade;
import com.musicexplorer.org.entity.Playlist;
import com.musicexplorer.org.entity.Song;
import com.musicexplorer.org.entitywrappers.GenericLinkWrapper;
import com.musicexplorer.org.entitywrappers.GenericLinkWrapperFactory;
import com.musicexplorer.org.utils.Link;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Babak Tamjidi baboly@gmail.com
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("playlists")
public class PlaylistResource {

    @EJB
    PlaylistFacade playlistManager;
    @EJB
    ProfileFacade profileManager;
    @EJB
    SongFacade songManager;
    @EJB
    GenericLinkWrapperFactory<Playlist> genericLWF;

    @Context
    UriInfo uriInfo;

    public PlaylistResource() {
        try {
            String lookupName = "java:module/PlaylistFacade";
            String lookupName2 = "java:module/GenericLinkWrapperFactory";
            String lookupName3 = "java:module/ProfileFacade";
            playlistManager = (PlaylistFacade) InitialContext.doLookup(lookupName);
            genericLWF = (GenericLinkWrapperFactory) InitialContext.doLookup(lookupName2);
            profileManager = (ProfileFacade) InitialContext.doLookup(lookupName3);
        } catch (NamingException e) {
            System.out.println("EXCEPTION MESSAGE:::" + e.getMessage());
        }
    }

    @GET
    @Path("{pid}/")
    public Collection<Song> getPlaylist(@PathParam("pid") int id) {
        Playlist playlist = playlistManager.find(id);
        Collection<Song> songs = playlist.getSongCollection();
        return songs;
    }

    @GET
    public Response getPlaylistsById(@Context UriInfo uriInfo, @PathParam("id") int id) {
        Query query = playlistManager.getEm().createNamedQuery("Playlist.findByProfileId", Playlist.class);
        query.setParameter("profileid", profileManager.find(id));
        List<Playlist> list = query.getResultList();
        List<GenericLinkWrapper> playlists = genericLWF.getById(list);
        this.uriInfo = uriInfo;
        String uri2 = uriInfo.getBaseUriBuilder().path(PlaylistResource.class).path("all").build().toString();
        int size = 0;
        for (GenericLinkWrapper<Playlist> pl : playlists) {
            size++;
            System.out.println("Size :" + size);
            String uri = this.uriInfo.getBaseUriBuilder().
                    path(PlaylistResource.class).
                    path(Integer.toString(pl.getEntity().getId())).
                    build().toString();
            pl.setLink(new Link(uri, "User playlist"));
            if(playlists.size() == size){
                pl.setLink(new Link(uri2, "All playlists"));
            }
        }
        return Response.status(Status.OK).entity(playlists).build();
    }

    @GET
    @Path("all")
    public Response getPlaylists(@Context UriInfo uriInfo) {
        this.uriInfo = uriInfo;
        Playlist playlist = new Playlist();
        List<GenericLinkWrapper> playlists = genericLWF.getAll(playlist);

        for (GenericLinkWrapper<Playlist> pl : playlists) {
            String uri = this.uriInfo.getBaseUriBuilder().
                    path(PlaylistResource.class).
                    path(Integer.toString(pl.getEntity().getId())).
                    build().toString();
            pl.setLink(new Link(uri, "playlist"));
        }
        return Response.status(Status.OK).entity(playlists).build();
    }

    @GET
    @Path("{pid}/{songid}/")
    public Song getSongInPlaylist(@PathParam("songid") int songid) {
        return songManager.find(songid);
    }

    @POST
    public Playlist addPlaylist(Playlist playlist, @PathParam("id") int id) {
        if (profileManager.find(id) != null) {
            playlist.setProfileid(profileManager.find(id));
        }
        List<Song> so = new ArrayList<>();
        Collection<Song> songs = playlist.getSongCollection();
        if(songs != null){
        for (Song s : songs) {
            so.add(songManager.find(s.getId()));
        }
        }
        playlist.setSongCollection(so);
        playlistManager.create(playlist);
        return playlist;
    }

    @PUT
    @Path("{pid}")
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
    public void delPlaylist(@PathParam("pid") int id) {
        if (playlistManager.find(id) != null) {
            playlistManager.remove(playlistManager.find(id));
        }
    }
}
