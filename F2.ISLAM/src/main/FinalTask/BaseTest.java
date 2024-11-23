package FinalTask;

import org.awaitility.Awaitility;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;

import static org.testng.Assert.assertTrue;

public class BaseTest {

    public WebDriver driver;

    @BeforeTest
    public void setup() {
        HashMap<String, Object> chromePrefs = new HashMap<>();
        String RELATIVE_RESOURCE_PATH = "src/test/resources";
        chromePrefs.put("download.default_directory", new File(RELATIVE_RESOURCE_PATH).getAbsolutePath());
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);
        driver = new ChromeDriver(options);
        driver.get("https://wikipedia.org/"); // Navigate to Wikipedia
        // Explicitly wait for the page to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("searchInput")));
        // Assert that the main Wikipedia page is open and active by finding the search input element
        WebElement searchInput = driver.findElement(By.id("searchInput"));
        assertTrue(searchInput.isDisplayed() && searchInput.isEnabled(), "Main Wikipedia page is not open and active!");

    }

    @Test
    public void WikipediaTest() throws IOException {
        // Locate the search box
        WebElement searchBox = driver.findElement(By.id("searchInput"));

        // Enter search term
        searchBox.sendKeys("Albert Einstein");

        // Locate the search button
        WebElement searchButton = driver.findElement(By.xpath("//*[@id=\"search-form\"]//button"));

        // Click the search button
        searchButton.click();

        new WebDriverWait(driver, Duration.ofSeconds(5));

        // Click on the dropdown menu
        WebElement dropdownMenu = driver.findElement(By.id("vector-page-tools-dropdown-checkbox"));
        dropdownMenu.click();

        // Scroll to the "Download as PDF" element
        WebElement downloadPDF = driver.findElement(By.id("coll-download-as-rl"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", downloadPDF);
        new WebDriverWait(driver, Duration.ofSeconds(6)).until(ExpectedConditions.elementToBeClickable(downloadPDF));

        // Click on the "Download as PDF" element
        downloadPDF.click();

        final By FILE_DOWNLOAD_BUTTON = By.xpath("//*[@id=\"mw-content-text\"]//button");

        // Locate the download button on the download page
        WebElement downloadButton = driver.findElement(FILE_DOWNLOAD_BUTTON);

        // Click on the download button
        downloadButton.click();


        // This example waits for 10 seconds, adjust as needed
        new WebDriverWait(driver, Duration.ofSeconds(10));// Replace with a more robust waiting approach (e.g., Awatility)

        // Verify file download using custom polling
        Path downloadPath = Paths.get("src/test/resources");
        String expectedFileName = "Albert_Einstein.pdf";

        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < 30000) { // Adjust timeout as needed
            Path filePath = downloadPath.resolve(expectedFileName);
            try {
                if (Files.exists(filePath) && Files.size(filePath) > 0) {
                    assertTrue(true, "File downloaded successfully!");
                    break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                Thread.sleep(500); // Adjust polling interval
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}