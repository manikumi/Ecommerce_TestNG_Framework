package Framework;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Framework.PageObjects.CartPage;
import Framework.PageObjects.CheckoutPage;
import Framework.PageObjects.ConfirmationPage;
import Framework.PageObjects.LandingPage;
import Framework.PageObjects.OrderPage;
import Framework.PageObjects.ProductCatalog;
import Framework.TestComponent.BaseTest;
import io.github.bonigarcia.wdm.WebDriverManager;

public class OrderProductTest extends BaseTest {
	
	String productName= "ZARA COAT 3";
	
	@Test(dataProvider= "getDataFromJson",groups= {"Purchase"})
	public  void submitOrder(HashMap <String,String> input) throws IOException {
		
		
		
		//Login 
		ProductCatalog productlog= landingPage.loginPage(input.get("email"),input.get("password"));
		
		//get products list
		List<WebElement> products=  productlog.getProudctList();
		
		//Add Product to cart
		productlog.addProductTocart(input.get("product"));
		
		//Click on cart 
		CartPage cartpage= productlog.goToCartPage();
		
		//Verify items in Cart 
		Boolean match= cartpage.verifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);
		
		//Check out element
		CheckoutPage checkoutpage= cartpage.goToCheckOut();
	
		//Select country
		checkoutpage.selectCountry("India");
		
		//Submit the order
		ConfirmationPage confirmationPage= checkoutpage.submitOrder();
		
		//Verify message
		String confirmMsg= confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMsg.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		
	}
	
	@Test(dependsOnMethods = {"submitOrder"})
	public void orderHistoryTest() {
		ProductCatalog productlog= landingPage.loginPage("manikumisai11@gmail.com", "Sairam@1998");
		OrderPage orderpage= productlog.goToOrdersPage(); 
		Boolean match= orderpage.verifyOrderDisplay(productName);
		Assert.assertTrue(match);		
	}
	
	@DataProvider
	public Object[][] getDataFromJson() throws IOException {
		List<HashMap<String,String>> data= getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\Framework\\Testdata\\PurchaseOrder.json");	
		return new Object[][] {{data.get(0)},{data.get(1)}};
	}
	
	//@DataProvider
	public Object[][] getData() {
		HashMap<String,String> map= new HashMap();
		map.put("email", "manikumisai11@gmail.com");
		map.put("password", "Sairam@1998");
		map.put("productName", "ZARA COAT 3");
		
		HashMap<String,String> map1= new HashMap();
		map1.put("email", "practice1998@gmail.com");
		map1.put("password", "Sairam@1998");
		map1.put("productName", "ADIDAS ORIGINAL");
		
		return new Object[][] {{map},
			{map1}};
	}
	
	//@DataProvider
	public Object[][] testData() {
		return new Object[][] {{"manikumisai11@gmail.com"},{"Sairam@1998"},{"ZARA COAT 3"},
			{"practice1998@gmail.com"},{"Sairam@1998"},{"ADIDAS ORIGINAL"}};
		
	}

}
