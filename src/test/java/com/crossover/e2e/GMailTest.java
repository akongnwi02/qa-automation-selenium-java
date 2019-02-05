package com.crossover.e2e;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;
import junit.framework.TestCase;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class GMailTest extends TestCase {
    private WebDriver driver;
    private Properties properties = new Properties();

    public void setUp() throws Exception {
        
        properties.load(new FileReader(new File("src/test/resources/test.properties")));
        //Dont Change below line. Set this value in test.properties file incase you need to change it..
        System.setProperty("webdriver.chrome.driver",properties.getProperty("webdriver.chrome.driver") );
        driver = new ChromeDriver();
    }

    public void tearDown() throws Exception {
        //driver.quit();
    }

    /*
     * Please focus on completing the task
     * 
     */
    @Test
    public void testSendEmail() throws Exception {
        // emailSubject and emailbody to be used in this unit test.
        String emailSubject = properties.getProperty("email.subject");
        String emailBody = properties.getProperty("email.body");
        String emailLable = properties.getProperty("email.label");
        // username and password to be used for this unit test
        String password = properties.getProperty("password");
        String username = properties.getProperty("username");

        driver.get("https://mail.google.com/");
        Thread.sleep(2000);

        WebElement userElement = driver.findElement(By.id("identifierId"));
        userElement.sendKeys(username);

        driver.findElement(By.id("identifierNext")).click();
        Thread.sleep(2000);

        WebElement passwordElement = driver.findElement(By.name("password"));
        passwordElement.sendKeys(password);
        driver.findElement(By.id("passwordNext")).click();
        Thread.sleep(2000);

        WebElement composeElement = driver.findElement(By.xpath("//*[@role='button' and text()='Compose']"));
        composeElement.click();
        Thread.sleep(1500);

        driver.findElement(By.name("to")).clear();
        driver.findElement(By.name("to")).sendKeys(String.format("%s@gmail.com", username));

        driver.findElement(By.name("subjectbox")).clear();
        driver.findElement(By.name("subjectbox")).sendKeys(emailSubject);

        driver.findElement(By.xpath("//*[@role='textbox']")).clear();
        driver.findElement(By.xpath("//*[@role='textbox']")).sendKeys(emailBody);
        Thread.sleep(1000);

        driver.findElement(By.xpath("//*[@role='button' and @aria-label='More options']")).click();
        WebElement labelBtn =  driver.findElement(By.xpath("//*[@role='menuitem']//*[text()='Label']"));
        Actions action = new Actions(driver);
        action.moveToElement(labelBtn).perform();
        Thread.sleep(500);

        driver.findElement(By.xpath("//input[@ignoreesc='true']")).sendKeys(emailLable);
        driver.findElement(By.xpath("//input[@ignoreesc='true']")).sendKeys(Keys.ENTER);
        driver.findElement(By.xpath("//*[@role='button' and text()='Send']")).click();
        Thread.sleep(4000);

        driver.findElement(By.xpath("//*[@role='tab' and @aria-label='Social']")).click();
        Thread.sleep(200);

        driver.findElement(By.xpath(String.format("//*[@name='me' and @email='%s@gmail.com']/parent::span/parent::div/parent::td/parent::tr//span[@title='Not starred']", username))).click();
        driver.findElement(By.xpath(String.format("//*[@email='%s@gmail.com' and @data-hovercard-owner-id='23']", username))).click();
        Thread.sleep(500);
    }
}
