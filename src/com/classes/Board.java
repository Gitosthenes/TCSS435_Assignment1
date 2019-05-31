package com.classes;

import java.util.Arrays;
import java.lang.Math;

public class Board {
    private String[] boardAsLine;
    private String[][] boardAsGrid;
    private int spaceRow;
    private int spaceCol;

    Board(final String[] inputArray) {
        boardAsLine = inputArray;
        boardAsGrid = lineToGrid(inputArray);
        int[] spaceCoordinates = getSpaceCoordinates();
        spaceRow = spaceCoordinates[0];
        spaceCol = spaceCoordinates[1];
    }

    Board(final String[][] inputGrid) {
        boardAsGrid = inputGrid;
        boardAsLine = gridToLine(inputGrid);
        int[] spaceCoordinates = getSpaceCoordinates();
        spaceRow = spaceCoordinates[0];
        spaceCol = spaceCoordinates[1];
    }

    public String[] getBoardAsLine() {
        return boardAsLine;
    }

    public String[][] getBoardAsGrid() {
        return boardAsGrid;
    }

    public Board moveSpaceTile(Move move) {
        String[][] newGrid = deepCopy(boardAsGrid);

        //System.out.println(spaceRow + ", " + spaceCol);

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

    public int[] getSpaceCoordinates() {
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

    public int getH1(final String[] goal) {
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

    public int getH2(final String[] goal) {
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

    public boolean isLegalMove(final Move move) {
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

    @Override
    public String toString() {
        return Arrays.toString(boardAsLine);
    }

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

    private String[] gridToLine(final String[][] input) {
        String[] line = new String[input.length*input.length];

        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                line[(i*input.length)+j] = input[i][j];
            }
        }

        return line;
    }

    private String[][] lineToGrid(final String[] input) {
        String[][] grid = new String[4][4];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = input[(i*4)+j];
            }
        }

        return grid;
    }

    private String[][] deepCopy(final String[][] input) {
        String[][] newArray = new String[input.length][input[0].length];
        for (int i = 0; i < newArray.length; i++) {
            newArray[i] = Arrays.copyOf(input[i], input[i].length);
        }
        return newArray;
    }
}
