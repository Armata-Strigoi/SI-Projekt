package test;

import static org.junit.jupiter.api.Assertions.*;

import com.company.Connect;
import org.junit.jupiter.api.*;

class ConnectTest {

    private Connect connection;

    @AfterEach
    void tearDown() {
    }

    @Test
    void connect() {
        connection = new Connect("root","root");
        assertEquals(true,connection.connect("root","root"), "Połączenie z bazą powinno działa");
        assertEquals(false,connection.connect("root2","root2"), "Połączenie z bazą nie powinno się powieść");
    }

    @Test
    void close() {
        connection = new Connect("root","root");
        assertEquals(true,connection.close(), "Połączenie z bazą powinno zostało przerwane");
    }
}