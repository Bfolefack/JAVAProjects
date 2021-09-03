package com.example.Frames;

import java.awt.event.MouseEvent;
import java.lang.reflect.Constructor;

import javax.swing.*;
import javax.swing.event.MouseInputListener;

public class SelfDestructFrame extends JFrame implements MouseInputListener{
    SelfDestructFrame(){
        super();
        JButton button = new JButton("Kill Me");
        add(button);
    }

    SelfDestructFrame(String s){
        super(s);
        JButton button = new JButton("Kill Me");
        this.add(button);
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        this.setSize(WIDTH/2, HEIGHT/2);        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    
}
