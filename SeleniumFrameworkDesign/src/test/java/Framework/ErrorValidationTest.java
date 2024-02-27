package Framework;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import Framework.PageObjects.CartPage;
import Framework.PageObjects.CheckoutPage;
import Framework.PageObjects.ConfirmationPage;
import Framework.PageObjects.ProductCatalog;
import Framework.TestComponent.BaseTest;
import Framework.TestComponent.Retry;

public class ErrorValidationTest extends BaseTest {
	
	@Test(groups= {"ErrorHandling"},retryAnalyzer = Retry.class)
	public  void loginErrorValidation() throws IOException {
	
		//Login 
	    landingPage.loginPage("manikumisai1@gmail.com", "Sairam@199");
		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());		
	}
	
	@Test
	public void productErrorValidation() {
     String productName= "ZARA COAT 3";
		
		//Login 
		ProductCatalog productlog= landingPage.loginPage("practice1998@gmail.com", "Sairam@1998");
		
		//get products list
		List<WebElement> products=  productlog.getProudctList();
		
		//Add Product to cart
		productlog.addProductTocart(productName);
		
		//Click on cart 
		CartPage cartpage= productlog.goToCartPage();
		
		//Verify items in Cart 
		Boolean match= cartpage.verifyProductDisplay("ZARA COAT 33");
		Assert.assertFalse(match);
		
	}

}
