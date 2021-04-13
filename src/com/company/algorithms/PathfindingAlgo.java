package com.company.algorithms;

import com.company.Node;

public abstract class PathfindingAlgo {

    protected final int BLOCK_COST = 10;
    protected final int DIAGONAL_COST = 14;
    protected boolean doingAlgo = false;

    protected final int manhattan(final int x1, final int y1, final int x2, final int y2) {
        return BLOCK_COST * (Math.abs(x1 - x2) + Math.abs(y1 - y2));
    }

    protected final int diagonalDis(final int x1, final int y1, final int x2, final int y2) {
        int dx = Math.abs(x1 - x2);
        int dy = Math.abs(y1 - y2);
        //return (int) (blockCost * Math.sqrt((dx * dx) + (dy * dy)));
        return BLOCK_COST * Math.max(dx, dy) + (DIAGONAL_COST - BLOCK_COST) * Math.min(dx, dy);
    }

    protected final void reconstructPath(Node start, Node current) {
        // reconstruct path
        while (!current.getParentNode().equals(start)) {
            current = current.getParentNode();
            current.makePath();
        }
        start.makeStart();
        doingAlgo = false;
    }

    public boolean isDoingAlgo() {
        return doingAlgo;
    }

    abstract public void reset();
    abstract public void setUpGraph(final Node start);
    abstract public void noDiagonal(final Node start, final Node end);
    abstract public void diagonal(final Node start, final Node end);
}
