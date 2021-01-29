package test;

import com.company.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class LogowanieTest {

        Connect contest;
        Connect connect;
        Magazyn magtest;
        Magazyn magazyn;
        Logowanie logowanie;
        Logowanie logtest;
        PracownikKurier kurier;
        PracownikKsiegowy ksiegowy;
        PracownikStacjonarny pracownikStacjonarny;

    @BeforeEach
    void init(){
        contest = new Connect("root","root");
        connect = new Connect("root","root");
        PaczkaFactory.PobierzZBazy(connect.connection);
        magazyn = new Magazyn(connect.connection);
        magtest = new Magazyn(contest.connection);
        logtest =  new Logowanie(magtest,contest.connection);
        logowanie = new Logowanie(magazyn,connect.connection);
        kurier = new PracownikKurier(magtest,1,contest.connection);
        ksiegowy = new PracownikKsiegowy(magtest,2,contest.connection);
        pracownikStacjonarny = new PracownikStacjonarny(magtest,3);

    }

    @Test
    void uprawnijSprawdz() {
        assertEquals(kurier.getClass(),logowanie.Uprawnij("Kurier",4).getClass(),"Obiekty powinny być tej samej klasy");
        assertEquals(ksiegowy.getClass(),logowanie.Uprawnij("Ksiegowy",5).getClass(),"Obiekty powinny być tej samej klasy");
        assertEquals(pracownikStacjonarny.getClass(),logowanie.Uprawnij("PracownikStacjonarny",6).getClass(),"Obiekty powinny być tej samej klasy");
        assertNotEquals(pracownikStacjonarny.getClass(),logowanie.Uprawnij("Kurier",7).getClass(),"Obiekty nie powinny być tej samej klasy");
        assertNotEquals(kurier.getClass(),logowanie.Uprawnij("Ksiegowy",8).getClass(),"Obiekty nie powinny być tej samej klasy");
        assertNotEquals(ksiegowy.getClass(),logowanie.Uprawnij("PracownikStacjonarny",9).getClass(),"Obiekty nie powinny być tej samej klasy");
        assertEquals(kurier.getClass(),logowanie.SprawdzDane("kurier","root").getClass(),"Obiekty powinny być tej samej klasy");
        assertEquals(ksiegowy.getClass(),logowanie.SprawdzDane("ksieg","root").getClass(),"Obiekty powinny być tej samej klasy");
        assertEquals(pracownikStacjonarny.getClass(),logowanie.SprawdzDane("root","root").getClass(),"Obiekty powinny być tej samej klasy");
        assertNotEquals(kurier.getClass(),logowanie.SprawdzDane("ksieg","root").getClass(),"Obiekty nie powinny być tej samej klasy");
        assertNotEquals(ksiegowy.getClass(),logowanie.SprawdzDane("root","root").getClass(),"Obiekty nie powinny być tej samej klasy");
        assertNotEquals(pracownikStacjonarny.getClass(),logowanie.SprawdzDane("kurier","root").getClass(),"Obiekty nie powinny być tej samej klasy");
    }
}