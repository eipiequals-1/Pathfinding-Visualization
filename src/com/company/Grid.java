package com.company;

import com.company.algorithms.Astar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Grid extends JPanel implements ActionListener {

    private final int ROWS = 20;
    private final int COLS = 20;
    private final int CELL_W = Frame.WIDTH / COLS;
    private final int CELL_H = (Frame.HEIGHT - Frame.BUFFER_HEIGHT) / ROWS;

    private Node[][] grid;

    private final int maxDelay = 100;
    private final int minDelay = 1;

    private final JCheckBox diagonal = new JCheckBox("diagonal", false);
    private final JCheckBox showCosts =new JCheckBox("show values", false);
    private final JSlider delay = new JSlider(minDelay, maxDelay);

    private Node start;
    private Node end;

    private Node startCopy;
    private Node endCopy;

    private JButton visualize;
    private JButton clear;
    private JButton reset;

    private final Timer timer;

    private Algorithms algorithm = Algorithms.A_STAR;

    private final Astar aStar = new Astar();

    public Grid() {
        setLayout(null);
        setFocusable(true);
        setUpPanel();
        addMouseListener(new NewMouseListener());
        timer = new Timer(maxDelay - delay.getValue(), this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, Frame.WIDTH, Frame.HEIGHT);
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLS; x++) {
                grid[y][x].draw(g, showCosts.isSelected());
            }
        }
        drawGrid(g);
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        // updating method of the visualization
        if (algorithm == Algorithms.A_STAR) {
            if (!diagonal.isSelected() && aStar.isAlgoFinished()) {
                aStar.noDiagonal(start, end);
            } else if (diagonal.isSelected() && aStar.isAlgoFinished()) {
                aStar.diagonal(start, end);
            }
        } else if (algorithm == Algorithms.DIJKSTRA) {
        } else if (algorithm == Algorithms.BFS) {}
        timer.setDelay(maxDelay - delay.getValue());
        repaint();
    }

    private class VisualizeListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent e) {
            if ((start != null) && (end != null)) {
                for (int y = 0; y < ROWS; y++) {
                    for (int x = 0; x < COLS; x++) {
                        grid[y][x].setNeighbors(grid, diagonal.isSelected());
                        startCopy = start;
                        endCopy = end;
                    }
                }
                aStar.addStartNode(start);
            }
        }
    }

    private class ClearListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent e) {
            resetGrid();
        }
    }

    private class ResetListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent e) {
            resetGrid();
            try {
                start = startCopy;
                end = endCopy;
                start.makeStart();
                end.makeEnd();
            } catch (Exception ex) {}
        }
    }

    private void resetGrid() {
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLS; x++) {
                grid[y][x].reset();
                start = null;
                end = null;
                aStar.reset();
            }
        }
    }

    private class NewMouseListener implements MouseListener {
        @Override
        public void mousePressed(final MouseEvent e) {
            // the y coordinate might be out of range
            try {
                int x = e.getX();
                int y = e.getY();
                Node n = grid[y / CELL_H][x / CELL_W];
                // add a colored node to the board
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if ((end == null) && (start != null)) {
                        n.makeEnd();
                        end = n;
                    } else if (start == null) {
                        n.makeStart();
                        start = n;
                    } else {
                        if ((n != start) && (n != end)) {
                            n.makeWall();
                        }
                    }
                }
                // remove a colored node; clear the node
                else if (SwingUtilities.isRightMouseButton(e)) {
                    if (n == start) {
                        start = null;
                    } else if ((n == end) && (start == null)) {
                        end = null;
                    } else if ((n == end) && (start != null)) {
                        end = null;
                    }
                    n.reset();
                }
            } catch (Exception ex) {
            }
        }
        @Override
        public void mouseReleased(final MouseEvent e) {}
        @Override
        public void mouseEntered(final MouseEvent e) {}
        @Override
        public void mouseExited(final MouseEvent e) {}
        @Override
        public void mouseClicked(final MouseEvent e) {}
    }

    private void setUpPanel() {
        grid = new Node[ROWS][COLS];
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLS; x++) {
                grid[y][x] = new Node(x, y, CELL_W, CELL_H, ROWS, COLS);
            }
        }
        visualize = setUpButton("VISUALIZE", Frame.WIDTH / 2 - 80, Frame.HEIGHT - Frame.BUFFER_HEIGHT + 20, 160, 60, 30, new Color(7,
                165, 165), new VisualizeListener());
        add(visualize);
        clear = setUpButton("CLEAR", Frame.WIDTH - 110, Frame.HEIGHT - Frame.BUFFER_HEIGHT + 20, 100, 60, 30, new Color(236, 55, 60),
                new ClearListener());
        add(clear);
        reset = setUpButton("RESET", 10, Frame.HEIGHT - Frame.BUFFER_HEIGHT + 20, 100, 60, 30, new Color(31, 78, 199), new ResetListener());
        add(reset);
        diagonal.setBounds(150, Frame.HEIGHT - 75, 100, 20);
        add(diagonal);
        delay.setBounds(150, Frame.HEIGHT - Frame.BUFFER_HEIGHT + 5, 100, 20);
        add(delay);
        showCosts.setBounds(150, Frame.HEIGHT - 55, 100, 20);
        add(showCosts);
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        for (int y = 0; y < ROWS + 1; y++) {
            g.drawLine(0, y * CELL_H, Frame.WIDTH, y * CELL_H);
        }
        for (int x = 0; x < COLS + 1; x++) {
            g.drawLine(x * CELL_W, 0, x * CELL_W, Frame.HEIGHT - Frame.BUFFER_HEIGHT);
        }
    }

    private static JButton setUpButton(String words, int x, int y, int w, int h, int fontSize, Color color, ActionListener actionListener) {
        JButton button = new JButton(words);
        button.setBounds(x, y, w, h);
        button.setFont(new Font("uroob", Font.PLAIN, fontSize));
        button.setBackground(color);
        button.addActionListener(actionListener);
        button.revalidate();
        return button;
    }
    private enum Algorithms {
        A_STAR, DIJKSTRA, BFS
    }
}
