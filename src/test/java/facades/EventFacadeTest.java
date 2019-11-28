/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import DTO.EventDTO;
import errorhandling.NotFoundException;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;

/**
 *
 * @author aamandajuhl
 */
public class EventFacadeTest {
    
    private static EventFacade facade;
    
    public EventFacadeTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
        facade = EventFacade.getEventFacade();
    }

    /**
     * Test of getEventFacade method, of class EventFacade.
     */
    @Test
    public void testGetEventFacade() {
        EventFacade expResult = facade;
        EventFacade result = EventFacade.getEventFacade();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEvents method, of class EventFacade.
     * @throws errorhandling.NotFoundException
     */
    @Test
    public void testGetEvents() throws NotFoundException {
       
        String expResult = "Carrer de Pamplona, 88";
        List<EventDTO> result = facade.getEvents("2019-12-27", "2019-12-28", "Spain", "Barcelona");

        assertEquals(expResult, result.get(0).getEventAddress());
        assertEquals(1, result.size());
    }
    
     /**
     * Test of getNegativeEvents method, of class EventFacade.
     * @throws errorhandling.NotFoundException
     */
    @Test
    public void testNegativeGetEvents() throws NotFoundException {
       
        try {
            facade.getEvents("2019-11-27", "2019-11-28", "France", "Paris");
            fail();
        } catch (NotFoundException ex) {
            assertEquals("There are no events in that city for the given date", ex.getMessage());
        }
    }
    
}
