package com.company;

import java.awt.*;
import java.util.ArrayList;

public class Node {

    private int x;
    private int y;

    private int width;
    private int height;

    private Node parent;
    private ArrayList<Node> neighbors;

    private Color color;

    private int f;
    private int h;
    private int g;

    public Node(final int x, final int y, final int w, final int h) {
        this.x = x;
        this.y = y;
        this.color = Color.WHITE;
        this.width = w;
        this.height = h;

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

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x * width, y * height, width, height);

        g.setColor(Color.BLACK);
        g.setFont(new Font("uroob", Font.PLAIN, 15));
        FontMetrics metrics = g.getFontMetrics();
        String fScore = "" + f;
        String gScore = "" + this.g;
        String hScore = "" + h;
        if (color != Color.WHITE) {
            g.drawString(fScore, x * width + width / 2 - metrics.stringWidth(fScore) / 2, y * height + 15);
            g.drawString(gScore, x * width + 5, (y + 1) * height - 2);
            g.drawString(hScore, (x + 1) * width - 2 - metrics.stringWidth(hScore), (y + 1) * height - 2);
        }

    }

    public void makeStart() {
        color = Color.BLUE;
    }

    public void makeEnd() {
        color = Color.RED;
    }

    public void makePath() {
        color = new Color(32, 233, 255);
    }

    public void reset() {
        color = Color.WHITE;
        f = 0;
        g = 0;
        h = 0;
        parent = null;
        neighbors.clear();
    }

    public void makeWall() {
        color = Color.BLACK;
    }

    public void makeOpen() {
        color = new Color(132, 255, 138);
    }

    public void makeClosed() {
        color = new Color(253, 90, 90);
    }

    public boolean isWall() {
        return color == Color.BLACK;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setNeighbors(final Node[][] grid, boolean diagonal) {
        // DOWN
        if (y + 1 < Grid.ROWS && !grid[y + 1][x].isWall()) {
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
        if (x + 1 < Grid.COLS && !grid[y][x + 1].isWall()) {
            neighbors.add(grid[y][x + 1]);
        }
        if (diagonal) {
            // BOTTOM LEFT
            if (y + 1 < Grid.ROWS && x - 1 > -1 && !grid[y + 1][x - 1].isWall()) {
                neighbors.add(grid[y + 1][x - 1]);
            }
            // BOTTOM RIGHT
            if (y + 1 < Grid.ROWS && x + 1 < Grid.COLS && !grid[y + 1][x + 1].isWall()) {
                neighbors.add(grid[y + 1][x + 1]);
            }
            // TOP LEFT
            if (y - 1 > -1 && x - 1 > -1 && !grid[y - 1][x - 1].isWall()) {
                neighbors.add(grid[y - 1][x - 1]);
            }
            // TOP RIGHT
            if (y - 1 > -1 && x + 1 < Grid.COLS && !grid[y - 1][x + 1].isWall()) {
                neighbors.add(grid[y - 1][x + 1]);
            }
        }
    }

    public ArrayList<Node> getNeighbors() {
        return neighbors;
    }
}
