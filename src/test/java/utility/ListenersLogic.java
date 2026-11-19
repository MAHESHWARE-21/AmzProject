package utility;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListenersLogic implements ITestListener {

    public static WebDriver driver;

    public void captureScreenshot(ITestResult result, String status) {

        WebDriver driver = BaseTest.driver;

        if (driver == null) {
            System.out.println("Driver is null. Screenshot cannot be captured.");
            return;
        }

        try {

            TakesScreenshot ts = (TakesScreenshot) driver;

            File source = ts.getScreenshotAs(OutputType.FILE);

            File folder = new File(
                "test-output/Screenshot/" + status
            );

            if (!folder.exists()) {
                folder.mkdirs();
            }

            File destination = new File(
                folder, result.getName() + ".png"
            );

            FileHandler.copy(source, destination);

            System.out.println("Screenshot saved: " +
                    destination.getAbsolutePath());

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        System.out.println("Test Case Passed");

        captureScreenshot(result, "Pass");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        System.out.println("Test Case Failed");

        captureScreenshot(result, "Fail");
    }
}
