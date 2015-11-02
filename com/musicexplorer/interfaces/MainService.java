/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.musicexplorer.interfaces;

import com.musicexplorer.org.ejb.ArtistFacade;
import com.musicexplorer.org.ejb.FollowerFacade;
import com.musicexplorer.org.ejb.PlaylistFacade;
import com.musicexplorer.org.ejb.ProfileFacade;
import com.musicexplorer.org.ejb.ShareFacade;
import com.musicexplorer.org.ejb.SongFacade;
import javax.ejb.Local;

/**
 *
 * @author Babak Tamjidi baboly@gmail.com
 */
@Local
public interface MainService {

    public ArtistFacade getArtistService(); 

    public FollowerFacade getFollowerService(); 

    public PlaylistFacade getPlaylistService(); 

    public ProfileFacade getProfileSerivce(); 

    public ShareFacade getShareService(); 

    public SongFacade getSongService(); 
}
