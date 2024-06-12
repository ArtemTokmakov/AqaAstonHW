import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class MtsTests {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void SetUp() {
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\TemaTokmakov\\IdeaProjects\\AqaAstonHW\\src\\main\\resources\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("http://mts.by");

        WebElement acceptCookies = driver.findElement(By.xpath("//button[@id='cookie-agree']"));
        acceptCookies.click();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Проверка названия в блоке 'Онлайн пополнение без комиссии'")
    void isTitleTest() {

        WebElement blockTitle = driver.findElement(
                By.xpath("//div[@class='pay__wrapper']/h2"));
        String expectedTitle = "Онлайн пополнение\nбез комиссии";
        assertEquals(expectedTitle, blockTitle.getText().trim());
    }

    @Test
    @DisplayName("Проверка платежных логотипов в блоке 'Онлайн пополнение без комиссии'")
    void payLogosTest() {

        List<WebElement> paymentLogos = driver.findElements(
                By.xpath("//div[@class='pay__partners']/ul/li"));
        assertEquals(5, paymentLogos.size(), "На сайте должно быть 5 логотипов платёжных систем");
    }

    @Test
    @DisplayName("Проверка ссылки 'Подробнее о снрвисе' в блоке 'Онлайн пополнение без комиссии'")
    void checkLinkTest(){
        WebElement link = driver.findElement(
                By.xpath("//a[text()='Подробнее о сервисе']"));
        link.click();

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("help/poryadok-oplaty-i-bezopasnost-internet-platezhey/"), "Ссылка не работает");
    }


    @Test
    @DisplayName("Проверка кнопки 'Продолжить' в меню онлайн пополнения")
    void continueButtonTest(){

        WebElement phoneNumber = driver.findElement(
                By.xpath("//input[@id='connection-phone']"));
        phoneNumber.click();
        phoneNumber.sendKeys("297777777");

        WebElement amountButton = driver.findElement(
                By.xpath("//input[@id='connection-sum']"));
        amountButton.click();
        amountButton.sendKeys("50");

        WebElement continueButton = driver.findElement(
                By.xpath("//button[text()='Продолжить']"));
        continueButton.click();

        WebElement iframe = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div.bepaid-app iframe")));
        driver.switchTo().frame(iframe);

        List<WebElement> yandexPayButton = driver.findElements(
                By.xpath("//button[@aria-label='Yandex Pay']"));
        assertEquals(1, yandexPayButton.size(), "Отсутствует кнопка 'Yandex Pay', кнопка 'Продолжить' не работает");
    }
}
// Для Анастасии: не пойму в чем причина, последний тест то проходит, то 2-4 раза не проходит, то опять все ок.


