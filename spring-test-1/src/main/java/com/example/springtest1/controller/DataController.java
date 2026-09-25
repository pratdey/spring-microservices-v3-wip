package com.example.springtest1.controller;

import com.example.springtest1.business.BusinessService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
@Component
public class DataController {

    //private final BusinessService businessService;
    @Autowired
    private  BusinessService businessService;

    public DataController(BusinessService businessService) {
        this.businessService = businessService;
    }

    /** Returns the sum of the sample array: GET /data/sum */
    //@GetMapping("/data/sum")
    public void sumData() {
        //return businessService.addData();
        System.out.println( businessService.addData() );
    }
}

