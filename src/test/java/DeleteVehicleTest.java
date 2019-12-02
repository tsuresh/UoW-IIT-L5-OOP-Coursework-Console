import controllers.WestminsterRentalVehicleManager;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteVehicleTest {

    @InjectMocks
    WestminsterRentalVehicleManager manager = new WestminsterRentalVehicleManager();

    @Test
    public void deleteVehicle() {
        assertTrue(manager.deleteVehicle("0000"));
    }

    @Test
    public void deleteVehicleBlankPlateNo() {
        assertFalse(manager.deleteVehicle(""));
    }

    @Test
    public void deleteVehicleInvalidPlateNo() {
        assertFalse(manager.deleteVehicle("-000!@"));
    }

}
