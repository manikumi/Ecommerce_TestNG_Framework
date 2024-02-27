package Framework.PageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Framework.AbstractComponents.AbstarctComponent;

public class ProductCatalog extends AbstarctComponent {
	
	WebDriver driver;
	
	public ProductCatalog(WebDriver driver) {
		//initialization code
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	

	//Page factory
	@FindBy(css=".mb-3")
	List<WebElement> products;
	
	@FindBy(css=".ng-animating")
	WebElement spinner;
	
	By productsBy= By.cssSelector(".mb-3");
	By addToCart= By.cssSelector(".card-body button:last-of-type");
	By toast= By.cssSelector("#toast-container");
	
	public List<WebElement> getProudctList() {
		waitForElementtoAppear(productsBy);
		return products;
	}
	
	public WebElement getProductByname(String productName) {
		WebElement prod= getProudctList().stream().filter(product->
		product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
		return prod;
	}
	
	public void addProductTocart(String productName ) {
		WebElement prod= getProductByname(productName);
		prod.findElement(addToCart).click();
		waitForElementtoAppear(toast);
		waitForElementToDisapper(spinner);
		
	}
}
