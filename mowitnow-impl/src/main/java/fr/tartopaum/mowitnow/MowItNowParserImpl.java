package fr.tartopaum.mowitnow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.tartopaum.mowitnow.exception.HandlerException;
import fr.tartopaum.mowitnow.exception.ParseException;
import fr.tartopaum.mowitnow.model.Coordinates;
import fr.tartopaum.mowitnow.model.Grid;
import fr.tartopaum.mowitnow.model.Order;
import fr.tartopaum.mowitnow.model.Orientation;
import fr.tartopaum.mowitnow.model.Mower;

public class MowItNowParserImpl implements MowItNowParser {

    private final Pattern gridPattern = Pattern.compile("^(\\d+)\\s(\\d+)$");
    private final int gridPatternGroupWidth = 1;
    private final int gridPatternGroupHeight = 1;

    private final Pattern mowerPattern = Pattern.compile("^(\\d+)\\s(\\d+)\\s([NEWS])$");
    private final int mowerPatternGroupX = 1;
    private final int mowerPatternGroupY = 2;
    private final int mowerPatternGroupOrientation = 3;

    @Override
    public void parse(Reader reader, MowItNowHandler handler) throws ParseException, HandlerException {
        BufferedReader br = new BufferedReader(reader);

        parseGrid(br, handler);
        parseMowers(br, handler);

        handler.end();
    }

    /**
     * Première partie du parsing : parsing de la grille.
     * @param br Reader.
     * @param handler Handler.
     * @throws ParseException
     * @throws HandlerException
     */
    protected void parseGrid(BufferedReader br, MowItNowHandler handler) throws ParseException, HandlerException {
        try {
            String line = br.readLine();
            if (line == null) {
                throw new ParseException("La ligne de la tondeuse est vide");
            }

            Matcher matcher = getGridPattern().matcher(line);
            if (!matcher.matches()) {
                throw new ParseException("La ligne de la tondeuse n'est pas de la forme [NOMBRE] [NOMBRE]");
            }

            handler.begin(new Grid(
                    Integer.valueOf(matcher.group(getGridPatternGroupWidth())) + 1,
                    Integer.valueOf(matcher.group(getGridPatternGroupHeight())) + 1));

        } catch (IOException e) {
            throw new ParseException(e);
        }
    }

    /**
     * Seconde partie du parsing : parsing des tondeuses.
     * @param br Reader.
     * @param handler Handler.
     * @throws ParseException
     * @throws HandlerException
     */
    protected void parseMowers(BufferedReader br, MowItNowHandler handler) throws ParseException, HandlerException {
        try {
            String mowerLine;
            String orderLine;
            Matcher matcher = getMowerPattern().matcher("");
            while ((mowerLine = br.readLine()) != null) {
                orderLine = br.readLine();
                if (orderLine == null) {
                    // on accepte que la dernière ligne soit vide
                    if (mowerLine.isEmpty()) {
                        break;
                    } else {
                        throw new ParseException("Fin du fichier atteinte alors qu'on attendait des ordres");
                    }
                }

                matcher.reset(mowerLine);
                if (!matcher.matches()) {
                    throw new ParseException("La ligne de tondeuse n'est pas de la forme [NOMBRE] [NOMBRE] [N/E/W/S]");
                }

                handler.beginMower(new Mower(
                        new Coordinates(
                                Integer.valueOf(matcher.group(getMowerPatternGroupX())),
                                Integer.valueOf(matcher.group(getMowerPatternGroupY()))),
                        getOrientation(matcher.group(getMowerPatternGroupOrientation()))));

                parseOrders(orderLine, handler);

                handler.endMower();
            }
        } catch (IOException e) {
            throw new ParseException(e);
        }
    }


    /**
     * Parsing de la ligne contenant les ordres.
     * @param orderLine Ligne contenant les ordres.
     * @param handler Handler.
     * @throws ParseException
     * @throws HandlerException
     */
    private void parseOrders(String orderLine, MowItNowHandler handler) throws ParseException, HandlerException {
        char[] chars = orderLine.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char character = chars[i];
            Order order = getOrder(String.valueOf(character));

            handler.order(order);
        }
    }

    /**
     * Convertions String => Order.
     * @param orderString Représentation de l'ordre.
     * @return Order.
     * @throws ParseException
     */
    protected Order getOrder(String orderString) throws ParseException {
        switch (orderString) {
        case "A":
            return Order.GO_FORWARD;
        case "D":
            return Order.TURN_RIGHT;
        case "G":
            return Order.TURN_LEFT;
        default:
            throw new ParseException("Ordre non gérée : " + orderString);
        }
    }

    /**
     * Convertions String => Orientation.
     * @param orientationString Représentation de l'orientation.
     * @return Orientation.
     * @throws ParseException
     */
    protected Orientation getOrientation(String orientationString) throws ParseException {
        switch (orientationString) {
        case "N":
            return Orientation.NORTH;
        case "E":
            return Orientation.EAST;
        case "W":
            return Orientation.WEST;
        case "S":
            return Orientation.SOUTH;
        default:
            throw new ParseException("Orientation non gérée : " + orientationString);
        }
    }

    /** Retourne le pattern attendu pour la ligne de la grille. */
    protected Pattern getGridPattern() {
        return gridPattern;
    }

    /** Pour le pattern de la grille : index du groupe contenant la largeur. */
    protected int getGridPatternGroupWidth() {
        return gridPatternGroupWidth;
    }

    /** Pour le pattern de la grille : index du groupe contenant la hauteur. */
    protected int getGridPatternGroupHeight() {
        return gridPatternGroupHeight;
    }

    /** Retourne le pattern attendu pour la ligne de situation d'une tondeuse. */
    protected Pattern getMowerPattern() {
        return mowerPattern;
    }

    /** Pour le pattern de la situation : index du groupe contenant la position X. */
    protected int getMowerPatternGroupX() {
        return mowerPatternGroupX;
    }

    /** Pour le pattern de la situation : index du groupe contenant la position Y. */
    protected int getMowerPatternGroupY() {
        return mowerPatternGroupY;
    }

    /** Pour le pattern de la situation : index du groupe contenant l'orientation. */
    protected int getMowerPatternGroupOrientation() {
        return mowerPatternGroupOrientation;
    }

}
