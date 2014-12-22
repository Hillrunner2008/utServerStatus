/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utstatus;

import com.utstatus.model.Configuration;
import com.utstatus.model.UtServer;
import org.junit.Test;

/**
 *
 * @author dcnorris
 */
public class UtServerTest {

    @Test
    public void defaultServerTest() {
        Configuration config = new Configuration();
        UtServer defaultServer = new UtServer(config.getIp(), config.getPort());
        
    }
}
