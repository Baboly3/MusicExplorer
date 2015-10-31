/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.musicexplorer.controllers;

import com.musicexplorer.org.ejb.ProfileFacade;
import com.musicexplorer.org.entity.Profile;
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
@Path("profiles")
public class ProfileResource{

    @EJB
    ProfileFacade pm;
    @EJB
    GenericLinkWrapperFactory<Profile> genericLWF;

    @Context
    UriInfo uriInfo;

    @GET
    @Path("{id}/")
    public Response getProfile(@Context UriInfo uriInfo, @PathParam("id") int id) {

        List<Profile> list = new ArrayList<Profile>();
        list.add(pm.find(id));
        List<GenericLinkWrapper> profile = genericLWF.getById(list);
        this.uriInfo = uriInfo;
        String uri2 = uriInfo.getBaseUriBuilder().path(ProfileResource.class).path("playlists").build().toString();
        int size = 0;
        for (GenericLinkWrapper<Profile> pl : profile) {
            size++;
            System.out.println("Size :" + size);
            String uri = this.uriInfo.getBaseUriBuilder().
                    path(ProfileResource.class).
                    path(Integer.toString(pl.getEntity().getId())).path("playlists").
                    build().toString();
            pl.setLink(new Link(uri, "User playlists"));
        }
        return Response.status(Status.OK).entity(profile).build();

    }

    @GET
    public Response getProfiles(@Context UriInfo uriInfo) {
        this.uriInfo = uriInfo;
        Profile profile = new Profile();
        List<GenericLinkWrapper> profileList = genericLWF.getAll(profile);

        for (GenericLinkWrapper<Profile> gl : profileList) {
            String uri = this.uriInfo.getBaseUriBuilder().
                    path(ProfileResource.class).
                    path(Integer.toString(gl.getEntity().getId()))
                    .build().toString();
            gl.setLink(new Link(uri, "profile"));
        }
        return Response.status(Status.OK).entity(profileList).build();

    }

    @POST
    public Profile addProfile(Profile profile) {
        Profile mProfile = new Profile();
        mProfile = profile;
        pm.create(mProfile);
        return profile;
    }

    @DELETE
    @Path("{id}")
    public void delProfile(@PathParam("id") int id) {
        if (pm.find(id) != null) {
            pm.remove(pm.find(id));
        }
    }

    @PUT
    @Path("{id}")
    public Response editProfile(Profile profile, @PathParam("id") int id) {
        profile.setId(id);
        if (pm.find(id) != null) {
            pm.edit(profile);
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Path("{id}/playlists/")
    public PlaylistResource getPlaylists() {
        return new PlaylistResource();
    }

}