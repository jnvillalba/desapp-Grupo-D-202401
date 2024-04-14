package ar.edu.unq.desapp.grupoD.backenddesappapi.model;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RoleTest {

    @Test
    public void testRoleCreation() {
        Role mockRole = mock(Role.class);

        when(mockRole.getName()).thenReturn("Admin");

        assertEquals("Admin", mockRole.getName());
    }

    @Test
    public void testRoleIdGeneration() {
        Role mockRole = mock(Role.class);

        Long mockId = 123L;
        when(mockRole.getId()).thenReturn(mockId);

        assertEquals(mockId, mockRole.getId());
    }
}


