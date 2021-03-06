package lesson5;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.selenide.AllureSelenide;

public class PureSelenideTest {

    private final static String BASE_URL = "https://github.com";
    private final static String REPOSITORY = "suhi13/y_qaguru";

    @Test
    @Owner("Yuliia Sukhova")
    @Tags({@Tag("web"), @Tag("Critical")})
    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "Base URL", value = BASE_URL)
    @Story("User can see menu elements")
    @Feature("Navigation")
    @DisplayName("Check 'Issue' element display")
    public void checkIssueDisplayTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        open(BASE_URL);
        $(".header-search-input").as("Search field").setValue(REPOSITORY).submit();
        $(By.linkText(REPOSITORY)).click();

        $(withText("Issues")).shouldBe(Condition.visible);
    }
}
