/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpfo;

import de.linguatools.disco.CorruptConfigFileException;
import de.linguatools.disco.DISCO;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.System.out;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Salah Ait-Mokhtar
 */
public class FT {

    public static Evaluation experiment(Rep rep, NN nn,
            Dataset trainSet, Dataset testSet) {
        // afficher l'ensemble des traits
        out.println(rep.fset);
        Model model = new Model(rep, nn);
        // entraîner sur les données d'entraînements
        model.train(trainSet);
        // Evaluer sur le données de test
        Evaluation eval = new Evaluation();
        eval.evaluate(model, testSet);
        // retourner les résultats d'évaluation
        return eval;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // 
        String discoDir;
//        discoDir = args[0];
//discoDir = "C:/Data/Lexical/FR/fr-general-20151126-lm-word2vec-sim";
        discoDir = "./resources/fr-general-20151126-lm-word2vec-sim";
        // second argument is the input word
        String word;
//        word = args[1];
        word = "restaurant";

        /**
         * **************************************
         * create instance of class DISCO. * Do NOT load the word space into
         * RAM. *
	 ***************************************
         */
        DISCO disco;
        try {
            disco = new DISCO(discoDir, false);
        } catch (FileNotFoundException | CorruptConfigFileException ex) {
            System.out.println("Error creating DISCO instance: " + ex);
            return;
        }

        
        
        
        String lexPathname = "./resources/lefff-3.4.mlex";
        String corpusPathname = "./corpus/corpus.all20";
        if (args.length == 2) {
            lexPathname = args[0];
            corpusPathname = args[1];
        }
        // charger le lexique
        Lexicon lex = new Lexicon();
        lex.load(Paths.get(lexPathname));
        // Créer un tokeniseur
        Tokenizer tokenizer = new Tokenizer(lex);
        // Charger les données
        Path dataPath = Paths.get(corpusPathname);
        Dataset dataset = Dataset.load(dataPath);
        // Réserver 80% pour l'entraînement, et 20% pour le test
        Dataset testset = dataset.split(0.80f);

        // Créer une représentation
        Rep rep = new Rep_TCFL_BOW(tokenizer, lex, 700, 50);
      // Rep rep = new Rep_TCFL_BOW_disco(tokenizer, lex, 700, 30, disco);
        // initialiser la représentation (l'ensemble de ses traits)
        rep.initializeFeatures(dataset);
        // Créer un RN
       //  NN nn = new NN_H15linH05lin(rep.getDimension(), ClassLabel.size());
      //  NN nn = new NN_H50softH05soft(rep.getDimension(), ClassLabel.size());
        NN nn = new NN_H05sigH15sigH05sig(rep.getDimension(), ClassLabel.size());
        //NN nn = new NN_H90tanhH20tanh(rep.getDimension(), ClassLabel.size());
//        NN nn = new NN_H90tanhH20tanh(rep.getDimension(), ClassLabel.size());
        // lancer l'expérience
        Evaluation eval = experiment(rep, nn, dataset, testset);
        // Afficher les résultats
        System.out.println(eval.resultToString());

    }

}
