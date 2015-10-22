package com.company;

import com.sun.xml.internal.bind.v2.runtime.Coordinator;

/**
 * Created by Joey on 22/10/2015.
 */
public class Coordinate {

    private int row;
    private int col;
    private boolean visited;
    private String path;
    private String lastStep;

    public Coordinate(int row, int col, String path) {
        this.row = row;
        this.col = col;
        this.path = path;
        this.lastStep = "";
        this.visited = false;

    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public String getPath() {
        return path;
    }

    public void addToPath(String newNode) {
        if (!path.equals("")) {
            path += " ";
        }
        path += newNode;
        lastStep = newNode;
    }

    public String getLastStep() {
        return lastStep;
    }

    public boolean equals(Coordinate c2) {
        return (this.row == c2.row && this.col == c2.col);
    }

    public String toString() {
        return row + ":" + col + " " + path;
    }
}
