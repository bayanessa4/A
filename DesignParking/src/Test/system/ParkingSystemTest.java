package Test.system;

import org.junit.jupiter.api.Test;
import system.ParkingLot;
import system.ParkingSystem;

import static org.junit.jupiter.api.Assertions.*;

public class ParkingSystemTest {

    @Test
    public void testEnterVehicle() {
        ParkingSystem system = new ParkingSystem();
        int before = ParkingLot.getInstance().getAvailableSpots();

        system.enterVehicle("car", "999-88-777");
        int after = ParkingLot.getInstance().getAvailableSpots();

        assertEquals(before - 1, after, "החניה לא התעדכנה נכון");
    }

    @Test
    public void testExitVehicle() {
        ParkingSystem system = new ParkingSystem();
        system.enterVehicle("truck", "123-45-678");
        int before = ParkingLot.getInstance().getAvailableSpots();

        system.exitVehicle("123-45-678");
        int after = ParkingLot.getInstance().getAvailableSpots();

        assertEquals(before + 1, after, "הרכב לא הוסר נכון מהחניון");
    }
}
