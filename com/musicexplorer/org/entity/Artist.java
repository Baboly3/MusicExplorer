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
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "artist")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Artist.findAll", query = "SELECT a FROM Artist a"),
    @NamedQuery(name = "Artist.findById", query = "SELECT a FROM Artist a WHERE a.id = :id"),
    @NamedQuery(name = "Artist.findByName", query = "SELECT a FROM Artist a WHERE a.name = :name"),
    @NamedQuery(name = "Artist.findByHistory", query = "SELECT a FROM Artist a WHERE a.history = :history"),
    @NamedQuery(name = "Artist.findByGenrer", query = "SELECT a FROM Artist a WHERE a.genrer = :genrer"),
    @NamedQuery(name = "Artist.findByCreated", query = "SELECT a FROM Artist a WHERE a.created = :created")})
public class Artist implements DatePersistance, Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "name")
    private String name;
    @Size(max = 255)
    @Column(name = "history")
    private String history;
    @Size(max = 45)
    @Column(name = "genrer")
    private String genrer;
    @Column(name = "created")
    @Temporal(TemporalType.DATE)
    private Date created;
    @Column(name = "updated")
    @Temporal(TemporalType.DATE)
    private Date updated;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "artist")
    private Collection<Song> songCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "artistid")
    private Collection<Follower> followerCollection;

    public Artist() {
    }

    public Artist(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public Date getCreated() {
        return created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getGenrer() {
        return genrer;
    }

    public void setGenrer(String genrer) {
        this.genrer = genrer;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Artist)) {
            return false;
        }
        Artist other = (Artist) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.musicexplorer.org.entity.Artist[ id=" + id + " ]";
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