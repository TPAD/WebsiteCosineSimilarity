package edu.cmu.cs.cs214.hw1;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 * Takes a list of URLs on the command line and prints the two URL whose web
 * pages have the highest cosine similarity.
 */
public class FindClosestMatch {

    /**
     * Should print the closest match in a set of Documents, formed from urls
     * specified in args, in terms of their cosine similarity.
     * @param args arguments passed on the cmd line (SHOULD BE URLs in String form)
     */
    public static void main(String[] args) {
        try {
            List<Document> docs = new ArrayList<>();
            if (args.length <= 1) {
                System.err.println("ERROR: Program requires more than one arg");
                return;
            }
            for (String arg : args) {
                docs.add(new Document(arg));
            }
            printClosestMatch(docs);
        } catch (IOException exception) {
            System.out.println("ERROR: - IOException");
        }
    }

    /**
     * Takes a list of documents and compares each unique pair once to find the two documents
     * with the highest cosine similarity.
     * @param documents A Document list to compare using cosine similarity
     */
    private static void printClosestMatch(List<Document> documents)  {
        Document compDoc = documents.get(0);
        int numDocs = documents.size();
        double similarity;
        double bestSimilarity = 0.0;
        String urlOne = "";
        String urlTwo = "";
        int j = 1;
        for (int k = 0; k < numDocs - 1; k++) {
            for (int i = j; i < numDocs; i++) {
                Document currDoc = documents.get(i);
                similarity = compDoc.cosineSimilarity(currDoc);
                if (similarity >= bestSimilarity) {
                    bestSimilarity = similarity;
                    urlOne = compDoc.toString();
                    urlTwo = currDoc.toString();
                }
            }
            j++;
            compDoc = documents.get(k+1);
        }
        System.out.println("The URLs for the most similar web pages are");
        System.out.println(urlOne);
        System.out.println(urlTwo);
        System.out.printf("with cosine similarity %f", bestSimilarity);
    }

}
