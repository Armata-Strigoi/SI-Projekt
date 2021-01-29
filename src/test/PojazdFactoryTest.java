package test;

import com.company.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PojazdFactoryTest {

    @Test
    void stworzPojazd() {
        Pojazd pojazdMaly  = PojazdFactory.stworzPojazd(1,"Skuter","Hundai","Backbone",10000);
        Pojazd pojazdSredni  = PojazdFactory.stworzPojazd(1,"Skuter","Hundai","Backbone",40000);
        Pojazd pojazdDuzy  = PojazdFactory.stworzPojazd(1,"Skuter","Hundai","Backbone",140000);
        assertTrue(Pojazd.class.isInstance(pojazdMaly));
        assertTrue(Pojazd.class.isInstance(pojazdSredni));
        assertTrue(Pojazd.class.isInstance(pojazdDuzy));
        assertTrue(PojazdMaly.class.isInstance(pojazdMaly));
        assertTrue(PojazdSredni.class.isInstance(pojazdSredni));
        assertTrue(PojazdDuzy.class.isInstance(pojazdDuzy));
        assertFalse(PojazdDuzy.class.isInstance(pojazdMaly));
        assertFalse(PojazdSredni.class.isInstance(pojazdMaly));
        assertFalse(PojazdMaly.class.isInstance(pojazdDuzy));
        assertFalse(PojazdSredni.class.isInstance(pojazdDuzy));
        assertFalse(PojazdMaly.class.isInstance(pojazdSredni));
        assertFalse(PojazdDuzy.class.isInstance(pojazdSredni));

    }
}