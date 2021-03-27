package com.company.algorithms;

import com.company.Node;

import java.util.ArrayList;

public class Astar {

    private final int blockCost = 10;
    private final int diagonalCost = 14;
    private final ArrayList<Node> openList = new ArrayList<>();
    private boolean finishedAlgo = false;

    private int manhattan(int x1, int y1, int x2, int y2) {
        return blockCost * (Math.abs(x1 - x2) + Math.abs(y1 - y2));
    }

    private int diagonalDis(int x1, int y1, int x2, int y2) {
        int dx = Math.abs(x1 - x2);
        int dy = Math.abs(y1 - y2);
        return (int) (blockCost * Math.sqrt((dx * dx) + (dy * dy)));
        //return blockCost * Math.max(dx, dy) + (diagonalCost - blockCost) * Math.min(dx, dy);
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

            if (current == end) {
                // reconstruct path
                Node currentNode = current; // copying the value
                while (currentNode.getParentNode() != start) {
                    currentNode = currentNode.getParentNode();
                    currentNode.makePath();
                }
                start.makeStart();
                end.makeEnd();
                finishedAlgo = true;
            }

            for (Node neighbor : current.getNeighbors()) {
                int tempG = current.getGScore() + manhattan(current.getX(), current.getY(), neighbor.getX(), neighbor.getY());

                if (neighbor.getGScore() == 0 || tempG < neighbor.getGScore()) {
                    // gscore is better
                    neighbor.setParent(current);

                    neighbor.setGScore(tempG);
                    neighbor.setHScore(manhattan(neighbor.getX(), neighbor.getY(), end.getX(), end.getY()));
                    neighbor.setFScore(neighbor.getGScore() + neighbor.getHScore());

                    if (!openList.contains(neighbor)) {
                        openList.add(neighbor);
                        neighbor.makeOpen();
                    }
                }
            }
            if (current != start) {
                current.makeClosed();
            }
        }
    }

    public void addStartNode(Node n) {
        openList.add(n);
    }

    public boolean isAlgoFinished() {
        return !finishedAlgo;
    }

    public void reset() {
        openList.clear();
        finishedAlgo = false;
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

            if (current == end) {
                // reconstruct path
                Node currentNode = current; // copying the value
                while (currentNode.getParentNode() != start) {
                    currentNode = currentNode.getParentNode();
                    currentNode.makePath();
                }
                start.makeStart();
                end.makeEnd();
                finishedAlgo = true;
            }

            for (Node neighbor : current.getNeighbors()) {
                int tempG = current.getGScore() + diagonalDis(current.getX(), current.getY(), neighbor.getX(), neighbor.getY());

                if (neighbor.getGScore() == 0 || tempG < neighbor.getGScore()) {
                    // gscore is better
                    neighbor.setParent(current);

                    neighbor.setGScore(tempG);
                    neighbor.setHScore(diagonalDis(neighbor.getX(), neighbor.getY(), end.getX(), end.getY()));
                    neighbor.setFScore(neighbor.getGScore() + neighbor.getHScore());

                    if (!openList.contains(neighbor)) {
                        openList.add(neighbor);
                        neighbor.makeOpen();
                    }
                }
            }
            if (current != start) {
                current.makeClosed();
            }
        }
    }
}