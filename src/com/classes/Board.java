package com.classes;

import java.util.Arrays;
import java.lang.Math;

/**
 * Board class encapsulates board state and methods related to chnaging information about the board state.
 */
public class Board {

    /* Global Variables: */
    private String[] boardAsLine;
    private String[][] boardAsGrid;
    private int spaceRow;
    private int spaceCol;

    /**
     * A simple enumeration class to represent the possible moves any given tile could make within the board.
     */
    public enum Move {
        R, D, L, U
    }

    /**
     * Board constructor if board is given as 1D array.
     */
    Board(final String[] inputArray) {
        boardAsLine = inputArray;
        boardAsGrid = lineToGrid(inputArray);
        int[] spaceCoordinates = getSpaceCoordinates();
        spaceRow = spaceCoordinates[0];
        spaceCol = spaceCoordinates[1];
    }

    /**
     * Board constructor if board is given as 2D array.
     */
    private Board(final String[][] inputGrid) {
        boardAsGrid = inputGrid;
        boardAsLine = gridToLine(inputGrid);
        int[] spaceCoordinates = getSpaceCoordinates();
        spaceRow = spaceCoordinates[0];
        spaceCol = spaceCoordinates[1];
    }

    /**
     * Returns the board state represented as a 1D array.
     */
    String[] getBoardAsLine() {
        return boardAsLine;
    }

    /**
     * Returns the board state represented as a 2D array.
     */
    private String[][] getBoardAsGrid() {
        return boardAsGrid;
    }

    /**
     * Returns a new board object where the space tile has been swapped with another tile based on the input move.
     * Returns null if the move trying to be made isn't a legal move.
     */
    Board moveSpaceTile(Move move) {
        String[][] newGrid = deepCopy(boardAsGrid);

        if (!isLegalMove(move)) {
            return null;
        }

        switch (move) {
            case R:
                String tempR = newGrid[spaceRow][spaceCol+1];
                newGrid[spaceRow][spaceCol+1] = " ";
                newGrid[spaceRow][spaceCol] = tempR;
                break;
            case D:
                String tempD = newGrid[spaceRow+1][spaceCol];
                newGrid[spaceRow+1][spaceCol] = " ";
                newGrid[spaceRow][spaceCol] = tempD;
                break;
            case L:
                String tempL = newGrid[spaceRow][spaceCol -1];
                newGrid[spaceRow][spaceCol-1] = " ";
                newGrid[spaceRow][spaceCol] = tempL;
                break;
            case U:
                String tempU = newGrid[spaceRow -1][spaceCol];
                newGrid[spaceRow-1][spaceCol] = " ";
                newGrid[spaceRow][spaceCol] = tempU;
                break;
        }

        return new Board(newGrid);
    }

    /**
     * Returns the coordinates of the space character inside of the 4x4 representation of the board.
     */
    int[] getSpaceCoordinates() {
        int[] coordinates = new int[2];

        for (int i = 0 ; i < boardAsGrid.length; i++) {
            for (int j = 0; j < boardAsGrid[i].length; j++) {
                if (boardAsGrid[i][j].equals(" ")) {
                    coordinates[0] = i;
                    coordinates[1] = j;
                }
            }
        }

        return coordinates;
    }

    /**
     * Returns the number of misplaced tile on the board relative to the goal state that the board is solvable to.
     */
    int getH1(final String[] goal) {
        int num = 0;
        String[][] goalGrid = lineToGrid(goal);
        for (int i = 0; i < goalGrid.length; i++) {
            for (int j = 0; j < goalGrid[i].length; j++) {
                if (!(boardAsGrid[i][j].equals(goalGrid[i][j]))) {
                    num++;
                }
            }
        }
        return num;
    }

    /**
     * Returns the sum of each tile's manhattan distances from its current position to the position it should be in to
     * match the goal state.
     */
    int getH2(final String[] goal) {
        int totDist = 0;
        String[][] goalGrid = lineToGrid(goal);
        for (int i = 0; i < goalGrid.length; i++) {
            for (int j = 0; j < goalGrid[i].length; j++) {
                String tile = goalGrid[i][j];
                for (int k = 0; k < boardAsGrid.length; k++) {
                    for (int l = 0; l < boardAsGrid[i].length; l++) {
                        if (tile.equals(boardAsGrid[k][l])) {
                            int tileDist = Math.abs(i-k)+Math.abs(j-l);
                            totDist += tileDist;
                            break;
                        }
                    }
                }
            }
        }
        return totDist;
    }

    /**
     * Returns whether a given move with the current board state is legal, or still within the 4x4 grid of the board.
     */
    boolean isLegalMove(final Move move) {
        int row = spaceRow;
        int col = spaceCol;

        switch (move) {
            case R:
                col++;
                break;
            case D:
                row++;
                break;
            case L:
                col--;
                break;
            case U:
                row--;
                break;
        }

        return (row >= 0 && row <= 3 && col >= 0 && col <= 3);
    }

    /**
     * Returns the 1D array representation of the board as a string.
     */
    @Override
    public String toString() {
        return Arrays.toString(boardAsLine);
    }

    /**
     * Determines whether the given board object is equivalent to this one by matching every tile.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
         if (!(obj instanceof Board)) {
             return false;
         }

         boolean ans = true;
         String[][] thisArray = this.getBoardAsGrid();
         String[][] otherArray = ((Board) obj).getBoardAsGrid();
         for(int i = 0; i < otherArray.length; i++) {
             for (int j = 0; j < otherArray[i].length; j++) {
                 if (!(thisArray[i][j].equals(otherArray[i][j]))) {
                    ans = false;
                    break;
                 }
             }
         }
         return ans;
    }

    /**
     * Converts the given 2D array representation of the board to its 1D representation.
     */
    private String[] gridToLine(final String[][] input) {
        String[] line = new String[input.length*input.length];

        for (int i = 0; i < input.length; i++) {
            System.arraycopy(input[i],0, line, (i*4), 4);
        }

        return line;
    }

    /**
     * Converts the given 1D array representation of the board to its 2D representation.
     */
    private String[][] lineToGrid(final String[] input) {
        String[][] grid = new String[4][4];

        for (int i = 0; i < grid.length; i++) {
            System.arraycopy(input, (i*4), grid[i], 0, 4);
        }

        return grid;
    }

    /**
     * Creates a copy of a given 2D array.
     */
    private String[][] deepCopy(final String[][] input) {
        String[][] newArray = new String[input.length][input[0].length];
        for (int i = 0; i < newArray.length; i++) {
            newArray[i] = Arrays.copyOf(input[i], input[i].length);
        }
        return newArray;
    }
}