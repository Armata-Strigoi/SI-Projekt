package test;

import com.company.SharedPaczka;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SharedPaczkaTest {

    @Test
    void obliczKubature() {
        SharedPaczka sp = new SharedPaczka(30,30,30,30);
        assertEquals(30*30*30,sp.ObliczKubature());
        assertNotEquals(25*30*30,sp.ObliczKubature());
    }
}