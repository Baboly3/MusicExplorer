/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.musicexplorer.resources;


import com.musicexplorer.org.utils.Link;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Babak Tamjidi baboly@gmail.com
 */
@Path("/home")
public class RootResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response startHere(@Context UriInfo uriInfo) {
        List<Link> links = new ArrayList<Link>();
        String uri = uriInfo.getBaseUriBuilder().path(ArtistResource.class).build().toString();
        links.add(new Link(uri, "artists"));

        uri = uriInfo.getBaseUriBuilder().path(ProfileResource.class).build().toString();
        links.add(new Link(uri, "profiles"));
        
        return Response.status(Response.Status.OK).entity(links).build();
    }
}
