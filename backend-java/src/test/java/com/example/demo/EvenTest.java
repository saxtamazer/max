import org.junit.jupiter.api.Test;

import java.util.Calendar;

public class EvenTest {
    @Test
    public void test() {
        Calendar c = Calendar.getInstance();
        System.out.println(c.get(Calendar.WEEK_OF_MONTH));
    }
}
