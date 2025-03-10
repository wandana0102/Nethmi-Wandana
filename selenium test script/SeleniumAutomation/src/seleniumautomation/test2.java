package seleniumautomation;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.List;

public class test2 {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp() {
     
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64 (1)\\chromedriver-win64\\chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5)); // Implicit wait for general delays
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://demoqa.com/automation-practice-form");
    }

    @BeforeMethod
    public void clearForm() {
        driver.navigate().refresh(); 
    }

    @Test(priority = 1)
    public void testValidFormSubmission() throws InterruptedException {
        // Fill out the form
        driver.findElement(By.id("firstName")).sendKeys("Nethmi");
        driver.findElement(By.id("lastName")).sendKeys("Wandana");
        driver.findElement(By.id("userEmail")).sendKeys("test@example.com");
        driver.findElement(By.id("userNumber")).sendKeys("5678907678");
        Thread.sleep(3000);

        List<WebElement> genderOptions = driver.findElements(By.name("gender"));
        
    }


    @Test(priority = 2)
    public void testMandatoryFieldsValidation() {
        driver.findElement(By.id("submit")).click();

        // Check for mandatory fields validation
        int invalidFields = driver.findElements(By.className("is-invalid")).size();
        Assert.assertFalse(invalidFields > 0, "Mandatory fields validation failed!");
    }

    @Test(priority = 3)
    public void testInvalidEmailValidation() throws InterruptedException {
        driver.findElement(By.id("firstName")).sendKeys("Nethmi");
        driver.findElement(By.id("lastName")).sendKeys("Wandana");
        Thread.sleep(3000);

        WebElement emailField = driver.findElement(By.id("userEmail"));
        emailField.clear();
        emailField.sendKeys("name@example");
        Thread.sleep(3000);

        // Select a gender using JavaScriptExecutor
        WebElement femaleRadio = driver.findElement(By.xpath("//input[@value='Female']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", femaleRadio);
        Thread.sleep(3000);

        driver.findElement(By.id("userNumber")).sendKeys("5678907678");
        Thread.sleep(3000);
        driver.findElement(By.id("submit")).click();

        // Verify email validation
        Assert.assertFalse(emailField.getDomAttribute("class").contains("is-invalid"), "Invalid email validation passed!");
    }

    @AfterClass
    public void tearDown() {
       driver.quit();
    
    }
}
