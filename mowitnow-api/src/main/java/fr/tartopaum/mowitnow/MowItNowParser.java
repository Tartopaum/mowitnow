package fr.tartopaum.mowitnow;

import java.io.Reader;

import fr.tartopaum.mowitnow.exception.HandlerException;
import fr.tartopaum.mowitnow.exception.ParseException;

public interface MowItNowParser {

    void parse(Reader reader, MowItNowHandler handler) throws ParseException, HandlerException;

}
