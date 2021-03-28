package com.company.algorithms;

import com.company.Node;

public class Dijkstra {

    private final int blockCost = 10;
    private final int diagonalCost = 14;
    private boolean finishedAlgo = false;

    private int manhattan(int x1, int y1, int x2, int y2) {
        return blockCost * (Math.abs(x1 - x2) + Math.abs(y1 - y2));
    }

    private int findMinDistance(int[] distance, boolean[] visited) {
        int minDistance = Integer.MAX_VALUE;
        int minDistanceVertex = -1;

        return minDistanceVertex;
    }

    private int diagonalDis(int x1, int y1, int x2, int y2) {
        int dx = Math.abs(x1 - x2);
        int dy = Math.abs(y1 - y2);
        return (int) (blockCost * Math.sqrt((dx * dx) + (dy * dy)));
        //return blockCost * Math.max(dx, dy) + (diagonalCost - blockCost) * Math.min(dx, dy);
    }

    public void diagonal(Node[][] grid, Node start, Node end) {
        int count = grid.length;
        boolean[] visitedVertex = new boolean[count];
        int[] distance = new int[count];
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                Node node = grid[y][x];

            }
        }
    }
}
