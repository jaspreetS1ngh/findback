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

    public double jaccardSimilarity(Set<String> first, Set<String> second){

        Set<String> intersection= new HashSet<>(first);
        Set<String> union= new HashSet<>(first);
        intersection.retainAll(second);
        union.addAll(second);

        if(union.isEmpty()){
            return 0.0;
        }
        
        return (double)intersection.size()/union.size();
    }
    public double descriptionSimilarity(String first, String second){

        Set<String> firstSet= tokenize(first);
        Set<String> secondSet= tokenize(second);
        if(firstSet.isEmpty() || secondSet.isEmpty()){
            return 0.0;
        }
        return jaccardSimilarity(firstSet, secondSet);
    }

}