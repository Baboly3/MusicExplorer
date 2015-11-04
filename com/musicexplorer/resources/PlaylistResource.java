/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.musicexplorer.resources;

import com.musicexplorer.interfaces.MainService;
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

    
    MainService mainService;
    GenericLinkWrapperFactory<Playlist> genericLWF;

    @Context
    UriInfo uriInfo;

    public PlaylistResource() {
        try { 
            String lookupName = "java:module/MainServiceFacade"; 
            String lookupName2 = "java:module/GenericLinkWrapperFactory"; 
             mainService = (MainService) InitialContext.doLookup(lookupName); 
             genericLWF = (GenericLinkWrapperFactory) InitialContext.doLookup(lookupName2); 
         } catch (NamingException e) { 
             System.out.println("EXCEPTION MESSAGE:::" + e.getMessage()); 
         } 
     } 

    

    public PlaylistResource(MainService mainService, GenericLinkWrapperFactory genericLinkWrapperFactory) {
        this.mainService = mainService;
        this.genericLWF = genericLinkWrapperFactory;  
    }

    @GET
    @Path("{playlistId}/")
    public Response getPlaylist(@Context UriInfo uriInfo, @PathParam("playlistId") int id) {
        System.out.println("playlistID: " + id);
        Playlist playlist = mainService.getPlaylistService().find(id);
        List<Song> songs = playlist.getSongCollection();
        GenericLinkWrapperFactory<Song> genericLWFSong = new GenericLinkWrapperFactory<>();
        List<GenericLinkWrapper> playlistLinkSongs = genericLWFSong.getById(songs);
        this.uriInfo = uriInfo;
        for (GenericLinkWrapper<Song> songList : playlistLinkSongs) {
            String uri = this.uriInfo.getBaseUriBuilder().path(PlaylistResource.class).
                    path(Integer.toString(id)).
                    path("songs").
                    path(Integer.toString(songList.getEntity().getId())).
                    build().toString();
            songList.setLink(new Link(uri, "Playlist song"));
        }
        return Response.ok().entity(playlistLinkSongs).build();
    }

    @GET
    public Response getPlaylists(@Context UriInfo uriInfo, @PathParam("profileId") int profileId) {
        System.out.println("profileID " + profileId);
        if (profileId != 0) {
            List<Playlist> list = mainService.getPlaylistService().getPlaylistByProfileId(profileId); 
            List<GenericLinkWrapper> playlists = genericLWF.getById(list);
            this.uriInfo = uriInfo;
            for (GenericLinkWrapper<Playlist> pl : playlists) {
                String uri = this.uriInfo.getBaseUriBuilder().
                        path(PlaylistResource.class).
                        path(Integer.toString(pl.getEntity().getId())).
                        build().toString();
                pl.setLink(new Link(uri, "User playlist"));
            }
            return Response.status(Status.OK).entity(playlists).build();
        }
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

    @POST
    public Response addPlaylist(Playlist playlist, @PathParam("profileId") int id) {
        if (mainService.getPlaylistService().getProfile(id) != null) {
            playlist.setProfileid(mainService.getPlaylistService().getProfile(id));
        }
        List<Song> so = new ArrayList<>();
        List<Song> songs = playlist.getSongCollection();
        if (songs != null) {
            for (Song s : songs) {
                so.add(mainService.getPlaylistService().getSong(s.getId()));
            }
        }
        playlist.setSongCollection(so);
        mainService.getPlaylistService().create(playlist);
        return Response.ok().entity(playlist).build();
    }

    @PUT
    @Path("{playlistId}")
    public Response addSongsToPlaylist(Playlist playlist, @PathParam("playlistId") int pid) {
        Playlist mPlaylist = new Playlist();

        if (mainService.getPlaylistService().find(pid) != null) {
            mPlaylist = mainService.getPlaylistService().find(pid);
        if (playlist.getPlaylist() == null){
            playlist.setPlaylist(mPlaylist.getPlaylist());
        }
        List<Song> updatedPlaylist = mPlaylist.getSongCollection();
        List<Song> songs = playlist.getSongCollection();
        for (Song s : songs) {
            updatedPlaylist.add(mainService.getPlaylistService().getSong(s.getId()));
        }
        mPlaylist.setSongCollection(updatedPlaylist);
        mainService.getPlaylistService().edit(mPlaylist);
        
        return Response.ok().entity(mPlaylist).build(); 
    }
        return Response.status(Status.BAD_REQUEST).build();
    }

    @DELETE
    @Path("{playlistId}")
    public Response delPlaylist(@PathParam("playlistId") int id) {
        if (mainService.getPlaylistService().find(id) != null) {
            mainService.getPlaylistService().remove(mainService.getPlaylistService().find(id));
            Response.ok().build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    @Path("{playlistId}/songs/")
    public SongResource getSongs() {
        GenericLinkWrapperFactory<Song> glwfs = new GenericLinkWrapperFactory<Song>();
        return new SongResource(mainService , glwfs);
    }
}
