/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.musicexplorer.resources;

import com.musicexplorer.interfaces.MainService;
import com.musicexplorer.org.entity.Artist;
import com.musicexplorer.org.entity.Song;
import com.musicexplorer.org.entitywrappers.GenericLinkWrapper;
import com.musicexplorer.org.entitywrappers.GenericLinkWrapperFactory;
import com.musicexplorer.org.utils.Link;
import java.util.ArrayList;
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
@Path("artists")
public class ArtistResource {


    @EJB
    MainService mainService;
    @EJB
    GenericLinkWrapperFactory<Artist> genericLWF;
    @Context
    UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{artistId}/")
    public Response getArtist(@PathParam("artistId") int id) {
        List<Artist> list = new ArrayList<Artist>();
        list.add(mainService.getArtistService().find(id));
        List<GenericLinkWrapper> artist = genericLWF.getById(list);
        for (GenericLinkWrapper<Artist> pl : artist) {

            String uri = uriInfo.getBaseUriBuilder().
                    path(ArtistResource.class).
                    path(Integer.toString(pl.getEntity().getId())).path("songs").
                    build().toString();
            pl.setLink(new Link(uri, "Artist songs"));
        }
        return Response.status(Status.OK).entity(artist).build();
    }

    @GET
    public Response getArtists(@Context UriInfo uriInfo) {
        this.uriInfo = uriInfo;
        Artist artist = new Artist();
        List<GenericLinkWrapper> artistList = genericLWF.getAll(artist);

        for (GenericLinkWrapper<Artist> gl : artistList) {
            String uri = this.uriInfo.getBaseUriBuilder().
                    path(ArtistResource.class).
                    path(Integer.toString(gl.getEntity().getId())).
                    build().toString();
            gl.setLink(new Link(uri, "artist"));
        }
        return Response.status(Status.OK).entity(artistList).build();

    }

    @POST
    public Artist addArtist(Artist artist) {
        Artist mArtist = new Artist();
        mArtist = artist;
        mainService.getArtistService().create(mArtist);
        return mArtist;
    }

    @DELETE
    @Path("{artistId}")
    public void delArtist(@PathParam("artistId") int id) {
        if (mainService.getArtistService().find(id) != null) {
            mainService.getArtistService().remove(mainService.getArtistService().find(id));
        }
    }

    @PUT
    @Path("{artistId}")
    public Response editArtist(Artist artist, @PathParam("artistId") int id) {
        
        if (mainService.getArtistService().find(id) != null) {
            Artist mArtist = new Artist();
            mArtist = mainService.getArtistService().find(id);
            artist.setId(id);
        if(artist.getGenrer() == null){
            artist.setGenrer(mArtist.getGenrer());
        }    
        if(artist.getHistory() == null){
            artist.setHistory(mArtist.getHistory());
        }
        if(artist.getName() == null){
            artist.setName(mArtist.getName());
        }
            mainService.getArtistService().edit(artist);
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Path("{artistId}/songs/")
    public SongResource getSongs() {
        GenericLinkWrapperFactory<Song> glwfs = new GenericLinkWrapperFactory<Song>();
        return new SongResource(mainService, glwfs );
    }
}