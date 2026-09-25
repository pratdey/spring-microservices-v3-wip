package com.example.springtest1.business;

import com.example.springtest1.data.DataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/** Contains the application logic for adding the data supplied by DataService. */
//@Service
@Component
public class BusinessService {


    /* Dependency injection - FIELD */
    @Autowired
    private  DataService dataService;
    

    /* Dependency injection - CONSTRUCTOR */
    //private final DataService dataService;
    /* public BusinessService(DataService dataService) {
        this.dataService = dataService;
    } */

    public int addData() {
        int total = 0;
        for (int value : dataService.getData()) {
            total += value;
        }
        return total;
    }
}

