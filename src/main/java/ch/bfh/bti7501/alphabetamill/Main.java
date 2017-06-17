package main.java.ch.bfh.bti7501.alphabetamill;

import main.java.ch.bfh.bti7501.alphabetamill.views.BoardGUI;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by Florian on 14.06.2017.
 */
public class Main {

    public static void main(String[] args) {
        JFrame game = new BoardGUI();
        game.setSize(600, 860);
        game.setResizable(false);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setVisible(true);
    }

}