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
import fr.tartopaum.mowitnow.model.Situation;

public class MowItNowParserImpl implements MowItNowParser {

    private final Pattern GRID_PATTERN = Pattern.compile("^(\\d+)\\s(\\d+)$");
    private final int GRID_PATTERN_GROUP_WIDTH = 1;
    private final int GRID_PATTERN_GROUP_HEIGHT = 1;

    private final Pattern SITUATION_PATTERN = Pattern.compile("^(\\d+)\\s(\\d+)\\s([NEWS])$");
    private final int SITUATION_PATTERN_GROUP_X = 1;
    private final int SITUATION_PATTERN_GROUP_Y = 2;
    private final int SITUATION_PATTERN_GROUP_ORIENTATION = 3;

    @Override
    public void parse(Reader reader, MowItNowHandler handler) throws ParseException, HandlerException {
        BufferedReader br = new BufferedReader(reader);

        parseGrid(br, handler);
        parseMowers(br, handler);

        handler.end();
    }

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

    protected void parseMowers(BufferedReader br, MowItNowHandler handler) throws ParseException, HandlerException {
        try {
            String situationLine;
            String orderLine;
            Matcher matcher = getSituationPattern().matcher("");
            while ((situationLine = br.readLine()) != null) {
                orderLine = br.readLine();
                if (orderLine == null) {
                    // on accepte que la dernière ligne soit vide
                    if (situationLine.isEmpty()) {
                        break;
                    } else {
                        throw new ParseException("Fin du fichier atteinte alors qu'on attendait des ordres");
                    }
                }

                matcher.reset(situationLine);
                if (!matcher.matches()) {
                    throw new ParseException("La ligne de situation n'est pas de la forme [NOMBRE] [NOMBRE] [N/E/W/S]");
                }

                handler.beginMower(new Situation(
                        new Coordinates(
                                Integer.valueOf(matcher.group(getSituationPatternGroupX())),
                                Integer.valueOf(matcher.group(getSituationPatternGroupY()))),
                        getOrientation(matcher.group(getSituationPatternGroupOrientation()))));

                parseOrders(orderLine, handler);

                handler.endMower();
            }
        } catch (IOException e) {
            throw new ParseException(e);
        }
    }

    private void parseOrders(String orderLine, MowItNowHandler handler) throws ParseException, HandlerException {
        char[] chars = orderLine.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char character = chars[i];
            Order order = getOrder(String.valueOf(character));

            handler.order(order);
        }
    }

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

    protected Pattern getGridPattern() {
        return GRID_PATTERN;
    }

    protected int getGridPatternGroupWidth() {
        return GRID_PATTERN_GROUP_WIDTH;
    }

    protected int getGridPatternGroupHeight() {
        return GRID_PATTERN_GROUP_HEIGHT;
    }

    protected Pattern getSituationPattern() {
        return SITUATION_PATTERN;
    }

    protected int getSituationPatternGroupX() {
        return SITUATION_PATTERN_GROUP_X;
    }

    protected int getSituationPatternGroupY() {
        return SITUATION_PATTERN_GROUP_Y;
    }

    protected int getSituationPatternGroupOrientation() {
        return SITUATION_PATTERN_GROUP_ORIENTATION;
    }

}
