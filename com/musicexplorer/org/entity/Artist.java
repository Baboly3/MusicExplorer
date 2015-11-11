/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.musicexplorer.org.entity;

import com.musicexplorer.model.helper.DatePersistance;
import java.io.Serializable;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
    @Pattern(regexp = "/^[a-zA-ZäöåÄÖÅ]+$/")
    @NotNull
    @Column(name = "name", unique = true)
    private String name;
    @Size(max = 255)
    @NotNull
    @Pattern(regexp = "/^[a-zA-ZäöåÄÖÅ]+$/")
    @Column(name = "history")
    private String history;
    @Size(max = 45)
    @NotNull
    @Pattern(regexp = "/^[a-zA-ZäöåÄÖÅ]+$/")
    @Column(name = "genrer")
    private String genrer;
    @Column(name = "created")
    @Temporal(TemporalType.DATE)
    private Date created;
    @Column(name = "updated")
    @Temporal(TemporalType.DATE)
    private Date updated;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "artist")
    private List<Song> songCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "artistid")
    private List<Follower> followerCollection;

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + Objects.hashCode(this.history);
        hash = 59 * hash + Objects.hashCode(this.genrer);
        hash = 59 * hash + Objects.hashCode(this.created);
        hash = 59 * hash + Objects.hashCode(this.updated);
        hash = 59 * hash + Objects.hashCode(this.songCollection);
        hash = 59 * hash + Objects.hashCode(this.followerCollection);
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
        final Artist other = (Artist) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.history, other.history)) {
            return false;
        }
        if (!Objects.equals(this.genrer, other.genrer)) {
            return false;
        }
        if (!Objects.equals(this.created, other.created)) {
            return false;
        }
        if (!Objects.equals(this.updated, other.updated)) {
            return false;
        }
        if (!Objects.equals(this.songCollection, other.songCollection)) {
            return false;
        }
        if (!Objects.equals(this.followerCollection, other.followerCollection)) {
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
