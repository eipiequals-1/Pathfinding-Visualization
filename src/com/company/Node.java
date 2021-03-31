package com.company;

import java.awt.*;
import java.io.Serializable;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.ArrayList;
import java.util.Comparator;

public class Node implements Serializable, Comparable<Node> {

    private static final long serialVersionUID = 5343869126593129023L;

    private int x;
    private int y;

    private int width;
    private int height;

    private int rows;
    private int cols;

    private Node parent;
    private ArrayList<Node> neighbors;

    private Color color;

    private int f;
    private int h;
    private int g;

    private static Color startColor = Color.BLUE;
    private static Color endColor = Color.RED;
    private static Color wallColor = Color.BLACK;
    private static Color emptyColor = Color.WHITE;
    private static Color openColor = new Color(132, 255, 138);
    private static Color closedColor = new Color(253, 90, 90);
    private static Color pathColor = new Color(32, 233, 255);

    public Node(final int x, final int y, final int w, final int h, int rows, int cols) {
        this.x = x;
        this.y = y;
        this.color = Color.WHITE;
        this.width = w;
        this.height = h;
        this.rows = rows;
        this.cols = cols;

        this.f = 0;
        this.g = 0;
        this.h = 0;

        this.parent = null;
        this.neighbors = new ArrayList<>();
    }

    public void setFScore(final int f) {
        this.f = f;
    }

    public int getFScore() {
        return f;
    }

    public void setHScore(final int h) {
        this.h = h;
    }

    public int getHScore() {
        return h;
    }
    public void setGScore(final int g) {
        this.g = g;
    }

    public int getGScore() {
        return g;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getParentNode() {
        return parent;
    }

    public void draw(Graphics g, boolean showCosts) {
        g.setColor(color);
        g.fillRect(x * width, y * height, width, height);

        g.setColor(Color.BLACK);
        g.setFont(new Font("uroob", Font.PLAIN, 15));
        FontMetrics metrics = g.getFontMetrics();
        String fScore = "" + f;
        String gScore = "" + this.g;
        String hScore = "" + h;
        if (color != Color.WHITE && showCosts && width <= 40) {
            g.drawString(fScore, x * width + width / 2 - metrics.stringWidth(fScore) / 2, y * height + 15);
            g.drawString(gScore, x * width + 5, (y + 1) * height - 2);
            g.drawString(hScore, (x + 1) * width - 2 - metrics.stringWidth(hScore), (y + 1) * height - 2);
        }

    }

    public void makeStart() {
        color = startColor;
    }

    public void makeEnd() {
        color = endColor;
    }

    public void makePath() {
        color = pathColor;
    }

    public void makeWall() {
        color = wallColor;
    }

    public void makeOpen() {
        color = openColor;
    }

    public void makeClosed() {
        color = closedColor;
    }

    public boolean isWall() {
        return color == wallColor;
    }

    public boolean isStart() {
        return color == startColor;
    }

    public boolean isEnd() {
        return color == endColor;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void reset() {
        color = Color.WHITE;
        f = 0;
        g = 0;
        h = 0;
        parent = null;
        neighbors.clear();
    }

    public void setNeighbors(final Node[][] grid, boolean diagonal) {
        // DOWN
        if (y + 1 < rows && !grid[y + 1][x].isWall()) {
            neighbors.add(grid[y + 1][x]);
        }
        // UP
        if (y - 1 > -1 && !grid[y - 1][x].isWall()) {
            neighbors.add(grid[y - 1][x]);
        }
        // LEFT
        if (x - 1 > -1 && !grid[y][x - 1].isWall()) {
            neighbors.add(grid[y][x - 1]);
        }
        // RIGHT
        if (x + 1 < cols && !grid[y][x + 1].isWall()) {
            neighbors.add(grid[y][x + 1]);
        }
        if (diagonal) {
            // BOTTOM LEFT
            if (y + 1 < rows && x - 1 > -1 && !grid[y + 1][x - 1].isWall()) {
                neighbors.add(grid[y + 1][x - 1]);
            }
            // BOTTOM RIGHT
            if (y + 1 < rows && x + 1 < rows && !grid[y + 1][x + 1].isWall()) {
                neighbors.add(grid[y + 1][x + 1]);
            }
            // TOP LEFT
            if (y - 1 > -1 && x - 1 > -1 && !grid[y - 1][x - 1].isWall()) {
                neighbors.add(grid[y - 1][x - 1]);
            }
            // TOP RIGHT
            if (y - 1 > -1 && x + 1 < rows && !grid[y - 1][x + 1].isWall()) {
                neighbors.add(grid[y - 1][x + 1]);
            }
        }
    }

    public ArrayList<Node> getNeighbors() {
        return neighbors;
    }

    @Override
    public int compareTo(final Node node) {
        //return node.f < this.f ? 1 : -1;
        return (node.f - f);
    }

    @Override
    public String toString() {
        return "isStart: " + isStart() + ", f Cost: " + f + ", (x, y): (" + x + ", " + y + ")";
    }

    public static class CostComparator implements Comparator<Node> {
        @Override
        public int compare(final Node node1, final Node node2) {
            if (node1.f < node2.f) {
                return -1;
            } else if (node1.f > node2.f) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
