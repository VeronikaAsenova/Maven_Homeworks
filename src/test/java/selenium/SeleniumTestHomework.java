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

public class SeleniumTestHomework {

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
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        //Wait for the pages (waiting for page loading)
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));

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
    public void testDominosRestaurantRegion() {

        String restaurantChoice = "София - Студентски Град";

        driver.get("https://www.dominos.bg/?gclid=EAIaIQobChMIvZbAk6bp-AIVQ-d3Ch3HRgUIEAAYASAAEgIk9fD_BwE");

        WebElement rejectOffer = driver.findElement(By.xpath("//button[@class='align-right secondary slidedown-button']"));
        wait.until(ExpectedConditions.visibilityOf(rejectOffer));
        rejectOffer.click();

        WebElement menuBtn = driver.findElement(By.xpath("//a[@class='font-bold open-modal']"));
        menuBtn.click();

        //check if the pop-up "Изберете ресторант" is shown to the user
        By specialMessage = By.xpath("//h3[text()='ИЗБЕРЕТЕ РЕСТОРАНТ ']");
        Assert.assertTrue(driver.findElement(specialMessage).isDisplayed());

        //select a restaurant by region
        By restaurant = By.xpath("//select[@name='store_id']");
        Select restDropdown = new Select(driver.findElement(restaurant));
        restDropdown.selectByVisibleText(restaurantChoice);

        //check if the right page is loaded after choosing a restaurant's region
        String URL = driver.getCurrentUrl();
        Assert.assertTrue(URL.contains("https://www.dominos.bg/menu/sofia-student-city"));

    }

    @Test
    public void isPizzaMenu() {

        driver.get("https://www.dominos.bg/menu/sofia-student-city");

        WebElement rejectOffer = driver.findElement(By.xpath("//button[@class='align-right secondary slidedown-button']"));
        wait.until(ExpectedConditions.visibilityOf(rejectOffer));
        rejectOffer.click();

        WebElement pizzaMenu = driver.findElement(By.xpath("//a[@data-perma='pizzas']"));

        wait.until(ExpectedConditions.visibilityOf(pizzaMenu));
        pizzaMenu.click();

        //check if the right menu is loaded
        String URL = driver.getCurrentUrl();
        Assert.assertTrue(URL.contains("https://www.dominos.bg/menu/sofia-student-city#pizzas"));

    }

    @Test
    public void masterBurgerPizza() {

        driver.get("https://www.dominos.bg/menu/sofia-student-city#pizzas");

        WebElement rejectOffer = driver.findElement(By.xpath("//button[@class='align-right secondary slidedown-button']"));
        wait.until(ExpectedConditions.visibilityOf(rejectOffer));
        rejectOffer.click();

        WebElement masterBurgerPizza = driver.findElement(By.xpath("//div[@data-perma='master_burger_pizza']"));
        masterBurgerPizza.click();

        //check if the right page is loaded after choosing a menu
        By specialMessage = By.xpath("//span[text()='МАСТЪР БУРГЕР ПИЦА']");
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(specialMessage)));
        Assert.assertTrue(driver.findElement(specialMessage).isDisplayed());
    }

    @Test
    public void orderPizza() {

        driver.get("https://www.dominos.bg/menu/sofia-student-city#pizzas");

        WebElement rejectOffer = driver.findElement(By.xpath("//button[@class='align-right secondary slidedown-button']"));
        wait.until(ExpectedConditions.visibilityOf(rejectOffer));
        rejectOffer.click();

        WebElement masterBurgerPizza = driver.findElement(By.xpath("//div[@data-perma='master_burger_pizza']"));
        masterBurgerPizza.click();

        String middlePizza = "Средна (6 Парчета)";
        String bizPizza = "Голяма (8 Парчета)";
        String jumboPizza = "Джъмбо (12 Парчета)";

        String pasteTraditional = "Традиционно";
        String pasteItalian = "Италиански стил";

        By sizeField = By.xpath("//select[@id='pizza-size']");
        Select sizeDropdown = new Select(driver.findElement(sizeField));
        sizeDropdown.selectByVisibleText(jumboPizza);

        By pasteField = By.xpath("//select[@id='pizza-pan']");
        Select pastedropDown = new Select(driver.findElement(pasteField));
        pastedropDown.selectByVisibleText(pasteItalian);

        WebElement addBtn = driver.findElement(By.xpath("//div[@class='Add_btn button green-btn text-center margin-top-25 col-xs-12 active']"));
        addBtn.click();

        By loginPopUp = By.xpath("//div[@class='popup-content clearfix']");

        //check if a pop-up with a Login/Register options is displayed
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(loginPopUp)));
        Assert.assertTrue(driver.findElement(loginPopUp).isDisplayed());

    }

    @Test
    public void userLogin() {

        String userEmail = "mivega7241@lodores.com";
        String password = "TestCase1234*";

        driver.get("https://www.dominos.bg/?gclid=EAIaIQobChMI6MXpnu3y-AIVRfDjBx0wmQz6EAAYASAAEgIfJfD_BwE");

        WebElement rejectOffer = driver.findElement(By.xpath("//button[@class='align-right secondary slidedown-button']"));
        wait.until(ExpectedConditions.visibilityOf(rejectOffer));
        rejectOffer.click();

        WebElement orderNow = driver.findElement(By.xpath("//a[text()='ПОРЪЧАЙ СЕГА']"));
        orderNow.click();

        WebElement popup = driver.findElement(By.xpath("//div[@class='popup-content clearfix']"));
        wait.until(ExpectedConditions.visibilityOf(popup));

        WebElement placeHolderEmail = driver.findElement(By.xpath("//input[@id='login-email']"));
        placeHolderEmail.click();
        placeHolderEmail.click();
        placeHolderEmail.sendKeys(userEmail);

        WebElement placeHolderPassword = driver.findElement(By.xpath("//input[@name='password']"));
        placeHolderPassword.click();
        placeHolderPassword.clear();
        placeHolderPassword.sendKeys(password);

        WebElement loginBtn = driver.findElement(By.xpath("//div[text()='Вход']"));
        loginBtn.click();

        //Check if "Изберете начин на поръчка" is displayed
        By orderChoicePopUp = By.xpath("//div[@class='popup-content clearfix']");
        Assert.assertTrue(driver.findElement(orderChoicePopUp).isDisplayed());
    }

    @Test
    public void userLogout(){

        String userEmail = "mivega7241@lodores.com";
        String password = "TestCase1234*";

        driver.get("https://www.dominos.bg/?gclid=EAIaIQobChMI6MXpnu3y-AIVRfDjBx0wmQz6EAAYASAAEgIfJfD_BwE");

        WebElement rejectOffer = driver.findElement(By.xpath("//button[@class='align-right secondary slidedown-button']"));
        wait.until(ExpectedConditions.visibilityOf(rejectOffer));
        rejectOffer.click();

        WebElement orderNow = driver.findElement(By.xpath("//a[text()='ПОРЪЧАЙ СЕГА']"));
        orderNow.click();

        WebElement popup = driver.findElement(By.xpath("//div[@class='popup-content clearfix']"));
        wait.until(ExpectedConditions.visibilityOf(popup));

        WebElement placeHolderEmail = driver.findElement(By.xpath("//input[@id='login-email']"));
        placeHolderEmail.click();
        placeHolderEmail.click();
        placeHolderEmail.sendKeys(userEmail);

        WebElement placeHolderPassword = driver.findElement(By.xpath("//input[@name='password']"));
        placeHolderPassword.click();
        placeHolderPassword.clear();
        placeHolderPassword.sendKeys(password);

        WebElement loginBtn = driver.findElement(By.xpath("//div[text()='Вход']"));
        loginBtn.click();

        WebElement closeIcon = driver.findElement(By.xpath("//img[@class='modal-close img-circle blue-btn cursor-pointer']"));
        closeIcon.click();

        WebElement profileIcon = driver.findElement(By.xpath("//div[@id='account-icon']"));
        profileIcon.click();

        WebElement logoutBtn = driver.findElement(By.xpath("//a[text()='ИЗХОД']"));
        logoutBtn.click();

        String url = driver.getCurrentUrl();
        Assert.assertTrue(url.contains("https://www.dominos.bg/"));
    }

