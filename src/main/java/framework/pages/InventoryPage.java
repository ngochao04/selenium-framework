package framework.pages;

import framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class InventoryPage extends BasePage {

    @FindBy(css = ".inventory_list")         private WebElement inventoryList;
    @FindBy(css = ".shopping_cart_badge")    private WebElement cartBadge;
    @FindBy(css = ".inventory_item button")  private List<WebElement> addToCartButtons;

    public InventoryPage(WebDriver driver) { super(driver); }

    public boolean isLoaded() {
        return isElementVisible(By.cssSelector(".inventory_list"));
    }

    public InventoryPage addFirstItemToCart() {
        waitAndClick(addToCartButtons.get(0));
        return this;
    }

    public InventoryPage addItemByName(String name) {
        driver.findElement(By.xpath(
            "//div[text()='" + name + "']/ancestor::div[@class='inventory_item']//button"
        )).click();
        return this;
    }

    public int getCartItemCount() {
        try {
            return Integer.parseInt(cartBadge.getText().trim());
        } catch (Exception e) {
            return 0;
        }
    }

    public CartPage goToCart() {
        driver.findElement(By.className("shopping_cart_link")).click();
        return new CartPage(driver);
    }
}