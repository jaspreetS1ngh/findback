package com.example.findback.matching;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class MatchingService{

    public Set<String> tokenize(String description){
        Set<String> words= new HashSet<>();
        if(description==null || description.equals("")){
            return words;
        }
        String normalized= description.toLowerCase();

        String[] tokens= normalized.split("[^a-z0-9]+");
        for(String word:tokens){
            if(!word.isEmpty()){
                 words.add(word);

            }
           
        }



        return words;

        
    }

}