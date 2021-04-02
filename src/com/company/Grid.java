package com.company;

import com.company.algorithms.Astar;
import com.company.algorithms.BreadthFirstSearch;
import com.company.algorithms.Dijkstra;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Grid extends JPanel implements ActionListener {

    // constants for size and grid
    private final int CELL_W = 20;
    private final int CELL_H = 20;
    private final int COLS = Frame.WIDTH / CELL_W;
    private final int ROWS = (Frame.HEIGHT - Frame.BUFFER_HEIGHT) / CELL_H;

    private Node[][] grid;

    private final int maxDelay = 150; // about 6 FPS
    private final int minDelay = 1; // 1000 FPS

    private final JCheckBox diagonal = new JCheckBox("diagonal", false);
    private final JCheckBox showCosts = new JCheckBox("show values", false);
    private final JSlider delay = new JSlider(minDelay, maxDelay);

    // initialized as null
    private Node start;
    private Node end;

    private JButton visualize;
    private JButton clear;
    private JButton randomize;

    private final JComboBox algoMenu = new JComboBox(new String[]{Algorithms.A_STAR.algoInWords, Algorithms.DIJKSTRA.algoInWords,
            Algorithms.BFS.algoInWords});

    private final Timer timer;

    private Algorithms algorithm = Algorithms.A_STAR;
    private final Astar aStar = new Astar();
    private final Dijkstra dijkstra = new Dijkstra();
    private final BreadthFirstSearch bfs = new BreadthFirstSearch();

    public Grid() {
        setLayout(null);
        setFocusable(true);
        setUpPanel();
        addMouseListener(new NewMouseListener());
        addMouseMotionListener(new NewMouseListener());
        timer = new Timer(maxDelay - delay.getValue(), this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, Frame.WIDTH, Frame.HEIGHT); // draws the background
        // draws each node
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
        switch (algorithm) {
            case A_STAR:
                if (!diagonal.isSelected() && aStar.isDoingAlgo()) {
                    aStar.noDiagonal(start, end);
                } else if (diagonal.isSelected() && aStar.isDoingAlgo()) {
                    aStar.diagonal(start, end);
                }
                break;
            case DIJKSTRA:
                if (!diagonal.isSelected() && dijkstra.isDoingAlgo()) {
                    dijkstra.noDiagonal(start, end);
                } else if (diagonal.isSelected() && dijkstra.isDoingAlgo()) {
                    dijkstra.diagonal(start, end);
                }
                break;
            case BFS:
                if (bfs.isDoingAlgo()) {
                    bfs.noDiagonal(start, end);
                }
                break;
        }
        timer.setDelay(maxDelay - delay.getValue()); // updates the FPS
        repaint(); // redraw the window
    }

    private class VisualizeListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent e) {
            // check if there is a start and end
            if ((start != null) && (end != null)) {
                for (int y = 0; y < ROWS; y++) {
                    for (int x = 0; x < COLS; x++) {
                        // initialize neighbors
                        if (!algorithm.equals(Algorithms.BFS)) {
                            grid[y][x].setNeighbors(grid, diagonal.isSelected());
                        } else {
                            grid[y][x].setNeighbors(grid, false);
                        }
                    }
                }
                // initialize any queues, lists, or values needed
                switch (algorithm) {
                    case A_STAR:
                        aStar.setUpGraph(start);
                        break;
                    case DIJKSTRA:
                        dijkstra.setUpGraph(start);
                        break;
                    case BFS:
                        bfs.setUpGraph(start);
                        break;
                }
            }
        }
    }

    private class ClearListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent e) {
            clearGrid();
        }
    }

    private class AlgoChooserListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent e) {
            // set the current algorithm
            String algo = (String) algoMenu.getSelectedItem();
            if (algo.equals(Algorithms.A_STAR.algoInWords)) {
                algorithm = Algorithms.A_STAR;
            } else if (algo.equals(Algorithms.DIJKSTRA.algoInWords)) {
                algorithm = Algorithms.DIJKSTRA;
            } else if (algo.equals(Algorithms.BFS.algoInWords)) {
                algorithm = Algorithms.BFS;
            }
        }
    }

    private class RandomMazeListener implements ActionListener {
        private final Random rand = new Random();
        private final int minNodes = ROWS * COLS / 8;
        @Override
        public void actionPerformed(final ActionEvent e) {
            int newNodes = minNodes + rand.nextInt(ROWS * COLS - minNodes * 6);
            clearGrid();
            int count = 0;
            // create a new maze
            while (count < newNodes) {
                int x = rand.nextInt(COLS);
                int y = rand.nextInt(ROWS);
                if (!grid[y][x].isWall()) {
                    grid[y][x].makeWall();
                    count++;
                }
            }
            int x = rand.nextInt(COLS);
            int y = rand.nextInt(ROWS);
            grid[y][x].makeStart();
            start = grid[y][x];
            // look for end that is not the start or a wall
            while (true) {
                x = rand.nextInt(COLS);
                y = rand.nextInt(ROWS);
                if (!grid[y][x].isStart() && !grid[y][x].isWall()) {
                    grid[y][x].makeEnd();
                    end = grid[y][x];
                    break;
                }
            }
        }
    }

    private void clearGrid() {
        // reset the entire grid and all algorithms
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLS; x++) {
                grid[y][x].reset();
            }
        }
        start = null;
        end = null;
        aStar.reset();
        dijkstra.reset();
        bfs.reset();
    }

    private class NewMouseListener implements MouseListener, MouseMotionListener {
        @Override
        public void mousePressed(final MouseEvent e) {
            createMap(e);
        }
        private void createMap(final MouseEvent e) {
            // the y coordinate might be out of range
            int gridX = e.getX() / CELL_W;
            int gridY = e.getY() / CELL_H;
            if (gridX < COLS && gridY < ROWS && gridX >= 0 && gridY >= 0) {
                Node n = grid[gridY][gridX];
                // add a colored node to the board
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (end == null && start != null && !start.equals(n)) {
                        // if end is not placed and start is placed
                        n.makeEnd();
                        end = n;
                    } else if (start == null && !n.equals(end)) {
                        // if start is not placed and selected does not equals end
                        n.makeStart();
                        start = n;
                    } else {
                        if (!n.equals(start) && !n.equals(end)) {
                            // otherwise, it has to equal a wall
                            n.makeWall();
                        }
                    }
                }
                // remove a colored node; clear the node
                else if (SwingUtilities.isRightMouseButton(e)) {
                    // if it is the start or end, update the start nodes
                    if (n.equals(start)) {
                        start = null;
                    } else if (n.equals(end)) {
                        end = null;
                    }
                    // regardless, reset the node to white
                    n.reset();
                }
            }
        }
        @Override
        public void mouseReleased(final MouseEvent e) {}
        @Override
        public void mouseEntered(final MouseEvent e) {}
        @Override
        public void mouseExited(final MouseEvent e) {}
        @Override
        public void mouseClicked(final MouseEvent e) {
            createMap(e);
        }
        @Override
        public void mouseDragged(final MouseEvent e) {
            createMap(e);
        }
        @Override
        public void mouseMoved(final MouseEvent e) {}
    }

    private void setUpPanel() {
        // create the graph full of nodes
        grid = new Node[ROWS][COLS];
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLS; x++) {
                grid[y][x] = new Node(x, y, CELL_W, CELL_H, ROWS, COLS);
            }
        }
        int buttonY = Frame.HEIGHT - Frame.BUFFER_HEIGHT + 10;
        // initialize JComponents
        clear = setUpButton("CLEAR", Frame.WIDTH - 110, buttonY, 100, 60, 30, new Color(236, 55, 60), new ClearListener());
        add(clear);
        visualize = setUpButton("VISUALIZE", Frame.WIDTH / 2 - 80, buttonY, 160, 60, 30, new Color(7, 165, 165), new VisualizeListener());
        add(visualize);

        randomize = setUpButton("NEW MAZE", 10, buttonY, 160, 60, 30, new Color(84, 126, 220), new RandomMazeListener());
        add(randomize);
        // JCheckBox
        diagonal.setBounds(280, Frame.HEIGHT - 75, 100, 20);
        add(diagonal);
        // JSlider that sets the speed
        delay.setBounds(280, Frame.HEIGHT - Frame.BUFFER_HEIGHT + 5, 100, 20);
        add(delay);
        // option to view the numbers
        showCosts.setBounds(280, Frame.HEIGHT - 55, 100, 20);
        add(showCosts);
        // menu to select algorithm
        algoMenu.setBounds(visualize.getX() + visualize.getWidth() + 100, Frame.HEIGHT - Frame.BUFFER_HEIGHT + 5, 170, 25);
        algoMenu.addActionListener(new AlgoChooserListener());
        add(algoMenu);
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        // draw horizontal lines
        for (int y = 0; y < ROWS + 1; y++) {
            g.drawLine(0, y * CELL_H, Frame.WIDTH, y * CELL_H);
        }
        // draw vertical lines
        for (int x = 0; x < COLS + 1; x++) {
            g.drawLine(x * CELL_W, 0, x * CELL_W, Frame.HEIGHT - Frame.BUFFER_HEIGHT);
        }
    }

    private static JButton setUpButton(String words, int x, int y, int w, int h, int fontSize, Color color, ActionListener actionListener) {
        // utility function that sets up the button
        JButton button = new JButton(words);
        button.setBounds(x, y, w, h);
        button.setFont(new Font("uroob", Font.PLAIN, fontSize));
        button.setBackground(color);
        button.addActionListener(actionListener);
        button.revalidate();
        return button;
    }
    private enum Algorithms {
        A_STAR("A* Search"), DIJKSTRA("Dijkstra Search"), BFS("Breadth First Search"); // choices of algorithms
        private final String algoInWords;
        Algorithms(String algoInWords) {
            this.algoInWords = algoInWords;
        }

        @Override
        public String toString() {
            return algoInWords;
        }
    }
}
