package tests;

import framework.base.BaseTest;
import framework.pages.CartPage;
import framework.pages.InventoryPage;
import framework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartTest extends BaseTest {

    @Test(description = "Thêm 1 sản phẩm vào giỏ → badge hiển thị 1")
    public void testAddItemToCart() {
        String username = System.getenv("APP_USERNAME");
        String password = System.getenv("APP_PASSWORD");

        InventoryPage inventory = new LoginPage(getDriver())
                .login(username, password);
        inventory.addFirstItemToCart();
        Assert.assertEquals(inventory.getCartItemCount(), 1);
    }

    @Test(description = "Vào giỏ hàng → có 1 sản phẩm")
    public void testCartHasItem() {
        String username = System.getenv("APP_USERNAME");
        String password = System.getenv("APP_PASSWORD");

        CartPage cart = new LoginPage(getDriver())
                .login(username, password)
                .addFirstItemToCart()
                .goToCart();
        Assert.assertEquals(cart.getItemCount(), 1);
    }

    @Test(description = "Xóa sản phẩm → giỏ hàng rỗng")
    public void testRemoveItemFromCart() {
        String username = System.getenv("APP_USERNAME");
        String password = System.getenv("APP_PASSWORD");

        CartPage cart = new LoginPage(getDriver())
                .login(username, password)
                .addFirstItemToCart()
                .goToCart();
        cart.removeFirstItem();
        Assert.assertEquals(cart.getItemCount(), 0);
    }

    @Test(description = "Fluent interface: login → add → cart → checkout")
    public void testFluentInterface() {
        String username = System.getenv("APP_USERNAME");
        String password = System.getenv("APP_PASSWORD");

        new LoginPage(getDriver())
                .login(username, password)
                .addFirstItemToCart()
                .goToCart()
                .goToCheckout();
        Assert.assertTrue(getDriver().getCurrentUrl().contains("checkout"));
    }
}