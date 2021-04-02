package com.company.algorithms;

import com.company.Node;

import java.util.ArrayList;
import java.util.List;

public class Dijkstra {

    private final int blockCost = 10;
    private final int diagonalCost = 14;
    private boolean doingAlgo = false;
    private List<Node> openList = new ArrayList<>();

    private int manhattan(final int x1, final int y1, final int x2, final int y2) {
        return blockCost * (Math.abs(x1 - x2) + Math.abs(y1 - y2));
    }
    private int diagonalDis(final int x1, final int y1, final int x2, final int y2) {
        int dx = Math.abs(x1 - x2);
        int dy = Math.abs(y1 - y2);
        return blockCost * Math.max(dx, dy) + (diagonalCost - blockCost) * Math.min(dx, dy);
        //return (int) (blockCost * Math.sqrt((dx * dx) + (dy * dy)));
    }

    private void reconstructPath(final Node start, final Node current) {
        // reconstruct path
        Node curr = current;
        curr.makeEnd();
        while (!curr.getParentNode().equals(start)) {
            curr = curr.getParentNode();
            curr.makePath();
        }
        doingAlgo = false;
    }

    public void noDiagonal(final Node start, final Node end) {
        if (!openList.isEmpty()) {

            // gets node with lowest cost
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
                    if (!openList.contains(neighbor)) {
                        openList.add(neighbor);
                        neighbor.makeOpen();
                    }
                }
            }
            if (!current.equals(start)) {
                current.makeClosed();
            }
        } else {
            doingAlgo = false;
        }
        start.makeStart();
        end.makeEnd();
    }

    public void diagonal(final Node start, final Node end) {
        if (!openList.isEmpty()) {

            // gets node with lowest cost
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
                    if (!openList.contains(neighbor)) {
                        openList.add(neighbor);
                        neighbor.makeOpen();
                    }
                }
            }
            if (!current.equals(start)) {
                current.makeClosed();
            }
        } else {
            doingAlgo = false;
        }
        start.makeStart();
        end.makeEnd();
    }

    public void setUpGraph(final Node start) {
        doingAlgo = true;
        start.setFScore(0);
        openList.add(start);
    }

    public boolean isDoingAlgo() {
        return doingAlgo;
    }

    public void reset() {
        openList.clear();
        doingAlgo = false;
    }
}
