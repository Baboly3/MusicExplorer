/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.musicexplorer.controllers;

import com.musicexplorer.org.ejb.ProfileFacade;
import com.musicexplorer.org.entity.Profile;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Babak Tamjidi baboly@gmail.com
 */
@Path("profiles")
public class ProfileController {


    @EJB
    ProfileFacade pm;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/")
    public Profile getProfile(@PathParam("id") int id) {
        return pm.find(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Profile> getProfiles() {
        return pm.findAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Profile addProfile(Profile profile) {
        Profile mProfile = new Profile();
        mProfile = profile;
        pm.create(mProfile);
        return profile;
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
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

//    @GET
//    @Path("{id}/playlists")
//    public PlaylistController getPlaylistController() {
//        return new PlaylistController();
//    }

}
