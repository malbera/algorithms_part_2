package com.algorithms.part2.week1;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinearProbingHashST;

import java.util.*;

public class WordNet {

    private final LinearProbingHashST<String, List<Integer>> noun2id;
    private final LinearProbingHashST<Integer, String> id2noun;
    private final SAP sap;

    public WordNet(String synsets,
                   String hypernyms) {
        validate(synsets);
        validate(hypernyms);
        id2noun = new LinearProbingHashST<>();
        noun2id = new LinearProbingHashST<>();
        In in = new In(synsets);
        String line;
        int vertexCount = 0;
        while ((line = in.readLine()) != null) {
            String[] split = line.split(",");
            vertexCount++;
            int id = Integer.parseInt(split[0]);
            id2noun.put(id, split[1]);
            for (String noun : split[1].split(" ")) {
                List<Integer> ids = noun2id.get(noun);
                if (ids == null) {
                    ids = new ArrayList<>();
                }
                ids.add(id);
                noun2id.put(noun, ids);
            }
        }

        Digraph digraph = new Digraph(vertexCount);
        in = new In(hypernyms);
        while ((line = in.readLine()) != null) {
            String[] split = line.split(",");
            int id = Integer.parseInt(split[0]);
            for (int i = 1; i < split.length; i++) {
                digraph.addEdge(id, Integer.parseInt(split[i]));
            }
        }

        DirectedCycle cycle = new DirectedCycle(digraph);
        if (cycle.hasCycle()) {
            throw new IllegalArgumentException();
        }

        int rootCount = 0;
        for (int i = 0; i < digraph.V(); i++) {
            if (digraph.outdegree(i) == 0) {
                rootCount++;
                if (rootCount > 1) {
                    throw new IllegalArgumentException();
                }
            }
        }

        sap = new SAP(digraph);
    }

    public Iterable<String> nouns() {
        return noun2id.keys();
    }

    public boolean isNoun(String word) {
        validate(word);
        return noun2id.contains(word);
    }

    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        return sap.length(noun2id.get(nounA), noun2id.get(nounB));
    }

    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        return id2noun.get(sap.ancestor(noun2id.get(nounA), noun2id.get(nounB)));
    }

    private void validate(Object o) {
        if (o == null) {
            throw new IllegalArgumentException();
        }
    }

}
