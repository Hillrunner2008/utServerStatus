/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utstatus;

import com.utstatus.server.QueryUtility;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dcnorris
 */
public class QueryMasterTest {

    private static final Logger logger = LoggerFactory.getLogger(QueryMasterTest.class);

    @Test
    public void queryMaster() throws Exception {
        QueryUtility queryUtility = new QueryUtility(null);
        logger.info(queryUtility.getMasterList());

    }
}
