package selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;

public class FirstSeleniumTest {

    public WebDriver driver;
    public WebDriverWait wait;
    public Actions actions;

    @BeforeMethod
    public void setUp() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no sandbox");
        options.addArguments("--window-size=1440x900");

        WebDriverManager.chromedriver().browserVersion("103").setup();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        //implicitWait for element (waiting for the element to appear)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        //Wait for the pages (waiting for page loading)
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));

        //Explicit wait
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        //Init Actions
        actions = new Actions(driver);
    }

    @AfterMethod
    public void tearDown() {
        driver.close();
    }

    @Test
    public void testLandingPage() {

        driver.get("http://training.skillo-bg.com");

        By loginLinkBy = By.id("nav-link-login");
        WebElement loginLink = driver.findElement(loginLinkBy);
        Assert.assertTrue(loginLink.isDisplayed());

        List<WebElement> listPost = driver.findElements(By.xpath("//div[@class='row post-list-container']//app-post-detail"));
        Assert.assertEquals(listPost, 3);

        loginLink.click();

        WebElement signInTxt = driver.findElement(By.xpath("//p[text()='Sign in']"));
        Assert.assertTrue(signInTxt.isDisplayed());

        //   Thread.sleep(2000);
    }

    @Test
    public void testLoginPage() {

        driver.get("http://training.skillo-bg.com/users/login");

        WebElement username = driver.findElement(By.cssSelector("#defaultLoginFormUsername"));
        WebElement password = driver.findElement(By.cssSelector("#defaultLoginFormPassword"));
        WebElement signInBtn = driver.findElement(By.cssSelector("#sign-in-button"));

        username.click();
        username.clear();
        username.sendKeys("roni");
        password.sendKeys("roni95");
        signInBtn.click();

        By logoutLink = By.cssSelector(".fas.fa-sign-out-alt.fa-lg");
        Assert.assertTrue(driver.findElement(logoutLink).isDisplayed());

        //here we can use actions in case we want to chain methods
        // always should end with ".perform" and before that ".build" if we have many actions
//        actions.click(username).sendKeys("roni").perform();
//        actions.click(password).sendKeys("roni95").perform();
    }
    @Test(invocationCount = 3)
    public void testMobileBG(){

        String carMarka = "Mini";
        String carModel = "Cooper";

        driver.get("https://www.mobile.bg/pcgi/mobile.cgi");

        By marka = By.xpath("//select[@name='marka']");
        By model = By.xpath("//select[@name='model']");
        By searchBtn = By.xpath("//input[@name='button2']");
        By towardSite = By.xpath("//p[text()='Към сайта']");

        driver.findElement(towardSite).click();

        Select markaDropdown = new Select(driver.findElement(marka));
        markaDropdown.selectByValue(carMarka);

        Select modelDropdown = new Select(driver.findElement(model));
        modelDropdown.selectByValue(carModel);

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(searchBtn)));
        driver.findElement(searchBtn).click();

        List<WebElement> listAdd = driver.findElements(By.xpath("//form[@name='search']//a[@class='mmm']"));
        listAdd.forEach(add ->{

            Assert.assertTrue(add.getText().contains(carMarka + " " + carModel));
        });

    }

}
