import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class SurveyTasks {
    public WebDriverWait wait;
    public WebDriver driver;
    @BeforeTest
    public void demoqaload(){
        ChromeOptions options= new ChromeOptions();
        options.addArguments("--incognito");
        driver = new ChromeDriver(options);
        driver.get("https://demoqa.com");
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }
    @Test
    public void widgetclick(){
       //click on the Widgets card from the home page
        WebElement widgetcard = driver.findElement(By.xpath("//*[text()='Widgets']"));
        widgetcard.click();
        new WebDriverWait(driver, Duration.ofSeconds(2));

        //Click on the progress bar from the left menu
        WebElement progressbar = driver.findElement(By.xpath("//*[text()='Progress Bar']"));
        progressbar.click();
        Assert.assertTrue(progressbar.isDisplayed() && progressbar.isEnabled(), "Progress Bar page is not loaded and is not active");
        new WebDriverWait(driver, Duration.ofSeconds(2));

        //Click Start button the Progress Bar Page
        WebElement startbutton = driver.findElement(By.id("startStopButton"));
        startbutton.click();

        //Wait for the Reset button to be displayed
        WebElement resetbutton = driver.findElement(By.id("resetButton"));
       wait.until(resetbutton.isDisplayed());
       Assert.assertTrue(resetbutton.isDisplayed() && resetbutton.isEnabled(), "Progress Bar page is not loaded and is not active");
List<WebElement> elements = driver.findElements(By.id(""));



    }
}
