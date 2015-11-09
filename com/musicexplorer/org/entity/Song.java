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
import java.util.Objects;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Babak Tamjidi baboly@gmail.com
 */
@Entity
@Table(name = "song")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Song.findAll", query = "SELECT s FROM Song s"),
    @NamedQuery(name = "Song.findById", query = "SELECT s FROM Song s WHERE s.id = :id"),
    @NamedQuery(name = "Song.findByTitle", query = "SELECT s FROM Song s WHERE s.title = :title"),
    @NamedQuery(name = "Song.findByDuration", query = "SELECT s FROM Song s WHERE s.duration = :duration"),
    @NamedQuery(name = "Song.findByPlaylistId", query = "SELECT s FROM Song s WHERE s.playlistCollection = :playlistCollection"),
    @NamedQuery(name = "Song.findByCreated", query = "SELECT s FROM Song s WHERE s.created = :created"),
    @NamedQuery(name = "Song.findByArtist", query = "SELECT s FROM Song s WHERE s.artist = :artist")})
public class Song implements DatePersistance , Serializable {

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
    private Double duration;
    @Column(name = "created")
    @Temporal(TemporalType.DATE)
    private Date created;
    @Column(name = "updated")
    @Temporal(TemporalType.DATE)
    private Date updated;
    @ManyToMany(mappedBy = "songCollection")
    private List<Playlist> playlistCollection;
    @JoinColumn(name = "artistId", referencedColumnName = "id", updatable = false)
    @ManyToOne(optional = false)
    private Artist artist;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "song")
    private List<Share> shareCollection;

    public Song() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Date getCreated() {
        return created;
    }

    public Date getUpdated() {
        return updated;
    }

    @XmlTransient
    public List<Playlist> getPlaylistCollection() {
        return playlistCollection;
    }

    public void setPlaylistCollection(List<Playlist> playlistCollection) {
        this.playlistCollection = playlistCollection;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
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
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.title);
        hash = 71 * hash + Objects.hashCode(this.duration);
        hash = 71 * hash + Objects.hashCode(this.updated);
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
        final Song other = (Song) obj;
        return true;
    }

    @Override
    public String toString() {
        return "Song{" + "id=" + id + ", title=" + title + ", duration=" + duration + ", created=" + created + ", updated=" + updated + ", playlistCollection=" + playlistCollection + ", artist=" + artist + ", shareCollection=" + shareCollection + '}';
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
