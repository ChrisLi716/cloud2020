import org.junit.Test;

import java.time.ZonedDateTime;

public class TestMain {

    @Test
    public void testZonedTime() {

        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        System.out.println(zonedDateTime);

    }

}
