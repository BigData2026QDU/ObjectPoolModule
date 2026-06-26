package com.servicepool;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ServicePoolManagerTest {

    @Test
    void testGetInstance() {
        ServicePoolManager manager1 = ServicePoolManager.getInstance();
        ServicePoolManager manager2 = ServicePoolManager.getInstance();
        assertNotNull(manager1);
        assertSame(manager1, manager2, "应该是同一个实例");
    }

    @Test
    void testRegisterAndBorrowService() {
        ServicePoolManager manager = ServicePoolManager.getInstance();
        
        manager.registerService(TestService.class, TestService::new, 5);
        
        assertTrue(manager.isRegistered(TestService.class));
        
        TestService service = manager.borrowService(TestService.class);
        assertNotNull(service);
        
        manager.returnService(TestService.class, service);
    }

    @Test
    void testInvalidService() {
        ServicePoolManager manager = ServicePoolManager.getInstance();
        
        assertThrows(IllegalStateException.class, () -> {
            manager.borrowService(UnregisteredService.class);
        });
    }

    private static class TestService {
    }

    private static class UnregisteredService {
    }
}
