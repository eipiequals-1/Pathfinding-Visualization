package com.company.algorithms;

import com.company.Node;

import java.util.*;

public class BreadthFirstSearch {

    private final int blockCost = 10;
    private boolean doingAlgo = false;
    private final List<Node> queue = new ArrayList<>();
    private List<Node> visited = new ArrayList<>();

    private int manhattan(final int x1, final int y1, final int x2, final int y2) {
        return blockCost * (Math.abs(x1 - x2) + Math.abs(y1 - y2));
    }
    private int diagonalDis(final int x1, final int y1, final int x2, final int y2) {
        int dx = Math.abs(x1 - x2);
        int dy = Math.abs(y1 - y2);
        return (int) (blockCost * Math.sqrt((dx * dx) + (dy * dy)));
        //return blockCost * Math.max(dx, dy) + (diagonalCost - blockCost) * Math.min(dx, dy);
    }

    private void reconstructPath(final Node start, final Node current) {
        // reconstruct path
        Node curr = current;
        while (!curr.getParentNode().equals(start)) {
            curr = curr.getParentNode();
            curr.makePath();
        }
        current.makeEnd();
        doingAlgo = false;
    }

    public void noDiagonal(final Node start, final Node end) {
        if (!queue.isEmpty()) {
            Node current = queue.remove(0);
            visited.add(current);

            if (current.equals(end)) {
                reconstructPath(start, end);
            }

            for (Node neighbor : current.getNeighbors()) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                    neighbor.setParent(current);
                    neighbor.makeOpen();
                }
            }

            if (!current.equals(start)) {
                current.makeClosed();
            }
        } else {
            doingAlgo = false;
        }
    }

    public void setUpGraph(final Node start) {
        queue.add(start);
        visited.add(start);
        doingAlgo = true;
    }

    public void reset() {
        queue.clear();
        visited.clear();
    }

    public boolean isDoingAlgo() {
        return doingAlgo;
    }
}
