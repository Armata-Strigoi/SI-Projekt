package test;

import com.company.GlownySystem;
import com.company.PaczkaFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GlownySystemTest {

    @Test
    void getInstance() {
        GlownySystem glownySystem = GlownySystem.getInstance();
        GlownySystem glownySystem2 = GlownySystem.getInstance();
        assertEquals(glownySystem,glownySystem2,"Systemy powinny być tym samym obiektem");
        assertNotEquals(null,glownySystem,"Obiekty powinny być różne");
    }

    @Test
    void zaladujSystem() {
        GlownySystem glownySystem = GlownySystem.getInstance();
        glownySystem.zaladujSystem("root","root");
        assertNotNull(glownySystem);
        assertNotNull(glownySystem.connect, "Połączenie nie powinno być nulem");
        assertNotNull(glownySystem.log, "System logowania nie powinien być nulem");
    }
}