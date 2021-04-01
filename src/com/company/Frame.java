package com.company;

import javax.swing.JFrame;

public class Frame {

    private JFrame frame;
    public static int WIDTH = 800, HEIGHT = 900, BUFFER_HEIGHT = 100;

    public void go() {
        this.frame = new JFrame("Pathfinding Visualization");

        this.frame.getContentPane().add(new Grid());
        this.frame.setSize(WIDTH, HEIGHT);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setResizable(true);
        this.frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Frame().go();
    }
}
