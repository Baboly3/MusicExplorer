/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.musicexplorer.controllers;

import com.musicexplorer.model.Artist;
import com.musicexplorer.model.Song;
import com.musicexplorer.org.ejb.ArtistFacade;
import com.musicexplorer.org.ejb.SongFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.persistence.Query;
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
@Path("artists")
public class ArtistController {

    @EJB
    ArtistFacade am;
    @EJB
    SongFacade sm;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/")
    public Artist getArtist(@PathParam("id") int id) {
        return am.find(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/songs/{songid}")
    public Song getArtistSong(@PathParam("id") int id, @PathParam("songid") int songId) {
        return sm.find(songId);
    }

    @GET
    @Path("/{id}/songs")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Song> getSong(@PathParam("id") int id) {
        Query query = am.getEm().createNamedQuery("Song.findByArtistid", Song.class);
        Artist artist = am.find(id);
        query.setParameter("artistId", artist);
        List<Song> songs = query.getResultList();
        return songs;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Artist> getArtists() {
        return am.findAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Artist addArtist(Artist artist) {
        Artist mArtist = new Artist();
        mArtist = artist;
        am.create(mArtist);
        return mArtist;
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void delArtist(@PathParam("id") int id) {
        if (am.find(id) != null) {
            am.remove(am.find(id));
        }
    }

    @PUT
    @Path("{id}")
    public Response editArtist(Artist artist, @PathParam("id") int id) {
        artist.setId(id);
        if (am.find(id) != null) {
            am.edit(artist);
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }
}
