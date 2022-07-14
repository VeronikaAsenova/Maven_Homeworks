package selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Alert;
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

import static org.bouncycastle.crypto.tls.ContentType.alert;


public class InternetExamples_Homework {
    public WebDriver driver;
    public WebDriverWait wait;
    public Actions actions;

    @BeforeMethod
    public void setUp() {

        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
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
    public void testAddRemoveElements() {

        driver.get("https://the-internet.herokuapp.com/add_remove_elements/");

        By listElements = By.cssSelector("div[id='elements']>button");
        List<WebElement> listDeletes = driver.findElements(listElements);
        Assert.assertEquals(listDeletes.size(), 0);

        WebElement addElementBtn = driver.findElement(By.cssSelector(".example>button"));
        addElementBtn.click();
        addElementBtn.click();

        listDeletes = driver.findElements(listElements);
        Assert.assertEquals(listDeletes.size(), 2);

        //click on delete btn and expect to delete 1 item (index 0)
        listDeletes.get(0).click();

        listDeletes = driver.findElements(listElements);
        Assert.assertEquals(listDeletes.size(), 1);

        //adding 3 new items
        addElementBtn.click();
        addElementBtn.click();
        addElementBtn.click();

        listDeletes = driver.findElements(listElements);
        Assert.assertEquals(listDeletes.size(), 4);

        //delete 2 items
        listDeletes.get(0).click();
        listDeletes.get(1).click();

        listDeletes = driver.findElements(listElements);
        Assert.assertEquals(listDeletes.size(), 2);

    }

    @Test
    public void testBasicAuth() throws InterruptedException {

        driver.get("https://admin:admin@the-internet.herokuapp.com/basic_auth");

        By baseTxt = By.xpath("//p");
        Assert.assertEquals(driver.findElement(baseTxt).getText(), "Congratulations! You must have the proper credentials.");

        Thread.sleep(2000);
    }

    @Test
    public void testCheckboxes() {

        driver.get("https://the-internet.herokuapp.com/checkboxes");

        By checkboxes = By.xpath("//form[@id='checkboxes']/input");
        WebElement checkboxOne = driver.findElements(checkboxes).get(0);
        WebElement checkboxTwo = driver.findElements(checkboxes).get(1);

        Assert.assertFalse(checkboxOne.isSelected());
        Assert.assertTrue(checkboxTwo.isSelected());

        checkboxOne.click();
        checkboxTwo.click();

        Assert.assertTrue(checkboxOne.isSelected());
        Assert.assertFalse(checkboxTwo.isSelected());

    }

    @Test
    public void testContextMenu() {

        driver.get("https://the-internet.herokuapp.com/context_menu");

        WebElement divHotSpot = driver.findElement(By.cssSelector("#hot-spot"));

        actions.contextClick(divHotSpot).perform();

        Alert alert = driver.switchTo().alert();

        Assert.assertEquals(alert.getText(), "You selected a context menu");
        alert.accept();

        driver.switchTo().defaultContent();
    }

    @Test
    public void testDragAndDrop() {

        driver.get("https://jqueryui.com/droppable/");

        WebElement iFrame = driver.findElement(By.xpath("//iframe"));

        driver.switchTo().frame(iFrame);

        WebElement elementA = driver.findElement(By.xpath("//div[@id='draggable']"));
        WebElement elementB = driver.findElement(By.xpath("//div[@id='droppable']"));

        WebElement dropTxt = driver.findElement(By.xpath("//div[@id='droppable']/p"));
        Assert.assertEquals(dropTxt.getText(), "Drop here");

        actions.dragAndDrop(elementA, elementB).perform();

        dropTxt = driver.findElement(By.xpath("//div[@id='droppable']/p"));
        Assert.assertEquals(dropTxt.getText(), "Dropped!");

        driver.switchTo().defaultContent();

    }

    @Test
    public void testDropDown() {

        driver.get("https://the-internet.herokuapp.com/dropdown");

        String option1 = "Option 1";
        String option2 = "Option 2";

        By dropDown = By.xpath("//select[@id='dropdown']");

        Select optionDropDown = new Select(driver.findElement(dropDown));
        optionDropDown.selectByVisibleText(option2);

    }
}
