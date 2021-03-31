package com.company.algorithms;

import com.company.Node;

import java.util.*;

public class Dijkstra {

    private final int blockCost = 10;
    private final int diagonalCost = 14;
    private boolean doingAlgo = false;
    //private Queue<Node> openSet = new PriorityQueue<>(new Node.CostComparator());
    private List<Node> openList = new ArrayList<>();

    private int manhattan(int x1, int y1, int x2, int y2) {
        return blockCost * (Math.abs(x1 - x2) + Math.abs(y1 - y2));
    }
    private int diagonalDis(int x1, int y1, int x2, int y2) {
        int dx = Math.abs(x1 - x2);
        int dy = Math.abs(y1 - y2);
        return (int) (blockCost * Math.sqrt((dx * dx) + (dy * dy)));
        //return blockCost * Math.max(dx, dy) + (diagonalCost - blockCost) * Math.min(dx, dy);
    }

    private void reconstructPath(Node start, Node current) {
        // reconstruct path
        current.makeEnd();
        while (!current.getParentNode().equals(start)) {
            current = current.getParentNode();
            current.makePath();
        }
        doingAlgo = false;
    }

    public void noDiagonal(final Node start, final Node end) {
        if (!openList.isEmpty()) {

            // gets node with lowest f score
            Node current = openList.get(0);
            for (Node n : openList) {
                if (n.getFScore() < current.getFScore()) {
                    current = n;
                }
            }
            // remove it from the open set
            openList.remove(current);

            if (current.equals(end)) {
                reconstructPath(start, end);
            }

            for (Node neighbor : current.getNeighbors()) {
                int newDis = current.getFScore() + manhattan(current.getX(), current.getY(), neighbor.getX(), neighbor.getY());

                if (newDis < neighbor.getFScore()) {
                    neighbor.setFScore(newDis);
                    neighbor.setParent(current);
                }
            }
            if (!current.equals(start)) {
                current.makeOpen();
            }
        }
    }

    public void diagonal(final Node start, final Node end) {
        if (!openList.isEmpty()) {

            // gets node with lowest f score
            Node current = openList.get(0);
            for (Node n : openList) {
                if (n.getFScore() < current.getFScore()) {
                    current = n;
                }
            }
            // remove it from the open set
            openList.remove(current);

            if (current.equals(end)) {
                reconstructPath(start, end);
            }

            for (Node neighbor : current.getNeighbors()) {
                int newDis = current.getFScore() + diagonalDis(current.getX(), current.getY(), neighbor.getX(), neighbor.getY());

                if (newDis < neighbor.getFScore()) {
                    neighbor.setFScore(newDis);
                    neighbor.setParent(current);
                }
            }
            if (!current.equals(start)) {
                current.makeOpen();
            }
        }
    }

    public void setUpGraph(final Node[][] grid, final Node start) {
        doingAlgo = true;

        for (Node[] y : grid) {
            for (Node x : y) {
                x.setFScore(Integer.MAX_VALUE);
                openList.add(x);
            }
        }
        start.setFScore(0);
    }

    public boolean isDoingAlgo() {
        return doingAlgo;
    }

    public void reset() {
        openList.clear();
        doingAlgo = false;
    }
}
