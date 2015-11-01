/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.musicexplorer.controllers;

import com.musicexplorer.org.ejb.ArtistFacade;
import com.musicexplorer.org.ejb.SongFacade;
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
    ArtistFacade am;
    @EJB
    GenericLinkWrapperFactory<Song> genericLWF;

    @Context
    UriInfo uriInfo;

    public SongResource() {
        try {
            String lookupName = "java:module/SongFacade";
            String lookupName2 = "java:module/GenericLinkWrapperFactory";
            String lookupName3 = "java:module/ArtistFacade";
            sm = (SongFacade) InitialContext.doLookup(lookupName);
            genericLWF = (GenericLinkWrapperFactory) InitialContext.doLookup(lookupName2);
            am = (ArtistFacade) InitialContext.doLookup(lookupName3);
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
    @Path("{songId}/")
    public Response getSong(@Context UriInfo uriInfo, @PathParam("songId") int id
    ) {
        List<Song> list = new ArrayList<Song>();
        list.add(sm.find(id));
        List<GenericLinkWrapper> songLinkList = genericLWF.getById(list);
        this.uriInfo = uriInfo;
        String uri2 = uriInfo.getBaseUriBuilder().path(ProfileResource.class).path("playlists").build().toString();
        int size = 0;
        for (GenericLinkWrapper<Song> pl : songLinkList) {
            size++;
            System.out.println("Size :" + size);
            String uri = this.uriInfo.getBaseUriBuilder().
                    path(SongResource.class).
                    path(Integer.toString(pl.getEntity().getId())).path("Songs").
                    build().toString();
            pl.setLink(new Link(uri, "Artist Song"));
        }
        return Response.status(Status.OK).entity(songLinkList).build();
    }
//
//    @POST
//    public Song addSong(Song song, @PathParam("id") int id) {
//        Song mSong = new Song();
//        mSong = song;
//        Artist artist = am.find(id);
//        mSong.setArtist(artist);
//        sm.create(mSong);
//        return mSong;
//    }
////
    @DELETE
    @Path("{songId}")
    public Response delSong(@PathParam("songId") int songId, @PathParam("playlistId") int playlistId) {
        System.out.println("playlistid " + playlistId + "\n songid " + songId);
        if(playlistId != 0){
            boolean removed = sm.removeSongFromPlaylist(songId, playlistId);
            if(removed){
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
//
//    @PUT
//    @Path("{id}")
//    public Response editSong(Song song, @PathParam("id") int id) {
//        song.setId(id);
//        if (sm.find(id) != null) {
//            sm.edit(song);
//            return Response.ok().build();
//        } else {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//
//    }

    //    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("{id}/songs/{songid}")
//    public Song getArtistSong(@PathParam("id") int id, @PathParam("songid") int songId) {
//        return sm.find(songId);
//    }
//    @GET
//    public List<Song> getSong(@PathParam("id") int id) {
//        Query query = sm.getEm().createNamedQuery("Song.findByArtist", Song.class);
//        Artist artist = am.find(id);
//        query.setParameter("artist", artist);
//        List<Song> songs = query.getResultList();
//        System.out.println("IN GET!!!");
//        return songs;
//    }
    //    @POST
//    @Path("{id}/songs")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Song addSongToArtist(Song song, @PathParam("id") int id) {
//        Song mSong = new Song();
//        mSong = song;
//        Artist artist = am.find(id);
//        mSong.setArtist(artist);
//        sm.create(mSong);
//        return mSong;
//    }
    //    @DELETE
//    @Path("{id}/songs/{songId}")
//    public void delArtistSong(@PathParam("songId") int songId) {
//        if (am.find(songId) != null) {
//            am.remove(am.find(songId));
//        }
//    }
}
