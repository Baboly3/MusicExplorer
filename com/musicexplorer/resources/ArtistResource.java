/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.musicexplorer.resources;

import com.musicexplorer.exception.DataNotFoundException;
import com.musicexplorer.interfaces.MainService;
import com.musicexplorer.org.entity.Artist;
import com.musicexplorer.org.entitywrappers.GenericLinkWrapper;
import com.musicexplorer.org.entitywrappers.GenericLinkWrapperFactory;
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
import javax.ws.rs.container.ResourceContext;
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
    @Context
    ResourceContext rc;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{artistId}/")
    public Response getArtist(@PathParam("artistId") int id) {
        
        if(mainService.getArtistService().find(id) == null){
            throw new DataNotFoundException("This id doesnt exist!");
        }
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
        String uri2 = this.uriInfo.getBaseUriBuilder().path(ArtistResource.class).path(Integer.toString(id)).build().toString();
        GenericLinkWrapper pl = new GenericLinkWrapper();
        pl.setLink(new Link(uri2, "Self"));
        artist.add(pl);      
        return Response.status(Status.OK).entity(artist).build();
    }

    @GET
    public Response getArtists(@Context UriInfo uriInfo) {
        this.uriInfo = uriInfo;
        Artist artist = new Artist();
        List<GenericLinkWrapper> artistList = genericLWF.getAll(artist);
        int i = 0;
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
    public Artist addArtist(@Valid Artist artist) {
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
    public Response editArtist(@Valid Artist artist, @PathParam("artistId") int id) {      
            mainService.getArtistService().edit(artist);
            return Response.ok().build();
    }

    @Path("{artistId}/songs/")
    public SongResource getSongs() {
        return rc.getResource(SongResource.class);
    }
}
