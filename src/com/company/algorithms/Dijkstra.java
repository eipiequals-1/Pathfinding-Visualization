package com.company.algorithms;

import com.company.Node;

import java.util.ArrayList;
import java.util.List;

public class Dijkstra extends PathfindingAlgo {

    private List<Node> openList = new ArrayList<>();

    @Override
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

    @Override
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

    @Override
    public void setUpGraph(final Node start) {
        doingAlgo = true;
        start.setFScore(0);
        openList.add(start);
    }

    @Override
    public void reset() {
        openList.clear();
        doingAlgo = false;
    }
}
