package pl.put.poznan.service;

import org.springframework.stereotype.Service;

@Service
public class JsonService {
    public String transform(String text){
        return text.toUpperCase();
    }
}
