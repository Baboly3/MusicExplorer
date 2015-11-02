/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.musicexplorer.controllers;

import com.musicexplorer.org.ejb.SongFacade;
import com.musicexplorer.org.entity.Artist;
import com.musicexplorer.org.entity.Song;
import static com.musicexplorer.org.entity.Song_.id;
import com.musicexplorer.org.entitywrappers.GenericLinkWrapper;
import com.musicexplorer.org.entitywrappers.GenericLinkWrapperFactory;
import com.musicexplorer.org.utils.Link;
import java.util.ArrayList;
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
@Path("songs")
public class SongResource {

    @EJB
    SongFacade sm;
    @EJB
    GenericLinkWrapperFactory<Song> genericLWF;

    @Context
    UriInfo uriInfo;

    public SongResource() {
        try {
            String lookupName = "java:module/SongFacade";
            String lookupName2 = "java:module/GenericLinkWrapperFactory";
            sm = (SongFacade) InitialContext.doLookup(lookupName);
            genericLWF = (GenericLinkWrapperFactory) InitialContext.doLookup(lookupName2);
        } catch (NamingException e) {
            System.out.println("EXCEPTION MESSAGE:::" + e.getMessage());
        }
    }

    @GET
    public Response getSongs(@Context UriInfo uriInfo, @PathParam("artistId") int aId, @PathParam("playlistId") int pId) {
        System.out.println("artistid " + aId + "/nplaylistId " + pId);
        List<Song> list = new ArrayList<Song>();
        List<GenericLinkWrapper> songLinkList = new ArrayList<GenericLinkWrapper>();
        this.uriInfo = uriInfo;
        if (aId != 0) {
            list = sm.getSongsByArtist(aId);
            songLinkList = genericLWF.getById(list);
            for (GenericLinkWrapper<Song> artistSonglist : songLinkList) {
                String uri = this.uriInfo.getBaseUriBuilder().
                        path(SongResource.class).
                        path(Integer.toString(artistSonglist.getEntity().getId())).
                        build().toString();
                artistSonglist.setLink(new Link(uri, "Artist Song"));
            }
            return Response.ok().entity(songLinkList).build();
        }
        if (pId != 0) {
            list = sm.getSongsByPlaylist(pId);
            songLinkList = genericLWF.getById(list);
            for (GenericLinkWrapper<Song> playlistSongList : songLinkList) {
                String uri = this.uriInfo.getBaseUriBuilder().
                        path(SongResource.class).
                        path(Integer.toString(playlistSongList.getEntity().getId())).
                        build().toString();
                playlistSongList.setLink(new Link(uri, "Artist Song"));
            }
            return Response.ok().entity(songLinkList).build();
        }
        this.uriInfo = uriInfo;
        Song song = new Song();
        List<GenericLinkWrapper> songList = genericLWF.getAll(song);
        for (GenericLinkWrapper<Song> gl : songList) {
            String uri = this.uriInfo.getBaseUriBuilder().
                    path(SongResource.class).
                    path(Integer.toString(gl.getEntity().getId()))
                    .build().toString();
            gl.setLink(new Link(uri, "songs"));
        }
        return Response.status(Status.OK).entity(songList).build();
    }

    @GET
    @Path("{songId}")
    public Response getSong(@PathParam("songId") int id) {
        Song song = sm.find(id);

//        List<Song> list = new ArrayList<Song>();
//        list.add(sm.find(id));
//        List<GenericLinkWrapper> songLinkList = genericLWF.getById(list);
//        this.uriInfo = uriInfo;
//        String uri2 = uriInfo.getBaseUriBuilder().path(ProfileResource.class).path("playlists").build().toString();
//        int size = 0;
//        for (GenericLinkWrapper<Song> pl : songLinkList) {
//            size++;
//            System.out.println("Size :" + size);
//            String uri = this.uriInfo.getBaseUriBuilder().
//                    path(SongResource.class).
//                    path(Integer.toString(pl.getEntity().getId())).
//                    build().toString();
//            pl.setLink(new Link(uri, "Artist Song"));
//        }
        return Response.status(Status.OK).entity(song).build();
    }

    @POST
    public Response addSong(Song song, @PathParam("artistId") int id) {
        Song mSong = new Song();
        mSong = song;
        Artist artist = sm.getArtist(id);
        mSong.setArtist(artist);
        sm.create(mSong);
        return Response.ok().entity(mSong).build();
    }

    @DELETE
    @Path("{songId}")
    public Response delSong(@PathParam("songId") int songId, @PathParam("playlistId") int playlistId) {
        System.out.println("playlistid " + playlistId + "\n songid " + songId);
        if (playlistId != 0) {
            boolean removed = sm.removeSongFromPlaylist(songId, playlistId);
            if (removed) {
                return Response.status(Status.OK).build();
            }
            return Response.status(Status.BAD_REQUEST).build();
        }
        if (sm.find(songId) != null && playlistId == 0) {
            sm.remove(sm.find(id));
            return Response.status(Status.NO_CONTENT).build();
        }
        return Response.status(Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("{songId}")
    public Response editSong(Song song, @PathParam("songId") int songId) {
        System.out.println("Song id: " + songId);
        
        if (sm.find(songId) != null) {
            Song mSong = sm.find(songId);
            song.setId(songId);
            if (song.getTitle() == null) {
                song.setTitle(mSong.getTitle());
            }
            if (song.getDuration() == null) {
                song.setDuration(mSong.getDuration());
            }
            sm.edit(song);
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
