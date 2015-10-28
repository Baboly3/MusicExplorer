/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.musicexplorer.controllers;

import com.musicexplorer.org.ejb.ArtistFacade;
import com.musicexplorer.org.ejb.SongFacade;
import com.musicexplorer.org.entity.Artist;
import com.musicexplorer.org.entity.Song;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
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
@Path("songs")
public class SongController {

    @EJB
    SongFacade sm;
    @EJB
    ArtistFacade am;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Song> getSongs() {
        return sm.findAll();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/")
    public Song getSong(@PathParam("id") int id) {
        return sm.find(id);
    }

    @POST
    @Consumes(value = {MediaType.APPLICATION_JSON, MediaType.MULTIPART_FORM_DATA})
    public Song addSong(Song song, @FormParam("artistId") int id) {
        Song mSong = new Song();
        mSong = song;
        Artist artist = am.find(id);
        mSong.setArtist(artist);
        sm.create(mSong);
        return mSong;
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void delSong(@PathParam("id") int id) {
        if (sm.find(id) != null) {
            sm.remove(sm.find(id));
        }
    }

    @PUT
    @Path("{id}")
    public Response editSong(Song song, @PathParam("id") int id) {
        song.setId(id);
        if (sm.find(id) != null) {
            sm.edit(song);
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }
}
