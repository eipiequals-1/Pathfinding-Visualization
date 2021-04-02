package com.company.algorithms;

import com.company.Node;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BreadthFirstSearch {

    private boolean doingAlgo = false;
    private final LinkedList<Node> queue = new LinkedList<>();
    private List<Node> visited = new ArrayList<>();

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
            Node current = queue.pollFirst();
            visited.add(current);

            if (current.equals(end)) {
                reconstructPath(start, end);
            }

            for (Node neighbor : current.getNeighbors()) {
                if (!visited.contains(neighbor)) {
                    queue.addLast(neighbor);
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
        start.makeStart();
        end.makeEnd();
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
