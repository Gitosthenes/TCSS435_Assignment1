package com.classes;

public class Node {
    private Node parent;
    private Board board;
    private int depth;
    private int heuristic;

    public Node(Node parent, Board board, int depth) {
        this.parent = parent;
        this.board = board;
        this.depth = depth;
        this.heuristic = 0;
    }

    public Node(Node parent, Board board, int depth, int heuristic) {
        this.parent = parent;
        this.board = board;
        this.depth = depth;
        this.heuristic = heuristic;
    }

    public Node getParent() {
        return parent;
    }

    public Board getBoard() {
        return board;
    }

    public int getDepth() {
        return depth;
    }

    public int getHeuristic() {
        return heuristic;
    }
}