package fr.tartopaum.mowitnow.model;

import java.io.Serializable;

/**
 * Situation d'une tondeuse mowitnow.
 * Il s'agit de la combinaison de coordonnées et d'une orientation.
 * @author Tartopaum
 */
public final class Mower implements Serializable {

    private static final long serialVersionUID = -5399998164501991689L;

    private final Coordinates coordinates;
	private final Orientation orientation;

	/**
	 * Situation d'une tondeuse mowitnow.
	 * Il s'agit de la combinaison de coordonnées et d'une orientation.
	 * @param coordinates Coordonnées.
	 * @param orientation Orientation.
	 */
	public Mower(Coordinates coordinates, Orientation orientation) {
		super();

		if (coordinates == null) {
            throw new IllegalArgumentException("Coordonnées nulles non autorisées");
        }

		if (orientation == null) {
            throw new IllegalArgumentException("Orientation nulle non autorisée");
        }

		this.coordinates = coordinates;
		this.orientation = orientation;
	}

	public Mower() {
		this(new Coordinates(), Orientation.NORTH);
	}

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public Mower withCoordinates(Coordinates coordinates) {
        return new Mower(coordinates, orientation);
    }

    public Mower withOrientation(Orientation orientation) {
        return new Mower(coordinates, orientation);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((coordinates == null) ? 0 : coordinates.hashCode());
        result = prime * result + ((orientation == null) ? 0 : orientation.hashCode());
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
        Mower other = (Mower) obj;
        if (coordinates == null) {
            if (other.coordinates != null) {
                return false;
            }
        } else if (!coordinates.equals(other.coordinates)) {
            return false;
        }
        if (orientation != other.orientation) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "(" + coordinates + ", " + orientation + ")";
    }

}
