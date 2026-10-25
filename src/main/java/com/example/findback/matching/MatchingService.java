package com.example.findback.matching;

import java.lang.reflect.AccessFlag.Location;
import java.util.*;

import org.springframework.stereotype.Service;

import com.example.findback.report.CampusLocation;
import com.example.findback.report.ItemReport;
import com.example.findback.report.ReportType;

@Service
public class MatchingService{
    final double COLOR_WEIGHT =30.0;
    final double LOCATION_WEIGHT= 30.0;
     final double DESCRIPTION_WEIGHT= 40.0;


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
    public boolean isEligible(ItemReport lost, ItemReport found){
        Objects.requireNonNull(lost, "lost report must not be null");
        Objects.requireNonNull(found, "found report must not be null");
        if (lost.getType() != ReportType.LOST) {
        return false;
    }

    if (found.getType() != ReportType.FOUND) {
        return false;
    }

    if (!lost.isOpen() || !found.isOpen()) {
        return false;
    }

    if (lost.getCategory() != found.getCategory()) {
        return false;
    }

    return true;
        

    }

    private boolean colorsMatch(String first, String second){

        if(first==null ||second==null || first.isBlank() || second.isBlank() ){
            return false;
        }
       String normalizedFirst= first.strip();
       String normalizedSecond= second.strip();

        return normalizedFirst.equalsIgnoreCase(normalizedSecond);

    }
    public MatchResult compare(ItemReport lost, ItemReport found){
        if(!isEligible(lost, found)){
            return new MatchResult(found.getId(), false, 0.0, List.of("Reports do not satisfy matching eligibility rules"));
        }
        double score= 0.0;
        List<String> reasons= new ArrayList<>();
        if(colorsMatch(lost.getColor(), found.getColor())){
            score+=COLOR_WEIGHT;
            reasons.add("Colour matches");
        }
        if(lost.getLocation()!=CampusLocation.OTHER && lost.getLocation()==found.getLocation()){
            score+=LOCATION_WEIGHT;
            reasons.add("Location matches");
        }
        double similarity= descriptionSimilarity(lost.getDescription(), found.getDescription());
        double descriptionPoints= similarity*DESCRIPTION_WEIGHT;
        score+=descriptionPoints;
        if(descriptionPoints>0){
            reasons.add("Description overlap contributes " + descriptionPoints +" points");
        }
        if(score==0){
            reasons.add("No matching colour, location, or description evidence");
        }
        return new MatchResult(found.getId(), true, score, reasons);
       

    }

    

}