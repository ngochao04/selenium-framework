package tests;

import framework.base.BaseTest;
import framework.pages.InventoryPage;
import framework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(description = "Đăng nhập hợp lệ → vào trang inventory")
    public void testLoginSuccess() {
        LoginPage loginPage = new LoginPage(getDriver());
        String username = System.getenv("APP_USERNAME");
        String password = System.getenv("APP_PASSWORD");

        InventoryPage inventory = loginPage.login(username, password);
        Assert.assertTrue(inventory.isLoaded(), "Trang inventory chưa load!");
    }

    @Test(description = "Đăng nhập sai mật khẩu → hiện thông báo lỗi")
    public void testLoginWrongPassword() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginExpectingFailure("standard_user", "wrongpass");
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Lỗi không hiển thị!");
        Assert.assertTrue(loginPage.getErrorMessage().contains("do not match"));
    }

    @Test(description = "Đăng nhập với username rỗng")
    public void testLoginEmptyUsername() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginExpectingFailure("", "secret_sauce");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Username is required"));
    }

    @Test(description = "Tài khoản bị khoá")
    public void testLockedOutUser() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginExpectingFailure("locked_out_user", "secret_sauce");
        Assert.assertTrue(loginPage.getErrorMessage().contains("locked out"));
    }
}