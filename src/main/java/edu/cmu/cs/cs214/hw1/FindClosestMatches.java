package edu.cmu.cs.cs214.hw1;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Takes an arbitrary number of URLs on the command line and finds the closest matching web page for each of the
 * command line arguments. Matches are made based on cosine similarity.
 */
public class FindClosestMatches {

    /**
     * Should print the closest matches between the documents formed from each command line argument,
     * in terms of their cosine similarity.
     * @param args arguments passed on the command line
     */
    public static void main(String[] args) {
        try {
            int argLen = args.length;
            List<Document> docs = new ArrayList<>();
            List<List<Double>> similarities;
            if (argLen <= 1) {
                System.out.println("ERROR: Program requires more than one arg");
                return;
            }
            for (String arg : args) {
                docs.add(new Document(arg));
            }
            similarities = calculateCosineSimilarities(docs);
            printClosestMatches(docs, similarities);
        } catch (IOException exception) {
            System.out.println("ERROR: - IOException");
        }
    }

    /**
     * Takes a List of Documents and calculates each unique pair's cosine similarity once.
     * @param docs the Documents for which cosine similarities are calculated
     * @return a (n * n-1) 2D List of cosine similarities, where n is the number of Documents in docs
     */
    private static List<List<Double>> calculateCosineSimilarities(List<Document> docs) {
        List<List<Double>> similarities = new ArrayList<>();
        Document compDoc = docs.get(0);
        int size = docs.size();
        int counter = 0;
        int j = 1;
        while (counter < size - 1) {
            List<Double> sims = new ArrayList<>();
            for (int i = j; i < size; i++) {
                Document currDoc = docs.get(i);
                sims.add(compDoc.cosineSimilarity(currDoc));
            }
            similarities.add(sims);
            counter++;
            compDoc = docs.get(counter);
            j++;
            // prepends the missing entries in the sims array
            for (int k = 0; k < similarities.size() - 1; k++) {
                List<Double> oldSims = similarities.get(k);
                sims.add(k, oldSims.get(counter - 2));
            }
        }
        // based on how the previous loops were written, the last Document in docs was neglected
        // the similarities required for the last Document are the final element in each of the previously added lists
        List<Double> last = new ArrayList<>();
        for(List<Double> items : similarities) {
            last.add(items.get(items.size() - 1));
        }
        similarities.add(last);
        return similarities;
    }

    /**
     * Prints each Document in docs's closest match in terms of cosine similarity
     * @param docs the documents for which the closest match will be determined
     * @param similarities a 2D (n * n-1) List of cosine similarities used to find each Document's closest match
     */
    private static void printClosestMatches(List<Document> docs, List<List<Double>> similarities) {
        for(int i = 0; i < docs.size(); i++) {
            List<Double> sims = similarities.get(i);
            String doc = docs.get(i).toString();
            Double max = Collections.max(sims);
            int index = sims.indexOf(max);
            String match = (i <= index) ? docs.get(++index).toString():docs.get(index).toString();
            System.out.printf("The closest match for %s is %s\n", doc, match);
            // approximate since the Collections max method loses a bit of accuracy
            System.out.printf("with approximate cosine similarity: %f\n\n", max);
        }
    }

}
