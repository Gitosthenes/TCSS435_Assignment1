package com.classes;

import java.util.*;

public class Main {

    private static final String[][] GOAL_STATES = new String[2][16];

    private static Node root;
    private static boolean solutionFound;
    private static int numCreated;
    private static int numExpanded;
    private static int maxFringe;
    private static Set<String> visitedBoards;

    public static void breadthFirst(final Board initial, final String[] goal) {
        Queue<Node> q = new LinkedList<>();
        solutionFound = false;
        root = new Node(null, initial, 0);
        visitedBoards = new HashSet<>();
        numCreated = 1;
        numExpanded = 0;
        maxFringe = 0;
        long startTime = System.currentTimeMillis();

        q.add(root);
        while (!q.isEmpty()) {
            if (System.currentTimeMillis() - startTime > 120000) {
                break;
            }
            if (q.size() > maxFringe) {
                maxFringe = q.size();
            }
            Node node = q.remove();
            numExpanded++;
            visitedBoards.add(node.getBoard().toString());
            if (Arrays.equals(goal, node.getBoard().getBoardAsLine())) {
                solutionFound = true;
                System.out.println(node.getDepth() + ", " + numCreated + ", " + numExpanded + ", " + maxFringe);
                break;
            } else {
                for (Move move : Move.values()) {
                    if (node.getBoard().isLegalMove(move)) {
                        Board newBoard = node.getBoard().moveSpaceTile(move);
                        if (!visitedBoards.contains(newBoard.toString())) {
                            numCreated++;
                            q.add(new Node(node, newBoard, node.getDepth() + 1));
                        }
                    }
                }
            }
        }
        if (!solutionFound) {
            System.out.println("-1, 0, 0, 0");
        }
    }

    public static void depthSearch(final Board initial, final String[] goal, final int... limit) {
        Stack<Node> s = new Stack<>();
        solutionFound = false;
        root = new Node(null, initial, 0);
        visitedBoards = new HashSet<>();
        numCreated = 1;
        numExpanded = 0;
        maxFringe = 0;

        s.push(root);
        while (!s.empty()) {
            if (s.size() > maxFringe) {
                maxFringe = s.size();
            }
            Node node = s.pop();
            numExpanded++;
            visitedBoards.add(node.getBoard().toString());
            if (Arrays.equals(goal, node.getBoard().getBoardAsLine())) {
                solutionFound = true;
                System.out.println(node.getDepth() + ", " + numCreated + ", " + numExpanded + ", " + maxFringe);
                break;
            } else if (limit.length == 0 || node.getDepth() < limit[0]) {
                Stack<Node> temp = new Stack<>();
                for (Move move : Move.values()) {
                    if (node.getBoard().isLegalMove(move)) {
                        Board newBoard = node.getBoard().moveSpaceTile(move);
                        if (!visitedBoards.contains(newBoard.toString())) {
                            numCreated++;
                            temp.push(new Node(node, newBoard, node.getDepth() + 1));
                        }
                    }
                }
                while (!temp.empty()) {
                    s.push(temp.pop());
                }
            }
        }
        if (!solutionFound) {
            System.out.println("-1, 0, 0, 0");
        }
    }

    public static void greedyBestFirst (final Board initial, final String[] goal, final String heuristic) {
        PriorityQueue<Node> pq = new PriorityQueue<>(new NodeHeuristicComparator());
        solutionFound = false;
        visitedBoards = new HashSet<>();
        numCreated = 1;
        numExpanded = 0;
        maxFringe = 0;
        root = new Node(null, initial, 0, heuristic.equals("h1")?initial.getH1(goal):initial.getH2(goal));
        long startTime = System.currentTimeMillis();

        pq.add(root);
        while(!pq.isEmpty()) {
            if (System.currentTimeMillis() - startTime > 90000) {
                break;
            }
            if (pq.size() > maxFringe) {
                maxFringe = pq.size();
            }
            Node node = pq.remove();
            numExpanded++;
            visitedBoards.add(node.getBoard().toString());
            if (Arrays.equals(goal, node.getBoard().getBoardAsLine())) {
                solutionFound = true;
                System.out.println(node.getDepth() + ", " + numCreated + ", " + numExpanded + ", " + maxFringe);
                break;
            } else {
                for (Move move : Move.values()) {
                    if (node.getBoard().isLegalMove(move)) {
                        Board newBoard = node.getBoard().moveSpaceTile(move);
                        if (!visitedBoards.contains(newBoard.toString())) {
                            numCreated++;
                            pq.add(new Node(node, newBoard, node.getDepth() + 1, heuristic.equals("h1") ?
                                                                                            newBoard.getH1(goal):
                                                                                            newBoard.getH2(goal)));
                        }
                    }
                }
            }
        }
        if (!solutionFound) {
            System.out.println("-1, 0, 0, 0");
        }
    }

