/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author aamandajuhl
 */
public class DataFacadeTest {
    
    public DataFacadeTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getDataFacade method, of class DataFacade.
     */
    @Test
    public void testGetDataFacade() {
        System.out.println("getDataFacade");
        DataFacade expResult = null;
        DataFacade result = DataFacade.getDataFacade();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getData method, of class DataFacade.
     */
    @Test
    public void testGetData() throws Exception {
        System.out.println("getData");
        String endpoint = "";
        DataFacade instance = new DataFacade();
        String expResult = "";
        String result = instance.getData(endpoint);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
