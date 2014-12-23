package com.utstatus;

import com.utstatus.gui.UrtApp;
import com.utstatus.model.Configuration;
import static com.utstatus.persistence.ConfigurationParser.fromJson;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import static java.lang.System.getProperty;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;

/**
 *
 * @author dcnorris
 */
public class Driver {

    private static final Logger logger = getLogger(Driver.class);
    public static final String CONFIGURATION_FILE_NAME = "utstatus.json";

    public static void main(String[] args) throws Exception {

        Configuration configuration;
        //Check for persisted configuration
        try (Reader reader = new FileReader(getProperty("user.home") + "/" + CONFIGURATION_FILE_NAME)) {
            configuration = fromJson(reader);
        } catch (IOException ex) {
            //don't throw exception just start with default configuration
            configuration = new Configuration();
        }
        logger.info("Starting UT Status Check");
        UrtApp app = new UrtApp(configuration);
    }
}
