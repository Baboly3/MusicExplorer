/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.musicexplorer.org.entity;

import com.musicexplorer.model.helper.DatePersistance;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Babak Tamjidi baboly@gmail.com
 */
@Entity
@Table(name = "playlist")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Playlist.findAll", query = "SELECT p FROM Playlist p"),
    @NamedQuery(name = "Playlist.findById", query = "SELECT p FROM Playlist p WHERE p.id = :id"),
    @NamedQuery(name = "Playlist.findByProfileId", query = "SELECT p FROM Playlist p WHERE p.profileid = :profileid"),
    @NamedQuery(name = "Playlist.findByCreated", query = "SELECT p FROM Playlist p WHERE p.created = :created")})
public class Playlist implements DatePersistance, Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "Name")
    private String playlist;
    @Column(name = "created")
    @Temporal(TemporalType.DATE)
    private Date created;
    @Column(name = "updated")
    @Temporal(TemporalType.DATE)
    private Date updated;
    @XmlElement(name = "songs")
    @JoinTable(name = "song_has_playlist", joinColumns = {
        @JoinColumn(name = "Playlist_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "Song_id", referencedColumnName = "id"),
        @JoinColumn(name = "Song_Artist_id", referencedColumnName = "artistId")})
    @ManyToMany
    private List<Song> songCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "playlistid")
    private List<Follower> followerCollection;
    @JoinColumn(name = "Profile_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Profile profileid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "playlistid")
    private List<Share> shareCollection;

    public Playlist() {
    }

    public Playlist(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public Date getUpdated() {
        return updated;
    }

    public String getPlaylist() {
        return playlist;
    }

    public void setPlaylist(String playlist) {
        this.playlist = playlist;
    }

    @XmlTransient
    public List<Song> getSongCollection() {
        return songCollection;
    }

    public void setSongCollection(List<Song> songCollection) {
        this.songCollection = songCollection;
    }

    @XmlTransient
    public List<Follower> getFollowerCollection() {
        return followerCollection;
    }

    public void setFollowerCollection(List<Follower> followerCollection) {
        this.followerCollection = followerCollection;
    }

    public Profile getProfileid() {
        return profileid;
    }

    public void setProfileid(Profile profileid) {
        this.profileid = profileid;
    }

    @XmlTransient
    public List<Share> getShareCollection() {
        return shareCollection;
    }

    public void setShareCollection(List<Share> shareCollection) {
        this.shareCollection = shareCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Playlist)) {
            return false;
        }
        Playlist other = (Playlist) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.musicexplorer.org.entity.Playlist[ id=" + id + " ]";
    }

    @Override
    @PrePersist
    public void SetCreated() {
        created = new Date();
    }

    @Override
    @PreUpdate
    public void SetUpdated() {
        updated = new Date();
    }
}
