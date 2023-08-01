import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MetalShopTest {

    static WebDriver driver;

    @BeforeAll
    public static void setUp(){
        ChromeOptions opt = new ChromeOptions();
        opt.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(opt);
        driver.get("http://serwer169007.lh.pl/autoinstalator/serwer169007.lh.pl/wordpress10772/");
        driver.manage().window().maximize();
    }

    @Test
    public void emptyUsername(){
        driver.findElement(By.xpath("//*[@id=\"menu-item-125\"]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("12345@");
        driver.findElement(By.xpath("//*[@id=\"post-9\"]/div/div/form/p[3]/button")).click();
        Assertions.assertEquals("Błąd: Nazwa użytkownika jest wymagana.", driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/ul/li")).getText());

    }

    @Test
    public void emptyPassword(){
        driver.findElement(By.xpath("//*[@id=\"menu-item-125\"]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"username\"]")).sendKeys("qwerty@007");
        driver.findElement(By.xpath("//*[@id=\"post-9\"]/div/div/form/p[3]/button")).click();
        Assertions.assertEquals("Błąd: pole hasła jest puste.", driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/ul/li")).getText());
    }

    @Test
    public void registerNewUser() {

        Faker faker = new Faker();
        String fakeName = faker.name().username();
        String email = fakeName + faker.random().nextInt(1000) + "@gmail.com";

        driver.findElement(By.xpath("//*[@id=\"menu-item-146\"]/a")).click(); //register
        driver.findElement(By.xpath("//*[@id=\"user_login\"]")).sendKeys(fakeName);
        driver.findElement(By.xpath("//*[@id=\"user_email\"]")).sendKeys(email);
        driver.findElement(By.xpath("//*[@id=\"user_pass\"]")).sendKeys("12345@@##");
        driver.findElement(By.xpath("//*[@id=\"user_confirm_password\"]")).sendKeys("12345@@##");
        driver.findElement(By.xpath("//*[@id=\"user-registration-form-118\"]/form/div[2]/button")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"ur-submit-message-node\"]/ul"))).isDisplayed();
        Assertions.assertEquals("User successfully registered.", driver.findElement(By.xpath("//*[@id=\"ur-submit-message-node\"]/ul")).getText());

    }
    @Test
    public void mainPageAndLoginPageContainNameAndSearchField(){

        Assertions.assertEquals("Softie Metal Shop", driver.findElement(By.xpath("//*[@id=\"masthead\"]/div[1]/div[1]/div/a")).getText());
        Assertions.assertTrue(driver.findElement(By.xpath("//*[@id=\"woocommerce-product-search-field-0\"]")).isDisplayed());
        driver.get("http://serwer169007.lh.pl/autoinstalator/serwer169007.lh.pl/wordpress10772/moje-konto/");
        Assertions.assertEquals("Softie Metal Shop", driver.findElement(By.xpath("//*[@id=\"masthead\"]/div[1]/div[1]/div/a")).getText());
        Assertions.assertTrue(driver.findElement(By.xpath("//*[@id=\"woocommerce-product-search-field-0\"]")).isDisplayed());
    }

    @Test
    public void changeMainPageToContactPage(){

        driver.findElement(By.xpath("//*[@id=\"menu-item-132\"]/a")).click();
        Assertions.assertEquals("Kontakt", driver.findElement(By.xpath("//*[@id=\"menu-item-132\"]/a")).getText());
    }

    @Test
    public void changeLoginPageToMainPage(){

        driver.findElement(By.xpath("//*[@id=\"masthead\"]/div[1]/div[1]/div/a")).click();
        Assertions.assertEquals("Sklep",driver.findElement(By.xpath("//*[@id=\"main\"]/header/h1")).getText());
    }

    @Test
    public void checkContactPageErrorMessage() {

        Faker faker = new Faker();
        String fakeName = faker.name().username();
        String email = fakeName + faker.random().nextInt(1000) + "@gmail.com";

        driver.get("http://serwer169007.lh.pl/autoinstalator/serwer169007.lh.pl/wordpress10772/kontakt/");
        driver.findElement(By.xpath("//*[@id=\"wpcf7-f128-p129-o1\"]/form/p[1]/label/span/input")).sendKeys(fakeName);
        driver.findElement(By.xpath("//*[@id=\"wpcf7-f128-p129-o1\"]/form/p[2]/label/span/input")).sendKeys(email);
        driver.findElement(By.xpath("//*[@id=\"wpcf7-f128-p129-o1\"]/form/p[3]/label/span/input")).sendKeys("question");
        driver.findElement(By.xpath("//*[@id=\"wpcf7-f128-p129-o1\"]/form/p[4]/label/span/textarea")).sendKeys("I have simple question");
        driver.findElement(By.xpath("//*[@id=\"wpcf7-f128-p129-o1\"]/form/p[5]/input")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"wpcf7-f128-p129-o1\"]/form/div[2]"))).isDisplayed();
        Assertions.assertEquals("Wystąpił problem z wysłaniem twojej wiadomości. Spróbuj ponownie później.", driver.findElement(By.xpath("//*[@id=\"wpcf7-f128-p129-o1\"]/form/div[2]")).getText());
    }

    @Test
    @Order(1)
        public void buyOneItem() {
        driver.get("http://serwer169007.lh.pl/autoinstalator/serwer169007.lh.pl/wordpress10772/");
        Assertions.assertEquals("0,00 zł", driver.findElement(By.xpath("//*[@id=\"site-header-cart\"]/li[1]/a/span[1]")).getText());
        driver.findElement(By.xpath("//html/body/div/div/div/div[2]/main/ul/li[3]/a[2]")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"main\"]/ul/li[3]/a[3]"))).isDisplayed();
        driver.findElement(By.xpath("//*[@id=\"main\"]/ul/li[3]/a[3]")).click(); //zobacz koszyk
        Assertions.assertEquals("Srebrna sztabka 500g",driver.findElement(By.xpath("//*[@id=\"post-7\"]/div/div/form/table/tbody/tr[1]/td[3]/a")).getText());

    }

    @Test
    public void buyAndRemoveOneItem() {
        driver.get("http://serwer169007.lh.pl/autoinstalator/serwer169007.lh.pl/wordpress10772/");
        driver.findElement(By.xpath("//html/body/div/div/div/div[2]/main/ul/li[3]/a[2]")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#main > ul > li.product.type-product.post-28.status-publish.last.instock.product_cat-srebro.product_cat-sztabki.has-post-thumbnail.shipping-taxable.purchasable.product-type-simple > a.added_to_cart.wc-forward"))).isDisplayed();
        driver.findElement(By.xpath("//*[@id=\"main\"]/ul/li[3]/a[3]")).click(); //zobacz koszyk
        driver.findElement(By.xpath("//*[@id=\"post-7\"]/div/div/form/table/tbody/tr[1]/td[1]/a")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#post-7 > div > div > div > p"))).isDisplayed();
        Assertions.assertEquals("Twój koszyk aktualnie jest pusty.", driver.findElement(By.xpath("//*[@id=\"post-7\"]/div/div/div/p")).getText());

    }
    @AfterAll
    public static void quit(){
        driver.quit();
    }
}
