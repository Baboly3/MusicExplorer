package com.musicexplorer.model.helper;


import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author Babak Tamjidi  baboly@gmail.com
 */

public interface DatePersistance {


    public void SetCreatedDate();

    public void SetUpdatedDate();
   
}
