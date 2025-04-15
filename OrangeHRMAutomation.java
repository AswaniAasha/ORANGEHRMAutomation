
package com.orangehrm.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OrangeHRMAutomation {
    WebDriver driver;
    String baseUrl = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(baseUrl);
    }

    @Test(priority = 1)
    public void login() throws InterruptedException {
        driver.findElement(By.name("username")).sendKeys("Admin");
        driver.findElement(By.name("password")).sendKeys("admin123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        Thread.sleep(2000);
    }

    @Test(priority = 2)
    public void navigateToPIM() {
        WebElement pimMenu = driver.findElement(By.xpath("//span[text()='PIM']"));
        new Actions(driver).moveToElement(pimMenu).click().perform();
    }

    @Test(priority = 3)
    public void addEmployees() throws InterruptedException {
        String[][] employees = {{"Aswani", "K"}, {"Alwin", "P"}, {"Salini", "M"}, {"Babu", "S"}};
        for (String[] emp : employees) {
            driver.findElement(By.xpath("//button[text()='Add']")).click();
            Thread.sleep(1000);
            driver.findElement(By.name("firstName")).sendKeys(emp[0]);
            driver.findElement(By.name("lastName")).sendKeys(emp[1]);
            driver.findElement(By.xpath("//button[@type='submit']")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//a[text()='Employee List']")).click();
            Thread.sleep(1000);
        }
    }

    @Test(priority = 4)
    public void verifyEmployees() throws InterruptedException {
        String[] names = {"Aswani K", "Alwin P", "salini M", "Babu S"};
        for (String name : names) {
            boolean found = false;
            List<WebElement> rows = driver.findElements(By.cssSelector(".oxd-table-body .oxd-table-card"));
            for (WebElement row : rows) {
                if (row.getText().contains(name)) {
                    System.out.println(name + " Verified");
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println(name + " Not Found");
            }
        }
    }

    @Test(priority = 5)
    public void logout() throws InterruptedException {
        driver.findElement(By.cssSelector(".oxd-userdropdown-name"))
                .click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//a[text()='Logout']"))
                .click();
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
