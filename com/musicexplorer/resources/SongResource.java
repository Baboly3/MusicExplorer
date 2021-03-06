/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.musicexplorer.resources;

import com.musicexplorer.exception.DataNotFoundException;
import com.musicexplorer.interfaces.MainService;
import com.musicexplorer.org.entity.Artist;
import com.musicexplorer.org.entity.Song;
import com.musicexplorer.org.entitywrappers.GenericLinkWrapper;
import com.musicexplorer.org.entitywrappers.GenericLinkWrapperFactory;
import com.musicexplorer.org.utils.Cashing;
import com.musicexplorer.org.utils.Link;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
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
    MainService mainService;

    @EJB
    GenericLinkWrapperFactory<Song> genericLWF;

    @Context
    UriInfo uriInfo;

    public SongResource() {
    }

//    public SongResource(MainService mainService, GenericLinkWrapperFactory genericLinkWrapperFactory) {
//        this.mainService = mainService;
//        this.genericLWF = genericLinkWrapperFactory;
//    }

    @GET
    public Response getSongs(@Context UriInfo uriInfo, @PathParam("artistId") int aId, @PathParam("playlistId") int pId, @Context Request request) {
        System.out.println("artistid " + aId + "/nplaylistId " + pId);
        List<Song> list = new ArrayList<Song>();
        List<GenericLinkWrapper> songLinkList = new ArrayList<GenericLinkWrapper>();
        Cashing cashing = new Cashing();
        this.uriInfo = uriInfo;
        if (aId != 0) {
            if (mainService.getArtistService().find(aId) == null) {
                throw new DataNotFoundException("The artist id doesnt exist");            
            }
            list = mainService.getSongService().getSongsByArtist(aId);
            songLinkList = genericLWF.getById(list);
            for (GenericLinkWrapper<Song> artistSonglist : songLinkList) {
                String uri = this.uriInfo.getBaseUriBuilder().
                        path(SongResource.class).
                        path(Integer.toString(artistSonglist.getEntity().getId())).
                        build().toString();
                artistSonglist.setLink(new Link(uri, "Artist Song"));
            }

            CacheControl cc = cashing.setCacheControl(86400, true, songLinkList);

            Response.ResponseBuilder builder = request.evaluatePreconditions(cashing.getEntityTag());
            if (builder == null) {
                builder = Response.ok(songLinkList);
                builder.tag(cashing.getEntityTag());
            }
            builder.cacheControl(cc);
            return builder.build();

        }
        if (pId != 0) {
            if (mainService.getPlaylistService().find(pId) == null) {
                throw new DataNotFoundException("The playlist id doesnt exist");
            }
            list = mainService.getSongService().getSongsByPlaylist(pId);
            songLinkList = genericLWF.getById(list);
            for (GenericLinkWrapper<Song> playlistSongList : songLinkList) {
                String uri = this.uriInfo.getBaseUriBuilder().
                        path(SongResource.class).
                        path(Integer.toString(playlistSongList.getEntity().getId())).
                        build().toString();
                playlistSongList.setLink(new Link(uri, "Artist Song"));
            }
            CacheControl cc = cashing.setCacheControl(86400, true, songLinkList);
            Response.ResponseBuilder builder = request.evaluatePreconditions(cashing.getEntityTag());
            if (builder == null) {
                builder = Response.ok(songLinkList);
                builder.tag(cashing.getEntityTag());
            }
            builder.cacheControl(cc);
            return builder.build();
        }
        this.uriInfo = uriInfo;
        Song song = new Song();
        songLinkList = genericLWF.getAll(song);
        for (GenericLinkWrapper<Song> gl : songLinkList) {
            String uri = this.uriInfo.getBaseUriBuilder().
                    path(SongResource.class).
                    path(Integer.toString(gl.getEntity().getId()))
                    .build().toString();
            gl.setLink(new Link(uri, "songs"));
        }

        CacheControl cc = cashing.setCacheControl(10, true, songLinkList);
        Response.ResponseBuilder builder = request.evaluatePreconditions(cashing.getEntityTag());
        if (builder == null) {
            builder = Response.ok(songLinkList);
            builder.tag(cashing.getEntityTag());
        }
        builder.cacheControl(cc);
        return builder.build();

    }

    @GET
    @Path("{songId}")
    public Response getSong(@PathParam("songId") int id, @Context Request request) {

        if (mainService.getSongService().find(id) == null) {
            throw new DataNotFoundException("This id doesnt exist");
        }
        Song song = mainService.getSongService().find(id);
        int hashValue = song.hashCode();

        CacheControl cc = new CacheControl();
        cc.setMaxAge(86400);
        cc.setPrivate(true);

        EntityTag eTag = new EntityTag(String.valueOf(hashValue));

        Response.ResponseBuilder builder = request.evaluatePreconditions(eTag);
        //Cash resource did change
        if (builder == null) {
            builder = Response.ok(song);
            builder.tag(eTag);
        }
        builder.cacheControl(cc);
        return builder.build();

    }

    @POST
    public Response addSong(@Valid Song song, @PathParam("artistId") int id) {
        Song mSong = new Song();
        mSong = song;
        Artist artist = mainService.getSongService().getArtist(id);
        mSong.setArtist(artist);
        mainService.getSongService().create(mSong);
        return Response.ok().entity(mSong).build();
    }

    @DELETE
    @Path("{songId}")
    public Response delSong(@PathParam("songId") int songId, @PathParam("playlistId") int playlistId) {
        System.out.println("playlistid " + playlistId + "\n songid " + songId);
        if (playlistId != 0) {
            boolean removed = mainService.getSongService().removeSongFromPlaylist(songId, playlistId);
            if (removed) {
                return Response.status(Status.OK).build();
            }
            return Response.status(Status.BAD_REQUEST).build();
        }
        if (mainService.getSongService().find(songId) != null && playlistId == 0) {
            mainService.getSongService().remove(mainService.getSongService().find(songId));
            return Response.status(Status.NO_CONTENT).build();
        }
        return Response.status(Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("{songId}")
    public Response editSong(@Valid Song song, @PathParam("songId") int songId) {
        System.out.println("Song id: " + songId);
        if (mainService.getSongService().find(songId) == null) {
            throw new DataNotFoundException("This id doesnt exist");
        } 
            mainService.getSongService().edit(song);
            return Response.ok().build();
        }
}
