/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.musicexplorer.controllers;

import com.musicexplorer.org.ejb.FollowerFacade;
import com.musicexplorer.org.ejb.PlaylistFacade;
import com.musicexplorer.org.ejb.ProfileFacade;
import com.musicexplorer.org.entity.Follower;
import java.util.List;
import javax.ejb.EJB;
import javax.persistence.Query;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Babak Tamjidi  baboly@gmail.com
 */
@Path("/playlists/{pId}/followers/")
public class FollowerResource {

    @EJB
    FollowerFacade fm;
    @EJB
    PlaylistFacade pm;
    @EJB
    ProfileFacade  profileManager;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Follower> getPlaylistFollowers(@PathParam("pId") int id){
        Query query = fm.getEm().createNamedQuery("Follower.findByPlaylist", Follower.class);
        query.setParameter("playlistid", pm.find(id));
        List<Follower> followers = query.getResultList();
        System.out.println("follower " + followers.get(0).getProfileid().getFirstName());
        return followers;
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public void setFollowerForPlaylist(@PathParam("pId") int pid, 
                                       @FormParam("profileId") int id){
        Follower follower = new Follower();
        follower.setPlaylistid(pm.find(pid));
        follower.setProfileid(profileManager.find(id));
        fm.create(follower);
    }
}
