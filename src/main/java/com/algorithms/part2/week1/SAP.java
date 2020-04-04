package com.algorithms.part2.week1;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

public class SAP {

    private final Digraph digraph;

    public SAP(Digraph digraph) {
        this.digraph = new Digraph(digraph);
    }

    public int length(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return shortest(v, w)[0];
    }

    public int ancestor(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return shortest(v, w)[1];
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        validateMilipleVertexes(v);
        validateMilipleVertexes(w);
        return shortest(v, w)[0];
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        validateMilipleVertexes(v);
        validateMilipleVertexes(w);
        return shortest(v, w)[1];
    }

    private int[] shortest(Iterable<Integer> v, Iterable<Integer> w) {
        validateMilipleVertexes(v);
        validateMilipleVertexes(w);
        BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(digraph, w);
        return calculateResult(bfsv, bfsw);
    }

    private int[] shortest(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(digraph, w);
        return calculateResult(bfsv, bfsw);
    }

    private int[] calculateResult(BreadthFirstDirectedPaths bfsv, BreadthFirstDirectedPaths bfsw) {
        int[] result = new int[2];
        int shortestLen = Integer.MAX_VALUE;
        int shortestAncestor = -1;
        for (int i = 0; i < digraph.V(); ++i) {
            if (bfsv.hasPathTo(i) && bfsw.hasPathTo(i)) {
                int len = bfsv.distTo(i) + bfsw.distTo(i);
                if (len < shortestLen) {
                    shortestLen = len;
                    shortestAncestor = i;
                }
            }
        }
        if (shortestAncestor == -1) {
            result[0] = -1;
            result[1] = -1;
        }
        else {
            result[0] = shortestLen;
            result[1] = shortestAncestor;
        }
        return result;
    }

    private void validate(Object o) {
        if (o == null) {
            throw new IllegalArgumentException();
        }
    }

    private void validateVertex(int v) {
        int graphVertex = digraph.V();
        if (v < 0 || v >= graphVertex) {
            throw new IllegalArgumentException();
        }
    }

    private void validateMilipleVertexes(Iterable<Integer> vertexes) {
        validate(vertexes);
        for (Integer vertex : vertexes) {
            validateVertex(vertex);
        }
    }


}
