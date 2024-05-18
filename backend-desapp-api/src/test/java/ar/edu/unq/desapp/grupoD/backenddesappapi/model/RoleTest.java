package ar.edu.unq.desapp.grupoD.backenddesappapi.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RoleTest {

    @Test
    void testRoleNoArgsConstructor() {
        Role role = new Role();
        assertNotNull(role);
    }

    @Test
    void testRoleDataAnnotation() {
        Role role = new Role();
        role.setId(1L);
        role.setName("Admin");
        assertEquals(1L, role.getId().longValue());
        assertEquals("Admin", role.getName());
    }
}


