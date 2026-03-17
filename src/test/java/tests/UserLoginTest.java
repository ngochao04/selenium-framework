package tests;

import framework.base.BaseTest;
import framework.pages.InventoryPage;
import framework.pages.LoginPage;
import framework.utils.JsonReader;
import framework.utils.TestDataFactory;
import framework.utils.UserData;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class UserLoginTest extends BaseTest {

    @DataProvider(name = "jsonUsers")
    public Object[][] getUsersFromJson() throws IOException {
        List<UserData> users = JsonReader.readUsers(
            "src/test/resources/testdata/users.json");
        return users.stream()
            .map(u -> new Object[]{u.username, u.password, 
                                   u.expectSuccess, u.description})
            .toArray(Object[][]::new);
    }

    @Test(dataProvider = "jsonUsers", description = "Login test từ JSON")
    public void testLoginFromJson(String username, String password,
                                   boolean expectSuccess, String desc) {
        LoginPage loginPage = new LoginPage(getDriver());

        if (expectSuccess) {
            InventoryPage inventory = loginPage.login(username, password);
            Assert.assertTrue(inventory.isLoaded(),
                "FAIL (expect success): " + desc);
        } else {
            loginPage.loginExpectingFailure(username, password);
            Assert.assertTrue(loginPage.isErrorDisplayed(),
                "FAIL (expect failure): " + desc);
        }
    }

    @Test(description = "Faker: sinh dữ liệu checkout ngẫu nhiên")
    public void testFakerDataGeneration() {
        Map<String, String> data = TestDataFactory.randomCheckoutData();

        // In ra để chứng minh mỗi lần khác nhau
        System.out.println("=== FAKER DATA ===");
        System.out.println("First Name : " + data.get("firstName"));
        System.out.println("Last Name  : " + data.get("lastName"));
        System.out.println("PostalCode : " + data.get("postalCode"));
        System.out.println("==================");

        // Kiểm tra data không rỗng
        Assert.assertFalse(data.get("firstName").isEmpty());
        Assert.assertFalse(data.get("lastName").isEmpty());
        Assert.assertEquals(data.get("postalCode").length(), 5);
    }
}