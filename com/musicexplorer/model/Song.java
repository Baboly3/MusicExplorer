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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Babak Tamjidi baboly@gmail.com
 */
@Entity
@Table(name = "song")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Song.findAll", query = "SELECT s FROM Song s"),
    @NamedQuery(name = "Song.findByIdSong", query = "SELECT s FROM Song s WHERE s.id  = :id"),
    @NamedQuery(name = "Song.findByTitle", query = "SELECT s FROM Song s WHERE s.title = :title"),
    @NamedQuery(name = "Song.findByDuration", query = "SELECT s FROM Song s WHERE s.duration = :duration"),
    @NamedQuery(name = "Song.findByCreated", query = "SELECT s FROM Song s WHERE s.created = :created"),
    @NamedQuery(name = "Song.findByArtistid", query = "SELECT s FROM Song s WHERE s.artistId = :artistId")})
public class Song implements DatePersistance, Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "title")
    private String title;
    @Column(name = "duration")
    private Integer duration;
    @Column(name = "created")
    @Temporal(TemporalType.DATE)
    private Date created;
    @Column(name = "updated")
    @Temporal(TemporalType.DATE)
    private Date updated;
    @JoinColumn(name = "artistId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Artist artistId;
    @ManyToMany(mappedBy = "songCollection", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<Playlist> playlistCollection;
    @JoinColumn(name = "shareId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Share shareId;

    public Song() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    public Artist getArtist() {
        return artistId;
    }

    public void setArtist(Artist artist) {
        this.artistId = artist;
    }

    public Artist getArtistId() {
        return artistId;
    }

    public void setArtistId(Artist artistId) {
        this.artistId = artistId;
    }

    public Collection<Playlist> getPlaylistCollection() {
        return playlistCollection;
    }

    public void setPlaylistCollection(Collection<Playlist> playlistCollection) {
        this.playlistCollection = playlistCollection;
    }

    public Share getShareId() {
        return shareId;
    }

    public void setShareId(Share shareId) {
        this.shareId = shareId;
    }


    public Share getShareid() {
        return shareId;
    }

    public void setShareid(Share shareid) {
        this.shareId = shareid;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Song)) {
            return false;
        }
        Song other = (Song) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return "Song{" + "id=" + id + ", title=" + title + ", duration=" + duration + ", created=" + created + ", updated=" + updated + ", artistId=" + artistId + ", playlistCollection=" + playlistCollection + ", shareId=" + shareId + '}';
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
