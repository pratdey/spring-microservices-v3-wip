package com.example.springtest1.data;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/** Supplies the initial data used by the business layer. */
//@Service
@Component
public class DataService {

    private final int[] sampleData = {10, 20, 30, 40, 50};

    public int[] getData() {
        // Return a copy so callers cannot change the service's source data.
        return sampleData.clone();
    }
}

