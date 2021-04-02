package com.company;

import javax.swing.JFrame;

public class Frame {

    private JFrame frame;
    public static int WIDTH = 1200, HEIGHT = 900, BUFFER_HEIGHT = 100;

    public void go() {
        frame = new JFrame("Pathfinding Visualization");

        frame.getContentPane().add(new Grid());
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Frame().go();
    }
}
