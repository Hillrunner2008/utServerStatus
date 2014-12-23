/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utstatus;

import com.utstatus.model.Configuration;
import com.utstatus.model.UtServer;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;

/**
 *
 * @author dcnorris
 */
public class UtServerTest {
    
    private static final Logger logger = getLogger(UtServerTest.class);
    
    @Test
    public void defaultServerTest() {
        Configuration config = new Configuration();
        UtServer defaultServer = new UtServer(config.getIp(), config.getPort());
        defaultServer.refreshServer();
        assertNotNull(defaultServer.getName());
        assertNotNull(defaultServer.getCapacityInfo());
    }
}
