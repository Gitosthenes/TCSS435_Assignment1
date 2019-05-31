package com.classes;

import java.util.Comparator;

public class NodeHeuristicComparator implements Comparator<Node> {

    @Override
    public int compare(Node n1, Node n2) {
        if (n1.getHeuristic() < n2.getHeuristic()) {
            return -1;
        } else if (n1.getHeuristic() > n2.getHeuristic()) {
            return 1;
        } else {
            return 0;
        }
    }
}
