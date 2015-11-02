/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.musicexplorer.org.ejb;

import com.musicexplorer.interfaces.MainService;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Babak Tamjidi  baboly@gmail.com
 */
@Stateless
public class MainServiceFacade implements MainService{

    @EJB
    ArtistFacade artistService;
    @EJB
    FollowerFacade followerService;
    @EJB
    PlaylistFacade playlistService;
    @EJB
    ProfileFacade profileSerivce;
    @EJB
    ShareFacade shareService;
    @EJB
    SongFacade songService;

    @Override
    public ArtistFacade getArtistService() {
        return artistService;
    }

    @Override
    public FollowerFacade getFollowerService() {
        return followerService;
    }

    @Override
    public PlaylistFacade getPlaylistService() {
        return playlistService;
    }

    @Override

    public ProfileFacade getProfileSerivce() {
        return profileSerivce;
    }

    @Override
    public ShareFacade getShareService() {
        return shareService;
    }

    @Override
    public SongFacade getSongService() {
        return songService;
    }
    
    
}
