/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpfo;

import de.linguatools.disco.CorruptConfigFileException;
import de.linguatools.disco.DISCO;
import de.linguatools.disco.ReturnDataBN;
import de.linguatools.disco.ReturnDataCol;
import de.linguatools.disco.WrongWordspaceTypeException;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Salah Ait-Mokhtar 
 */
public class Feature implements Comparable<Feature> {
    // le nom du trait
    private String name;
    // l'index du trait dans l'ensemble des traits auquel il appartient
    private int index;
    // fréquence du trait (nombre de fois observé dans les données)
    private int count = 0;
    private float weight = 0;
    // Est-ce que la sélection de ce trait dépend de sa fréquence?
    private boolean isFrequencyBased;

    public Feature(String name, int index) {
        this.name = name;
        this.index = index;
        this.count = 0;
        this.weight=0;
    }

    
    // Accessors
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getCount() {
        return count;
    }
    
    public float getWeightedCount(DISCO disco) {
        float simpos; float simpos2;
        float simneg; float simneg2;
        String word; String word2 = "";
        boolean bigram = false;
        if(this.name.contains("_")){
           String parts[] = this.name.split("\\_");
           word=parts[0];
           word2=parts[1];
           bigram=true;
        }
        else{
            word=this.name;
        }
        if (bigram){
            try{
         simpos = disco.semanticSimilarity("bon", word,
                DISCO.SimilarityMeasure.COSINE);}
        catch(IOException | IllegalArgumentException | NullPointerException ex){
            System.out.println("Error: " + ex);
            return 0;
        }
        try{
         simneg = disco.semanticSimilarity("mauvais", word,
                DISCO.SimilarityMeasure.COSINE);}
        catch(IOException | IllegalArgumentException | NullPointerException ex){
            System.out.println("Error: " + ex);
            return 0;
        }
        try{
         simpos2 = disco.semanticSimilarity("bon", word2,
                DISCO.SimilarityMeasure.COSINE);}
        catch(IOException | IllegalArgumentException | NullPointerException ex){
            System.out.println("Error: " + ex);
            return 0;
        }
        try{
         simneg2 = disco.semanticSimilarity("mauvais", word2,
                DISCO.SimilarityMeasure.COSINE);}
        catch(IOException | IllegalArgumentException | NullPointerException ex){
            System.out.println("Error: " + ex);
            return 0;
        }
        simpos=(simpos+simpos2)/2;
        simneg=(simneg+simneg2)/2;
        }
        else{
        try{
         simpos = disco.semanticSimilarity("bon", word,
                DISCO.SimilarityMeasure.COSINE);}
        catch(IOException | IllegalArgumentException | NullPointerException ex){
            System.out.println("Error: " + ex);
            return 0;
        }
        try{
         simneg = disco.semanticSimilarity("mauvais", word,
                DISCO.SimilarityMeasure.COSINE);}
        catch(IOException | IllegalArgumentException | NullPointerException ex){
            System.out.println("Error: " + ex);
            return 0;
        }
        }
        weight = simpos-simneg;
        float fcount = (float) count;
        float weightedcount = 10*weight*fcount;
        return weightedcount;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    public void incrementCount() {
        this.count++;
    }

    @Override
    public int compareTo(Feature o) {
        if (this.count > o.count) {
            return -1;
        } else if (this.count < o.count) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "(" + index + ")" + name + ":" + count;
    }
    
    
    
}
