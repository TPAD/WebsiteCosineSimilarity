package edu.cmu.cs.cs214.hw1;

import java.net.URL;
import java.util.Scanner;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.LinkedHashMap;

/**
 * Takes a URL, attempts to parse the web page contents.
 * Contains an instance method which calculates cosine similarity between
 * itself and Document in the argument
 */
public final class Document {

    private final Map<String, Integer> wordFrequencyMap;
    private final String docUrl;
    private final double magnitude;

    /**
     * Returns a Document object containing a Map of word frequency counts that is created
     * through parsing input from URL created from url string using a Scanner
     * @param url a URL as a String
     * @throws IOException if parse() throws IOException
     */
    public Document(String url) throws IOException {
        this.wordFrequencyMap = new LinkedHashMap<>();
        this.docUrl = url;
        this.parse(new URL(url).openStream());
        this.magnitude = calculateMagnitude();
    }

    /**
     * Parses the web page content from URL created using url String, and adds the words
     * to the word frequency map.
     * @param in input stream created from URL
     */
    private void parse(InputStream in) {
        try (Scanner scanner = new Scanner(in)) {
            while(scanner.hasNext()) {
                String word = scanner.next();
                Integer freq = this.wordFrequencyMap.get(word);
                freq = (freq == null) ? 0:++freq;
                this.wordFrequencyMap.put(word, freq);
            }
        }
    }

    /**
     * Instance method that takes a second Document and returns the cosine
     * similarity between self and second Document.
     * @param doc used for comparison
     * @return the cosine similarity between parameter and self
     */
    double cosineSimilarity(Document doc) {
        int numerator = 0;
        double denominator = this.magnitude * doc.magnitude;
        Map<String, Integer> docMap = doc.wordFrequencyMap;
        for (String word : this.wordFrequencyMap.keySet()) {
            if (docMap.containsKey(word)) {
                numerator += wordFrequencyMap.get(word) * docMap.get(word);
            }
        }
        return numerator / denominator;
    }

    /**
     * Instance method that calculates the Euclidean norm or magnitude of the
     * word frequency map of the Document
     * @return the 'magnitude' of the word frequency map
     */
    private double calculateMagnitude() {
        int count = 0;
        for (String word : wordFrequencyMap.keySet()) {
            Integer freq = wordFrequencyMap.get(word);
            count += (freq != null) ? (freq * freq):0;
        }
        return Math.sqrt(count);
    }

    // prints the Document's url string
    @Override
    public String toString() {
        return this.docUrl;
    }

}
