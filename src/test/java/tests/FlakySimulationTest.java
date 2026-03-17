package tests;

import framework.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.concurrent.atomic.AtomicInteger;

public class FlakySimulationTest extends BaseTest {

    // AtomicInteger giữ giá trị qua các lần retry trong cùng JVM
    private static final AtomicInteger callCount = new AtomicInteger(0);

    @Test(description = "Mô phỏng flaky test — fail 2 lần đầu, pass lần 3")
    public void testFlakyScenario() {
        int count = callCount.incrementAndGet();
        System.out.println("[FlakyTest] Đang chạy lần thứ: " + count);

        if (count <= 2) {
            Assert.fail("Mô phỏng lỗi mạng tạm thời — lần " + count);
        }

        System.out.println("[FlakyTest] PASS ở lần thứ: " + count);
        Assert.assertTrue(true, "Test pass ở lần thứ " + count);
    }
}