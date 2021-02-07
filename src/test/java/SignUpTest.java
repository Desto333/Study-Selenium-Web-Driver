import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class SignUpTest {
    WebDriver driver;
    SignUpPage page;

    @Before
    public void setUp() throws MalformedURLException {
        System.setProperty("webdriver.chrome.driver", "C:\\Programs\\Selenium\\chromedriver.exe");    // для запуска SeleniumGrid строки 23-24 вместо 21-22
        driver = new ChromeDriver();
//        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
//        driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://www.spotify.com/us/signup/");
        driver.manage().window().maximize();
        page = new SignUpPage(driver);
        page.closeCookiesWarning();
    }

    @Test
    public void typeInvalidYear() {
        page.setMonth("May")
                .typeDay("10")
                .typeYear("85")
                .setShare(true)
                .pressSignUpButton();
        assertEquals(7, page.getErrors().size());
        assertTrue(page.getErrorByText("Enter a valid year.").isDisplayed());
    }

    @Test
    public void confirmInvalidEmail() {
        page.typeEmail("test@mail.test")
                .typeConfirmEmail("wrong@mail.test")
                .typeName("TestName")
                .setGender("Male")
                .pressSignUpButton();
        assertEquals(6, page.getErrors().size());
        assertTrue(page.getErrorByText("The email addresses don't match.").isDisplayed());
    }

    @Test
    public void signUpWithEmptyPassword() {
        page.typeEmail("test@mail.test")
                .typeConfirmEmail("test@mail.test")
                .typeName("TestName")
                .pressSignUpButton();
        assertEquals(6, page.getErrors().size());
        assertTrue(page.getErrorByText("You need to enter a password.").isDisplayed());
    }

    @Test
    public void typeInvalidValues() {
        page.typeEmail("testmail@gtrg.tv")
                .typeConfirmEmail("wrong@test.mail")
                .typePassword("")
                .typeName("")
                .typeDay("")
                .typeYear("")
                .setShare(true)
                .pressSignUpButton();
        assertEquals(8, page.getErrors().size());
        assertEquals("Confirm you're not a robot.", page.getErrorByNumber(8).getText());
    }

    @After
    public void tearDown() {
        driver.quit();
        driver = null;
    }
}
