package com.saucelabs.example.features;

import com.saucelabs.example.pages.InventoryPage;
import com.saucelabs.example.pages.LoginPage;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class LoginFeatures
{
    private final static String userName = System.getenv("SAUCE_USERNAME");
    private final static String accessKey = System.getenv("SAUCE_ACCESS_KEY");
//        private final static String platformName = "Android";
//        private final static String platformVersion = "9";
//        private final static String deviceName = "Google_Pixel_3_POC150";
    private final static String platformName = "iOS";
    private final static String platformVersion = "12.1.4";
    private final static String deviceName = "iPhone_XS_POC166";

    @Test
    public void verifyValidUsersCanSignIn()
    throws MalformedURLException
    {
        URL url = new URL("https://" + userName + ":" + accessKey + "@ondemand.us-west-1.saucelabs.com/wd/hub");

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", platformName);
        caps.setCapability("platformVersion", platformVersion);
        caps.setCapability("name", "Verify Valid Users Can Sign In");

        AppiumDriver driver = new AppiumDriver(url, caps);

        LoginPage loginPage = new LoginPage(driver);
        InventoryPage inventoryPage = new InventoryPage(driver);

        loginPage.navigateTo(LoginPage.PAGE_URL);
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");

        loginPage.clickLogin();
        inventoryPage.waitForPageLoad();

        ((JavascriptExecutor) driver).executeScript("sauce:job-result=passed");
        driver.quit();
    }
}
