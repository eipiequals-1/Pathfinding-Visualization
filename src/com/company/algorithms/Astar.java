package com.company.algorithms;

import com.company.Node;

import java.util.ArrayList;
import java.util.List;

public class Astar extends PathfindingAlgo {

    private final List<Node> openList = new ArrayList<>();

    @Override
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
                int tempG = current.getGScore() + manhattan(current.getX(), current.getY(), neighbor.getX(), neighbor.getY());

                if (tempG < neighbor.getGScore()) {
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
            if (!current.equals(start)) {
                current.makeClosed();
            }
        } else {
            doingAlgo = false;
        }
        start.makeStart();
        end.makeEnd();
    }

    @Override
    public void setUpGraph(Node start) {
        start.setGScore(0);
        start.setFScore(0);
        openList.add(start);
        doingAlgo = true;
    }

    @Override
    public void reset() {
        openList.clear();
        doingAlgo = false;
    }

    @Override
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
                int tempG = current.getGScore() + diagonalDis(current.getX(), current.getY(), neighbor.getX(), neighbor.getY());

                if (tempG < neighbor.getGScore()) {
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
            if (!current.equals(start)) {
                current.makeClosed();
            }
        } else {
            doingAlgo = false;
        }
        start.makeStart();
        end.makeEnd();
    }
}