package fr.tartopaum.mowitnow.model;

public class Grid {

    private final int width;
    private final int height;

    public Grid(int width, int height) {
        super();

        if (width < 1) {
            throw new IllegalArgumentException("La largeur doit être strictement positive");
        }

        if (height < 1) {
            throw new IllegalArgumentException("La hauteur doit être strictement positive");
        }

        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean contains(Coordinates coordinates) {
        int x = coordinates.getX();
        int y = coordinates.getY();

        return x >= 0
            && x < width
            && y >= 0
            && y < height;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + height;
        result = prime * result + width;
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
        Grid other = (Grid) obj;
        if (height != other.height) {
            return false;
        }
        if (width != other.width) {
            return false;
        }
        return true;
    }

}
