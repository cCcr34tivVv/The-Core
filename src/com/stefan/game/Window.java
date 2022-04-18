package com.stefan.game;

import javax.swing.JFrame;

public class Window extends JFrame{

    public Window(){
        // titlul ferestrei
        setTitle("The Core");
        // asigura inchiderea corecta a programului
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(GamePanel.getInstance(1280 , 720));
        setIgnoreRepaint(true);
        // dimensioneaza fereastra si componentele
        pack();
        // nu mai putem redimensiona fereastra
        setResizable(false);
        // cand fereastra se deschide va aparea in centrul ecranului
        setLocationRelativeTo(null);
        // permite vizualizarea ferestrei
        setVisible(true);
    }

}
