package com.utstatus;

import com.utstatus.gui.UrtApp;
import com.utstatus.model.Configuration;
import com.utstatus.persistence.ConfigurationParser;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dcnorris
 */
public class Driver {

    private static final Logger logger = LoggerFactory.getLogger(Driver.class);
    public static final String CONFIGURATION_FILE_NAME = "utstatus.json";

    public static void main(String[] args) throws Exception {

        Configuration configuration;
        //Check for persisted configuration
        try (Reader reader = new FileReader(System.getProperty("user.home") + "/" + CONFIGURATION_FILE_NAME)) {
            configuration = ConfigurationParser.fromJson(reader);
        } catch (IOException ex) {
            //don't throw exception just start with default configuration
            configuration = new Configuration();
        }
        logger.info("Starting UT Status Check");
        UrtApp app = new UrtApp(configuration);
    }
}
