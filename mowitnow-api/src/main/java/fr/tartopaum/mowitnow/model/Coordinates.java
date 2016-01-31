package fr.tartopaum.mowitnow.model;

import java.io.Serializable;

/**
 * Coordonnées : point 2D {@code (x, y)}.
 * @author Tartopaum
 */
public final class Coordinates implements Serializable {

    private static final long serialVersionUID = -4047666685223828458L;

    private final int x;
    private final int y;

    public Coordinates(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }

    public Coordinates() {
        this(0, 0);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coordinates withX(int x) {
        return new Coordinates(x, y);
    }

    public Coordinates withY(int y) {
        return new Coordinates(x, y);
    }

    public Coordinates moveX(int dx) {
        return withX(x + dx);
    }

    public Coordinates moveY(int dy) {
        return withY(y + dy);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Coordinates other = (Coordinates) obj;
        if (x != other.x) {
            return false;
        }
        if (y != other.y) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

}
