package com.classes;

import java.util.Comparator;

/**
 * Node objects encapsulate board states with the depth of the node when created and a heuristic value (if necessary).
 */
class Node {

    /* Global Variables */
    private Board board;
    private int depth;
    private int heuristic;

    /**
     * Node constructor method for when heuristic isn't needed (set to 0).
     */
    Node(Board board, int depth) {
        this.board = board;
        this.depth = depth;
        this.heuristic = 0;
    }

    /**
     * Alternate Node constructor method for when heuristic is needed.
     */
    Node(Board board, int depth, int heuristic) {
        this.board = board;
        this.depth = depth;
        this.heuristic = heuristic;
    }

    /**
     * Returns the board state of this node object.
     */
    Board getBoard() {
        return board;
    }

    /**
     * Returns the depth of this node object.
     */
    int getDepth() {
        return depth;
    }

    /**
     * Returns the heuristic value of this node object.
     */
    int getHeuristic() {
        return heuristic;
    }

    /**
     * Comparator class for ordering nodes by heuristic value in priority queues.
     */
    public static class NodeHeuristicComparator implements Comparator<Node> {

        @Override
        public int compare(Node n1, Node n2) {
            return Integer.compare(n1.getHeuristic(), n2.getHeuristic());
        }
    }
}