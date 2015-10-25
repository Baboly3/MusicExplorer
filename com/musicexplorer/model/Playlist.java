/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.musicexplorer.model;

import com.musicexplorer.model.helper.DatePersistance;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
 * @author Babak Tamjidi  baboly@gmail.com
 */
@Entity
@Table(name = "playlist")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Playlist.findAll", query = "SELECT p FROM Playlist p"),
    @NamedQuery(name = "Playlist.findById", query = "SELECT p FROM Playlist p WHERE p.id = :id"),
    @NamedQuery(name = "Playlist.findByCreated", query = "SELECT p FROM Playlist p WHERE p.created = :created")})
public class Playlist implements DatePersistance, Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "created")
    @Temporal(TemporalType.DATE)
    private Date created;
    @Column(name = "updated")
    @Temporal(TemporalType.DATE)
    private Date updated;
    @Size(max = 45)
    @Column(name = "playlist")
    private String name;
    @ManyToMany
    @XmlElement(name = "songs")
    private Collection<Song> songCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "playlistId")
    private Collection<Follower> followerCollection;
    @JoinColumn(name = "profileId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Profile profileId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "playlistId")
    private Collection<Share> shareCollection;

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

    public Profile getProfileId() {
        return profileId;
    }

    public void setProfileId(Profile profileId) {
        this.profileId = profileId;
    }

    
    @XmlTransient
    public Collection<Song> getSongCollection() {
        return songCollection;
    }

    public void setSongCollection(Collection<Song> songCollection) {
        this.songCollection = songCollection;
    }

    @XmlTransient
    public Collection<Follower> getFollowerCollection() {
        return followerCollection;
    }

    public void setFollowerCollection(Collection<Follower> followerCollection) {
        this.followerCollection = followerCollection;
    }

    public Profile getProfileid() {
        return profileId;
    }

    public void setProfileid(Profile profileid) {
        this.profileId = profileid;
    }

    public Date getUpdated() {
        return updated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public Collection<Share> getShareCollection() {
        return shareCollection;
    }

    public void setShareCollection(Collection<Share> shareCollection) {
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
        return "com.musicexplorer.org.Playlist[ id=" + id + " ]";
    }

   @Override
   @PrePersist
    public void SetCreated() {
        this.created = new Date();
    }

    @Override
    @PreUpdate
    public void SetUpdated() {
        this.updated = new Date();
    }


}
