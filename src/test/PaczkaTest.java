package test;

import com.company.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaczkaTest {

    @Test
    void zaktualizujStatus() {
        SharedPaczka sp = new SharedPaczka(10,10,10,10);
        Paczka paczka = new Paczka("hello",sp,2,0,10,new java.util.Date(),null,"tata",12,12,"123123123","tata",12,12,"123123123",null);
        assertEquals(1,paczka.ZaktualizujStatus(),"Status powinien wynosic 1");
    }

    @Test
    void dostarcz() {
        SharedPaczka sp = new SharedPaczka(10,10,10,10);
        Paczka paczka = new Paczka("hello",sp,2,0,10,new java.util.Date(),null,"tata",12,12,"123123123","tata",12,12,"123123123",null);
        assertEquals(new java.util.Date(),paczka.Dostarcz(2));
    }

    @Test
    void cofnijStatus() {
        SharedPaczka sp = new SharedPaczka(10,10,10,10);
        Paczka paczka = new Paczka("hello",sp,2,1,10,new java.util.Date(),null,"tata",12,12,"123123123","tata",12,12,"123123123",null);
        paczka.CofnijStatus();
        assertEquals(0,paczka.numer_statusu,"Numer statusu powinien zostac na 0");
    }

    @Test
    void decorate() {
        SharedPaczka sp = new SharedPaczka(10,10,10,10);
        PaczkaCore delikatna = new PaczkaDelikatna(null);
        PaczkaCore magnetyczna = new PaczkaZZawartosciaMagnetyczna(delikatna);
        PaczkaCore paletowa = new PaczkaPaletowa(magnetyczna);
        PaczkaCore paczka = new Paczka("hello",sp,2,1,10,new java.util.Date(),null,"tata",12,12,"123123123","tata",12,12,"123123123",paletowa);
        PaczkaCore paczka2 = new Paczka("hello",sp,2,1,10,new java.util.Date(),null,"tata",12,12,"123123123","tata",12,12,"123123123",null);
        assertNotEquals(paczka.decorate(),paczka2.decorate(),"Rodzaje powinny inne byc takie same");
        assertEquals(paczka.decorate().contains("Paletowa"),paletowa.decorate().contains("Paletowa"));
        assertEquals(paczka.decorate().contains("Z zawartoscia megnetyczna"),magnetyczna.decorate().contains("Z zawartoscia megnetyczna"));
        assertEquals(paczka.decorate().contains("Delikatna"),delikatna.decorate().contains("Delikatna"));
        assertNotEquals(paczka.decorate().contains("Paletowa"),magnetyczna.decorate().contains("Paletowa"));
        assertNotEquals(paczka.decorate().contains("Z zawartoscia megnetyczna"),delikatna.decorate().contains("Z zawartoscia megnetyczna"));

    }
}