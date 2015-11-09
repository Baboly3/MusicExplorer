/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.musicexplorer.org.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Babak Tamjidi  baboly@gmail.com
 */
@Entity
@Table(name = "follower")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Follower.findAll", query = "SELECT f FROM Follower f"),
    @NamedQuery(name = "Follower.findByPlaylist", query = "SELECT f FROM Follower f WHERE f.playlistid = :playlistid"),
    @NamedQuery(name = "Follower.findById", query = "SELECT f FROM Follower f WHERE f.id = :id")})
public class Follower implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "Artist_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Artist artistid;
    @JoinColumn(name = "Playlist_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Playlist playlistid;
    @JoinColumn(name = "Profile_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Profile profileid;

    public Follower() {
    }

    public Follower(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Artist getArtistid() {
        return artistid;
    }

    public void setArtistid(Artist artistid) {
        this.artistid = artistid;
    }

    public Playlist getPlaylistid() {
        return playlistid;
    }

    public void setPlaylistid(Playlist playlistid) {
        this.playlistid = playlistid;
    }

    public Profile getProfileid() {
        return profileid;
    }

    public void setProfileid(Profile profileid) {
        this.profileid = profileid;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.artistid);
        hash = 17 * hash + Objects.hashCode(this.playlistid);
        hash = 17 * hash + Objects.hashCode(this.profileid);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Follower other = (Follower) obj;
        if (!Objects.equals(this.artistid, other.artistid)) {
            return false;
        }
        if (!Objects.equals(this.playlistid, other.playlistid)) {
            return false;
        }
        if (!Objects.equals(this.profileid, other.profileid)) {
            return false;
        }
        return true;
    }



    @Override
    public String toString() {
        return "com.musicexplorer.org.entity.Follower[ id=" + id + " ]";
    }

}