//    @Test
//    public void userRegister(){
//
//        driver.get("https://www.dominos.bg/?gclid=EAIaIQobChMI6MXpnu3y-AIVRfDjBx0wmQz6EAAYASAAEgIfJfD_BwE");
//
//        WebElement rejectOffer = driver.findElement(By.xpath("//button[@class='align-right secondary slidedown-button']"));
//        wait.until(ExpectedConditions.visibilityOf(rejectOffer));
//        rejectOffer.click();
//
//        WebElement orderNow = driver.findElement(By.xpath("//a[text()='ПОРЪЧАЙ СЕГА']"));
//        orderNow.click();
//
//        WebElement popup = driver.findElement(By.xpath("//div[@class='popup-content clearfix']"));
//        wait.until(ExpectedConditions.visibilityOf(popup));
//
//        WebElement registerBtn = driver.findElement(By.xpath("//div[@class='button blue-btn margin-top-25 margin-bottom-15 text-center col-xs-6 col-xs-offset-3 register']"));
//        registerBtn.click();
//
//        WebElement regPage = driver.findElement(By.xpath("//div[@class='box white-bg border-radius padding-20 clearfix']"));
//        Assert.assertTrue(regPage.isDisplayed());
//
//        WebElement firstName = driver.findElement(By.xpath("//input[@id='f_name']"));
//        WebElement lastName = driver.findElement(By.xpath("//input[@id='l_name']"));
//        WebElement email = driver.findElement(By.xpath("//input[@id='email']"));
//        WebElement password = driver.findElement(By.xpath("//input[@id='pass']"));
//        WebElement confPassword = driver.findElement(By.xpath("//input[@id='conf_pass']"));
//
//        //have a question here -> can't select the exact xpath of the checkbox
////        WebElement privacyPolicy = driver.findElement(By.xpath("(//div[@class='consent'])[2]"));
//        By checkbox = By.xpath("//div[@id='invoice-wrapper']/input");
//        WebElement privacyPolicy = driver.findElements(checkbox).get(0);
//
//        firstName.clear();
//        firstName.sendKeys("Test1");
//
//        lastName.clear();
//        lastName.sendKeys("Case2");
//
//        email.clear();
//        email.sendKeys("kokoka5614@satedly.com");
//
//        password.clear();
//        password.sendKeys("TestCase4321*");
//
//        confPassword.clear();
//        confPassword.sendKeys("TestCase4321*");
//
//    }
}