package fr.tartopaum.mowitnow;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import fr.tartopaum.mowitnow.exception.HandlerException;
import fr.tartopaum.mowitnow.exception.ParseException;
import fr.tartopaum.mowitnow.model.Coordinates;
import fr.tartopaum.mowitnow.model.Grid;
import fr.tartopaum.mowitnow.model.Order;
import fr.tartopaum.mowitnow.model.Orientation;
import fr.tartopaum.mowitnow.model.Mower;

public class MowItNowParserImplTest {

    /** Context JMock. */
    private Mockery context = new Mockery();

    /** Le parseur à tester. */
    private MowItNowParserImpl parser;

    /** Dépendance : handler. */
    private MowItNowHandler handler;

    @Before
    public void init() {
        parser = new MowItNowParserImpl();
        handler = context.mock(MowItNowHandler.class);
    }

    @Test
    public void shouldCallHandler() throws Exception {
        String input = "5 5\n"
                + "1 2 N\n"
                + "GA\n"
                + "3 3 E\n"
                + "AD";

        final Grid grid = new Grid(6, 6);
        final Mower mower1 = new Mower(
                new Coordinates(1, 2),
                Orientation.NORTH);

        final Mower mower2 = new Mower(
                new Coordinates(3, 3),
                Orientation.EAST);

    // attentes
        context.checking(new Expectations() {
            {
                oneOf(handler).begin(grid);

                oneOf(handler).beginMower(mower1);
                oneOf(handler).order(Order.TURN_LEFT);
                oneOf(handler).order(Order.GO_FORWARD);
                oneOf(handler).endMower();

                oneOf(handler).beginMower(mower2);
                oneOf(handler).order(Order.GO_FORWARD);
                oneOf(handler).order(Order.TURN_RIGHT);
                oneOf(handler).endMower();

                oneOf(handler).end();
            }
        });

    // exécution
        try (Reader reader = new InputStreamReader(new ByteArrayInputStream(input.getBytes("UTF-8")), "UTF-8")) {
            parser.parse(reader, handler);
        }

    // vérifications
        // -- assurées par les attentes

    }

    @Test
    public void shouldAcceptEmptyTrailingLine() throws Exception {
        String input = "5 5\n"
                + "1 2 N\n"
                + "GA\n"
                + "3 3 E\n"
                + "AD\n";

        final Grid grid = new Grid(6, 6);
        final Mower mower1 = new Mower(
                new Coordinates(1, 2),
                Orientation.NORTH);

        final Mower mower2 = new Mower(
                new Coordinates(3, 3),
                Orientation.EAST);

    // attentes
        context.checking(new Expectations() {
            {
                oneOf(handler).begin(grid);

                oneOf(handler).beginMower(mower1);
                oneOf(handler).order(Order.TURN_LEFT);
                oneOf(handler).order(Order.GO_FORWARD);
                oneOf(handler).endMower();

                oneOf(handler).beginMower(mower2);
                oneOf(handler).order(Order.GO_FORWARD);
                oneOf(handler).order(Order.TURN_RIGHT);
                oneOf(handler).endMower();

                oneOf(handler).end();
            }
        });

    // exécution
        try (Reader reader = new InputStreamReader(new ByteArrayInputStream(input.getBytes("UTF-8")))) {
            parser.parse(reader, handler);
        }

    // vérifications
        // -- assurées par les attentes

    }

    @Test(expected = ParseException.class)
    public void shouldFailOnMissingOrderLine() throws Exception {
        String input = "5 5\n"
                + "1 2 N\n"
                + "GA\n"
                + "3 3 E";

        final Grid grid = new Grid(6, 6);
        final Mower mower1 = new Mower(
                new Coordinates(1, 2),
                Orientation.NORTH);

        final Mower mower2 = new Mower(
                new Coordinates(3, 3),
                Orientation.EAST);

    // attentes
        context.checking(new Expectations() {
            {
                oneOf(handler).begin(grid);

                oneOf(handler).beginMower(mower1);
                oneOf(handler).order(Order.TURN_LEFT);
                oneOf(handler).order(Order.GO_FORWARD);
                oneOf(handler).endMower();

                oneOf(handler).beginMower(mower2);
                never(handler).order(Order.TURN_RIGHT);

                never(handler).end();
            }
        });

    // exécution
        try (Reader reader = new InputStreamReader(new ByteArrayInputStream(input.getBytes("UTF-8")))) {
            parser.parse(reader, handler);
        }

    // vérifications
        // -- assurées par les attentes

    }

    @Test(expected = ParseException.class)
    public void shouldFailOnBadGridDeclaration() throws Exception {
        String input = "test";

    // exécution
        try (Reader reader = new InputStreamReader(new ByteArrayInputStream(input.getBytes("UTF-8")))) {
            parser.parse(reader, handler);
        }

    // vérifications
        // -- assurées par les attentes

    }

    @Test(expected = ParseException.class)
    public void shouldFailOnBadMowerDeclaration() throws Exception {
        String input = "5 5\n"
                + "1 2 A\n"
                + "GA";

        final Grid grid = new Grid(6, 6);

    // attentes
        context.checking(new Expectations() {
            {
                oneOf(handler).begin(grid);
            }
        });

    // exécution
        try (Reader reader = new InputStreamReader(new ByteArrayInputStream(input.getBytes("UTF-8")))) {
            parser.parse(reader, handler);
        }

    // vérifications
        // -- assurées par les attentes

    }

    @Test(expected = ParseException.class)
    public void shouldFailOnBadOrderDeclaration() throws Exception {
        String input = "5 5\n"
                + "1 2 N\n"
                + "GAC";

        final Grid grid = new Grid(6, 6);
        final Mower mower1 = new Mower(
                new Coordinates(1, 2),
                Orientation.NORTH);

    // attentes
        context.checking(new Expectations() {
            {
                oneOf(handler).begin(grid);

                oneOf(handler).beginMower(mower1);
                oneOf(handler).order(Order.TURN_LEFT);
                oneOf(handler).order(Order.GO_FORWARD);
            }
        });

    // exécution
        try (Reader reader = new InputStreamReader(new ByteArrayInputStream(input.getBytes("UTF-8")))) {
            parser.parse(reader, handler);
        }

    // vérifications
        // -- assurées par les attentes

    }

    @Test(expected = HandlerException.class)
    public void shouldPropageHandlerException() throws Exception {
        String input = "5 5\n"
                + "1 2 N\n"
                + "GA\n"
                + "3 3 E\n"
                + "AD";

        final Grid grid = new Grid(6, 6);
        final Mower mower1 = new Mower(
                new Coordinates(1, 2),
                Orientation.NORTH);

    // attentes
        context.checking(new Expectations() {
            {
                oneOf(handler).begin(grid);

                oneOf(handler).beginMower(mower1);
                oneOf(handler).order(Order.TURN_LEFT);
                oneOf(handler).order(Order.GO_FORWARD);
                will(throwException(new HandlerException()));
            }
        });

    // exécution
        try (Reader reader = new InputStreamReader(new ByteArrayInputStream(input.getBytes("UTF-8")))) {
            parser.parse(reader, handler);
        }

    // vérifications
        // -- assurées par les attentes

    }

}
