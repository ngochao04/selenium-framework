package framework.pages;

import framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class CartPage extends BasePage {

    @FindBy(css = ".cart_item")                     private List<WebElement> cartItems;
    @FindBy(css = ".cart_item .btn_secondary")      private List<WebElement> removeButtons;
    @FindBy(id = "checkout")                        private WebElement checkoutButton;
    @FindBy(css = ".inventory_item_name")           private List<WebElement> itemNames;

    public CartPage(WebDriver driver) { super(driver); }

    public int getItemCount() {
        try { return cartItems.size(); } catch (Exception e) { return 0; }
    }

    public CartPage removeFirstItem() {
        if (!removeButtons.isEmpty()) waitAndClick(removeButtons.get(0));
        return this;
    }

    public List<String> getItemNames() {
        return itemNames.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public void goToCheckout() {
        waitAndClick(checkoutButton);
    }
}