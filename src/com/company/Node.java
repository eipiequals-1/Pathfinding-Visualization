package com.company;

import java.awt.*;
import java.util.ArrayList;

public class Node {

    // Color constants
    private final static Color START_COLOR = Color.BLUE;
    private final static Color END_COLOR = Color.RED;
    private final static Color WALL_COLOR = new Color(12, 53, 71);
    private final static Color EMPTY_COLOR = Color.WHITE;
    private final static Color OPEN_COLOR = new Color(175, 216, 248);
    private final static Color CLOSED_COLOR = new Color(122, 156, 199);
    private final static Color PATH_COLOR = new Color(255, 254, 106);

    private final int x;
    private final int y;
    private final int width;
    private final int height;

    private final int rows;
    private final int cols;

    private Node parent;
    private final ArrayList<Node> neighbors;

    private Color color;

    private int f;
    private int h;
    private int g;


    private int animationWidth;
    private final static int animationSpeed = 3;
    private boolean finishedAnimation = false;

    public Node(final int x, final int y, final int w, final int h, final int rows, final int cols) {
        this.x = x;
        this.y = y;
        this.color = EMPTY_COLOR;
        this.width = w;
        this.height = h;
        this.rows = rows;
        this.cols = cols;

        this.f = Integer.MAX_VALUE;
        this.g = Integer.MAX_VALUE;
        this.h = 0;

        this.parent = null;
        this.neighbors = new ArrayList<>();

        animationWidth = width / 2;
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

    public void setParent(final Node parent) {
        this.parent = parent;
    }

    public Node getParentNode() {
        return parent;
    }

    public void draw(final Graphics g, final boolean showCosts) {
        g.setColor(color);
        if (!finishedAnimation && color != EMPTY_COLOR) {
            g.fillRect(x * width + width / 2 - animationWidth / 2, y * height + height / 2 - animationWidth / 2, animationWidth, animationWidth);
            animationWidth += animationSpeed;
            if (animationWidth > width) {
                finishedAnimation = true;
            }
        } else {
            g.fillRect(x * width, y * height, width, height);
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("uroob", Font.PLAIN, 15));
        FontMetrics metrics = g.getFontMetrics();
        String fScore = "" + f;
        String gScore = "" + this.g;
        String hScore = "" + h;
        if ((color == PATH_COLOR || color == CLOSED_COLOR || color == OPEN_COLOR) && showCosts && width >= 40) {
            g.drawString(fScore, x * width + width / 2 - metrics.stringWidth(fScore) / 2, y * height + 15);
            g.drawString(gScore, x * width + 5, (y + 1) * height - 2);
            g.drawString(hScore, (x + 1) * width - 2 - metrics.stringWidth(hScore), (y + 1) * height - 2);
        }
    }

    public void makeStart() {
        color = START_COLOR;
    }

    public void makeEnd() {
        color = END_COLOR;
    }

    public void makePath() {
        color = PATH_COLOR;
        resetAnimation();
    }

    public void makeWall() {
        color = WALL_COLOR;
        resetAnimation();
    }

    public void makeOpen() {
        color = OPEN_COLOR;
        resetAnimation();
    }

    public void makeClosed() {
        color = CLOSED_COLOR;
        resetAnimation();
    }

    public boolean isWall() {
        return color == WALL_COLOR;
    }

    public boolean isStart() {
        return color == START_COLOR;
    }

    public boolean isEnd() {
        return color == END_COLOR;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public void reset() {
        color = EMPTY_COLOR;
        f = Integer.MAX_VALUE;
        g = Integer.MAX_VALUE;
        h = 0;
        parent = null;
        neighbors.clear();
    }

    private void resetAnimation() {
        animationWidth = width / 2;
        finishedAnimation = false;
    }

    public void setNeighbors(final Node[][] grid, final boolean diagonal) {
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
}
