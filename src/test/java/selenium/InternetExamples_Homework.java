package selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.SourceType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.bouncycastle.crypto.tls.ContentType.alert;


public class InternetExamples_Homework {
    public WebDriver driver;
    public WebDriverWait wait;
    public Actions actions;

    public JavascriptExecutor executor;

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

        //init JS executor
        executor = (JavascriptExecutor) driver;
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
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
        WebElement opt2 = driver.findElement(By.xpath("//option[@value='2']"));

        By dropDown = By.xpath("//select[@id='dropdown']");

        Select optionDropDown = new Select(driver.findElement(dropDown));
        optionDropDown.selectByVisibleText(option2);

        Assert.assertTrue(opt2.isDisplayed());
    }

    @Test
    public void testDynamicContent() {

        driver.get("https://the-internet.herokuapp.com/dynamic_content");

        By rowsTxt = By.cssSelector(".large-10:not(.large-centered)");
        By rowsImages = By.cssSelector(".large-2:not(.large-centered)>img");

        List<WebElement> listTextElements = driver.findElements(rowsTxt);
        List<WebElement> listTextImages = driver.findElements(rowsImages);

        List<String> listImages = new ArrayList<>();

        for (WebElement element : listTextImages) {
            listImages.add(element.getAttribute("src"));
        }

        List<String> listTexts = new ArrayList<>();

        for (WebElement element : listTextElements
        ) {
            listTexts.add(element.getText());
        }

        //Refresh the elements
        WebElement clickHereBtn = driver.findElement(By.xpath("//a[text()='click here']"));
        clickHereBtn.click();

        List<WebElement> listTextsAfter = driver.findElements(rowsTxt);
        List<WebElement> listImagesAfter = driver.findElements(rowsImages);

        for (int i = 0; i < listTextsAfter.size(); i++) {
            System.out.println("XXX: " + listTexts.get(i));
            System.out.println("XXX2: " + listTextsAfter.get(i).getText());
            Assert.assertNotEquals(listTexts.get(i), listTextsAfter.get(i).getText());
        }

        for (int i = 0; i < listImagesAfter.size(); i++) {
            System.out.println("XXX: " + listImages.get(i));
            System.out.println("XXX2: " + listImagesAfter.get(i).getAttribute("src"));
            Assert.assertNotEquals(listImages.get(i), listImagesAfter.get(i).getAttribute("src"));
        }
    }

    @Test
    public void testDynamicControls() {

        driver.get("https://the-internet.herokuapp.com/dynamic_controls");

        WebElement removeBtn = driver.findElement(By.xpath("//button[@onclick='swapCheckbox()']"));
        removeBtn.click();

        By loadingBar = By.xpath("//div[@id='loading']");
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(loadingBar)));
        Assert.assertTrue(driver.findElement(loadingBar).isDisplayed());

        //It's gone! appear
        By message = By.xpath("//p[@id='message']");
        Assert.assertTrue(driver.findElement(message).isDisplayed());

        WebElement addBtn = driver.findElement(By.xpath("//button[text()='Add']"));
        addBtn.click();

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(loadingBar)));
        Assert.assertTrue(driver.findElement(loadingBar).isDisplayed());

        //It's back! appear
        Assert.assertTrue(driver.findElement(message).isDisplayed());
    }

    @Test
    public void testFloatingMenu() {

        driver.get("https://the-internet.herokuapp.com/floating_menu");

        By menu = By.xpath("//div[@id='menu']");
        Assert.assertTrue(driver.findElement(menu).isDisplayed());

        executor.executeScript("window.scrollBy(0,3000)");
        Assert.assertTrue(driver.findElement(menu).isDisplayed());

        executor.executeScript("window.scrollBy(0,-2000)");
        Assert.assertTrue(driver.findElement(menu).isDisplayed());
    }

    @Test
    public void testHovers() {

        driver.get("https://the-internet.herokuapp.com/hovers");

        WebElement figureLeft = driver.findElement(By.xpath("//div[@class='example']/div[1]"));
        WebElement figureMiddle = driver.findElement(By.xpath("//div[@class='example']/div[2]"));
        WebElement figureRight = driver.findElement(By.xpath("//div[@class='example']/div[3]"));

        WebElement hoveredL = driver.findElement(By.xpath("//a[@href='/users/1']"));
        WebElement hoveredM = driver.findElement(By.xpath("//a[@href='/users/2']"));
        WebElement hoveredR = driver.findElement(By.xpath("//a[@href='/users/3']"));

        actions.moveToElement(figureLeft).perform();

        Assert.assertTrue(hoveredL.isDisplayed());
        Assert.assertFalse(hoveredM.isDisplayed());
        Assert.assertFalse(hoveredR.isDisplayed());

        actions.moveToElement(figureMiddle).perform();

        Assert.assertTrue(hoveredM.isDisplayed());
        Assert.assertFalse(hoveredL.isDisplayed());
        Assert.assertFalse(hoveredR.isDisplayed());

        actions.moveToElement(figureRight).perform();

        Assert.assertTrue(hoveredR.isDisplayed());
        Assert.assertFalse(hoveredL.isDisplayed());
        Assert.assertFalse(hoveredM.isDisplayed());
    }

    @Test
    public void testMultipleWindows() {

        driver.get("https://the-internet.herokuapp.com/windows");

        WebElement clickHereLink = driver.findElement(By.xpath("//a[@href='/windows/new']"));

        //identifier for the current window (PRIMARY window)
        String originalWindow = driver.getWindowHandle();

        clickHereLink.click();

        //изцикля между всички прозорци и последния, на който switch-ва е последно отворения
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }

        String newWindow = driver.getWindowHandle();

        WebElement newWindowTxt = driver.findElement(By.xpath("//h3"));
        Assert.assertTrue(newWindowTxt.isDisplayed());
        Assert.assertEquals(newWindowTxt.getText(), "New Window");

        // връщаме се към стария прозорец
        driver.switchTo().window(originalWindow);
        Assert.assertTrue(clickHereLink.isDisplayed());

        newWindowTxt = driver.findElement(By.xpath("//h3"));
        Assert.assertEquals(newWindowTxt.getText(), "Opening a new window");

        driver.switchTo().window(newWindow);
        newWindowTxt = driver.findElement(By.xpath("//h3"));
        Assert.assertEquals(newWindowTxt.getText(), "New Window");
    }

    @Test
    public void testRedirectLink() {

        driver.get("https://the-internet.herokuapp.com/redirector");

        WebElement redirectLink = driver.findElement(By.xpath("//a[@id='redirect']"));

        String originalURL = driver.getCurrentUrl();
        Assert.assertEquals(originalURL, "https://the-internet.herokuapp.com/redirector");

        redirectLink.click();

        String redirectedURL = driver.getCurrentUrl();
        Assert.assertNotEquals(redirectedURL, originalURL);
        Assert.assertEquals(redirectedURL, "https://the-internet.herokuapp.com/status_codes");
    }
}
