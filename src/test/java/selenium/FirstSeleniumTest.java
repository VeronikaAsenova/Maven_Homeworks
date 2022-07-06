package selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class FirstSeleniumTest {

    public WebDriver driver;

    @BeforeMethod
    public void setUp() {

        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless");
        options.addArguments("--no sandbox");
        options.addArguments("--window-size=1440x900");

        WebDriverManager.chromedriver().browserVersion("103").setup();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        //implicitWait for element (waiting for the element to appear)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        //Wait for the pages (waiting for page loading)
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
    }

    @AfterMethod
    public void tearDown() {
        driver.close();
    }

    @Test
    public void testDemo() throws InterruptedException {

        driver.get("http://training.skillo-bg.com");

        By loginLinkBy = By.id("nav-link-login");
        WebElement loginLink = driver.findElement(loginLinkBy);

        loginLink.click();

        WebElement signInTxt = driver.findElement(By.xpath("//p[text()='Sign in']"));

        Assert.assertTrue(signInTxt.isDisplayed());

        //   Thread.sleep(2000 );
    }

}