    public static void aStar (final Board initial, final String[] goal, final String heuristic) {
        PriorityQueue<Node> pq = new PriorityQueue<>(new NodeHeuristicComparator());
        PriorityQueue<Node> solutions = new PriorityQueue<>(new NodeHeuristicComparator());
        visitedBoards = new HashSet<>();

        root = new Node(null, initial, 0, heuristic.equals("h1") ? initial.getH1(goal) : initial.getH2(goal));
        numCreated = 1;
        numExpanded = 0;
        maxFringe = 0;
        solutionFound = false;
        long startTime = System.currentTimeMillis();

        pq.add(root);
        while (!pq.isEmpty()) {
            if (System.currentTimeMillis() - startTime > 60000) {
                break;
            }
            if (pq.size() > maxFringe) {
                maxFringe = pq.size();
            }
            Node node = pq.remove();
            numExpanded++;
            if (!solutions.isEmpty()) {
                Node headSolution = solutions.remove();
                solutions.add(headSolution);
                if (headSolution.getHeuristic() <= node.getHeuristic()+node.getDepth()) {
                    break;
                }
            }
            if (Arrays.equals(goal, node.getBoard().getBoardAsLine())) {
                solutions.add(node);
            } else {
                visitedBoards.add(node.getBoard().toString());
                for (Move move : Move.values()) {
                    if (node.getBoard().isLegalMove(move)) {
                        Board newBoard = node.getBoard().moveSpaceTile(move);
                        if (!visitedBoards.contains(newBoard.toString())) {
                            Node child = new Node(node, newBoard, node.getDepth() + 1, heuristic.equals("h1") ?
                                    newBoard.getH1(goal) + (node.getDepth() + 1) :
                                    newBoard.getH2(goal) + (node.getDepth() + 1));
                            pq.add(child);

                        }
                    }
                }
            }
        }
        if (!solutions.isEmpty()) {
            Node bestNode = solutions.remove();
            System.out.println(bestNode.getDepth() + ", " + numCreated + ", " + numExpanded + ", " + maxFringe);
        } else {
            System.out.println("-1, 0, 0, 0");
        }
    }

    public static int solvable(final Board board) {
        String[] boardAsStringArray = board.getBoardAsLine();
        int[] boardAsIntArray = new int[boardAsStringArray.length];
        int spaceRow = board.getSpaceCoordinates()[0];
        int inversions = 0;

        for (int i = 0; i < boardAsStringArray.length; i++) {
            if (" ".equals(boardAsStringArray[i])) {
                boardAsIntArray[i] = 99;
            } else if ("A".equals(boardAsStringArray[i])) {
                boardAsIntArray[i] = 10;
            } else if ("B".equals(boardAsStringArray[i])) {
                boardAsIntArray[i] = 11;
            } else if ("C".equals(boardAsStringArray[i])) {
                boardAsIntArray[i] = 12;
            } else if ("D".equals(boardAsStringArray[i])) {
                boardAsIntArray[i] = 13;
            } else if ("E".equals(boardAsStringArray[i])) {
                boardAsIntArray[i] = 14;
            } else if ("F".equals(boardAsStringArray[i])) {
                boardAsIntArray[i] = 15;
            } else {
                boardAsIntArray[i] = Integer.parseInt(boardAsStringArray[i]);
            }
        }

        for (int i = 0; i < boardAsIntArray.length; i++) {
            if (boardAsIntArray[i] < 16) {
                for (int j = i + 1; j < boardAsIntArray.length; j++) {
                    if (boardAsIntArray[i] > boardAsIntArray[j]) {
                        inversions++;
                    }
                }
            }
        }

        boolean evenNumInversions = inversions % 2 == 0;
        boolean spaceOnOddRowFromBot = spaceRow % 2 == 1;

        if (evenNumInversions == spaceOnOddRowFromBot) {
            return 0;
        } else {
            return 1;
        }
    }

    public static void main(String[] args) {
        GOAL_STATES[0] = "123456789ABCDEF ".split("");
        GOAL_STATES[1] = "123456789ABCDFE ".split("");

        Board initial = new Board(args[0].split(""));
        String searchMethod = args[1];
        String searchOption = "";
        if (args.length > 2) {
            searchOption = args[2];
        }

        int goalNum = solvable(initial);
        switch (searchMethod) {
            case "BFS":
                breadthFirst(initial, GOAL_STATES[goalNum]);
                break;
            case "DFS":
                depthSearch(initial, GOAL_STATES[goalNum]);
                break;
            case "GBFS":
                greedyBestFirst(initial, GOAL_STATES[goalNum], searchOption);
                break;
            case "AStar":
                aStar(initial, GOAL_STATES[goalNum], searchOption);
                break;
            case "DLS":
                depthSearch(initial, GOAL_STATES[goalNum], Integer.parseInt(searchOption));
                break;
        }
    }
}