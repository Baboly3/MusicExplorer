/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.musicexplorer.exception;

/**
 *
 * @author Babak Tamjidi baboly@gmail.com
 */
public class DataNotFoundException extends RuntimeException {

    public DataNotFoundException(String errorMessage) {
        super(errorMessage);

    }
}
