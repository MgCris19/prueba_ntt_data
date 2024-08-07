package com.codingtest.client.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Logging {
    static final Logger logger = LogManager.getLogger(Logging.class);
    private static final Logging INSTANCIA = new Logging();

    private Logging(){
        //BasicConfigurator.configure();
    }

    public static void info(String parameter){
        logger.info(parameter);
    }

    public void debug(String parameter){
        logger.debug("This is debug : " + parameter);
    }

    public void warn(String parameter){
        logger.warn("This is warn : " + parameter);
    }

    public static void error(String parameter){
        logger.error(parameter);
    }

    public void fatal(String parameter){
        logger.fatal("This is fatal : " + parameter);
    }

    public static Logging getLog(){
        return INSTANCIA;
    }
    public static void error(String string, Exception e) {
        error(string + e);
    }
}
