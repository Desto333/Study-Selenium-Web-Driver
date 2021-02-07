import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static java.lang.String.*;
import static org.openqa.selenium.By.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class SignUpPage {
    WebDriver driver;

    public SignUpPage(WebDriver driver) {
        this.driver = driver;
    }

    private final By emailField = cssSelector("input#email");
    private final By confirmEmailField = cssSelector("input#confirm");
    private final By passwordField = cssSelector("input#password");
    private final By nameField = cssSelector("input#displayname");
    private final By monthDropDown = cssSelector("select#month");
    private final String monthDropDownOption = "//select[@id='month']/option[contains(text(),'%s')]";
    private final By dayField = cssSelector("input#day");
    private final By yearField = cssSelector("input#year");
    private final String genderOption = "//label/span[text()='%s']";
    private final String shareCheckbox = "//span[contains(text(),'Share my registration')]";
    private final By signUpButton = xpath("//div[starts-with(@class,'SignupButton')]/button");
    private final String errorLabel = "//div[contains(@class,'InputErrorMessage') and string-length(text()>0)]";
    private final String errorLabelByText = "//div[starts-with(@class,'FormHelpText') and contains(text(),\"%s\")]";
    private final By cookiesMessageCloseButtonXpath = xpath("//div[@id='onetrust-close-btn-container']/button");

    public SignUpPage closeCookiesWarning() {
        if (driver.findElement(cookiesMessageCloseButtonXpath).isDisplayed()) {
            driver.findElement(cookiesMessageCloseButtonXpath).click();
        }
        return this;
    }

    public SignUpPage typeEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
        return this;
    }

    public SignUpPage typeConfirmEmail(String email) {
        driver.findElement(confirmEmailField).sendKeys(email);
        return this;
    }

    public SignUpPage typePassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
        return this;
    }

    public SignUpPage typeName(String name) {
        driver.findElement(nameField).sendKeys(name);
        return this;
    }

    public SignUpPage setMonth(String month) {
        driver.findElement(monthDropDown).click();
        new WebDriverWait(driver, 5)
                .until(visibilityOfElementLocated(xpath(format(monthDropDownOption, month)))).click();
        return this;
    }

    public SignUpPage typeDay(String day) {
        driver.findElement(dayField).sendKeys(day);
        return this;
    }

    public SignUpPage typeYear(String year) {
        driver.findElement(yearField).sendKeys(year);
        return this;
    }

    public SignUpPage setGender(String gender) {
        driver.findElement(new ByXPath(format(genderOption, gender))).click();
        return this;
    }

    public SignUpPage setShare(boolean value) {
        WebElement checkbox = driver.findElement(new ByXPath(shareCheckbox));
        if(!checkbox.isSelected() == value) {
            checkbox.click();
        }
        return this;
    }

    public void pressSignUpButton() {
        driver.findElement(signUpButton).click();
    }

    public List<WebElement> getErrors() {
        return driver.findElements(new ByXPath(errorLabel));
    }

    public WebElement getErrorByText(String errorText) {
        return driver.findElement(new ByXPath(format(errorLabelByText, errorText)));
    }

    public WebElement getErrorByNumber(int number) {
        return getErrors().get(number - 1);
    }

    public boolean isErrorVisible(String errorMessage) {
        return driver.findElements(new ByXPath(format(errorLabelByText, errorMessage))).size() > 0
                && driver.findElements(new ByXPath(format(errorLabelByText, errorMessage))).get(0).isDisplayed();
    }
}
