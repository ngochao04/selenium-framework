package tests;

import framework.base.BaseTest;
import framework.pages.LoginPage;
import framework.utils.ExcelReader;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginDDTTest extends BaseTest {

    @DataProvider(name = "excelLoginData")
    public Object[][] getLoginDataFromExcel() {
        return ExcelReader.getData(
            "src/test/resources/testdata/login_data.xlsx", "SmokeCases");
    }

    @DataProvider(name = "excelNegativeData")
    public Object[][] getNegativeData() {
        return ExcelReader.getData(
            "src/test/resources/testdata/login_data.xlsx", "NegativeCases");
    }

    @Test(dataProvider = "excelLoginData", groups = "smoke",
          description = "Smoke test đăng nhập từ Excel")
    public void testLoginSmoke(String username, String password,
                                String expectedUrl, String desc) {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(username, password);
        Assert.assertTrue(getDriver().getCurrentUrl().contains(expectedUrl),
            "FAIL: " + desc);
    }

    @Test(dataProvider = "excelNegativeData", groups = "regression",
          description = "Negative test đăng nhập từ Excel")
    public void testLoginNegative(String username, String password,
                                   String expectedError, String desc) {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginExpectingFailure(username, password);
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Lỗi không hiện: " + desc);
        Assert.assertTrue(loginPage.getErrorMessage().contains(expectedError),
            "Thông báo lỗi không đúng: " + desc);
    }
}