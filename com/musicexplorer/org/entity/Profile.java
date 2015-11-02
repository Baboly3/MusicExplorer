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
@Table(name = "profile")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Profile.findAll", query = "SELECT p FROM Profile p"),
    @NamedQuery(name = "Profile.findById", query = "SELECT p FROM Profile p WHERE p.id = :id"),
    @NamedQuery(name = "Profile.findByFirstName", query = "SELECT p FROM Profile p WHERE p.firstName = :firstName"),
    @NamedQuery(name = "Profile.findByLastName", query = "SELECT p FROM Profile p WHERE p.lastName = :lastName"),
    @NamedQuery(name = "Profile.findByPassword", query = "SELECT p FROM Profile p WHERE p.password = :password")})
public class Profile implements DatePersistance, Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "firstName", nullable = false)
    private String firstName;
    @Size(max = 45)
    @Column(name = "lastName", nullable = false)
    private String lastName;
    @Size(max = 45)
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "created", updatable = false, insertable = false)
    @Temporal(TemporalType.DATE)
    private Date created;
    @Column(name = "updated", updatable = false, insertable = false)
    @Temporal(TemporalType.DATE)
    private Date updated;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profileid")
    private List<Follower> followerCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profileid")
    private List<Playlist> playlistCollection;

    public Profile() {
    }

    public Profile(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreated() {
        return created;
    }

    public Date getUpdated() {
        return updated;
    }
    

    @XmlTransient
    public List<Follower> getFollowerCollection() {
        return followerCollection;
    }

    public void setFollowerCollection(List<Follower> followerCollection) {
        this.followerCollection = followerCollection;
    }

    @XmlTransient
    public List<Playlist> getPlaylistCollection() {
        return playlistCollection;
    }

    public void setPlaylistCollection(List<Playlist> playlistCollection) {
        this.playlistCollection = playlistCollection;
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
        if (!(object instanceof Profile)) {
            return false;
        }
        Profile other = (Profile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.musicexplorer.org.entity.Profile[ id=" + id + " ]";
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
