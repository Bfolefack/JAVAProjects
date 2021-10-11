package com.example.Objects.Agents;

import java.util.HashSet;
import java.util.Set;

import com.example.AntWars;
import com.example.Objects.Grid.Grid;
import com.example.Objects.Nest.Nest;

public class Colony {
    Set<Ant> ants = new HashSet<Ant>();
    public Nest nest;
    public void display(AntWars aw) {
        if (nest != null) {
            nest.display(aw);
        }
    }
    public void update(AntWars antWars, Grid farm) {
    }
}
