package ar.edu.unq.desapp.grupoD.backenddesappapi.model;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RoleTest {

    @Test
    public void testRoleNoArgsConstructor() {
        Role role = new Role();
        assertNotNull(role);
    }

    @Test
    public void testRoleDataAnnotation() {
        Role role = new Role();
        role.setId(1L);
        role.setName("Admin");
        assertEquals(1L, role.getId().longValue());
        assertEquals("Admin", role.getName());
    }
}


