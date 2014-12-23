/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utstatus;

import com.utstatus.model.Configuration;
import com.utstatus.model.Player;
import com.utstatus.model.UtServer;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dcnorris
 */
public class UtServerTest {
    
    private static final Logger logger = LoggerFactory.getLogger(UtServerTest.class);
    
    @Test
    public void defaultServerTest() {
        Configuration config = new Configuration();
        UtServer defaultServer = new UtServer(config.getIp(), config.getPort());
        defaultServer.refreshServer();
        Assert.assertNotNull(defaultServer.getName());
        Assert.assertNotNull(defaultServer.getCapacityInfo());
    }
}
