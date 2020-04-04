package com.algorithms.part2.week1;

public class Outcast {

    private final WordNet wordNet;

    public Outcast(WordNet wordnet) {
        validate(wordnet);
        this.wordNet = wordnet;
    }

    public String outcast(String[] nouns) {
        int[] distance = new int[nouns.length];
        for (int i = 0; i < nouns.length; ++i) {
            for (int j = i + 1; j < nouns.length; ++j) {
                int dist = wordNet.distance(nouns[i], nouns[j]);
                distance[i] += dist;
                distance[j] += dist;
            }
        }
        int maxDist = 0;
        String result = null;
        for (int i = 0; i < distance.length; ++i) {
            if (distance[i] > maxDist) {
                maxDist = distance[i];
                result = nouns[i];
            }
        }
        return result;
    }


    private void validate(Object o) {
        if (o == null) {
            throw new IllegalArgumentException();
        }
    }

}
