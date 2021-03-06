package lesson5;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import com.codeborne.selenide.Condition;

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
import io.qameta.allure.Step;
import io.qameta.allure.Story;

public class AnnotatedStepsTest {

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
        BaseSteps steps = new BaseSteps();

        steps.openBaseUrl();
        steps.searchRepository(REPOSITORY);
        steps.openRepository(REPOSITORY);
        steps.checkElementDisplay("Issues");
    }

    public class BaseSteps {
        @Step("Open Base URL")
        public void openBaseUrl() {
            open("https://github.com");
        }

        @Step("Search repository {repositoryName}")
        public void searchRepository(String repositoryName) {
            $(".header-search-input").as("Search field").setValue(repositoryName).submit();
        }

        @Step("Open {repositoryName} repository in search results")
        public void openRepository(String repositoryName) {
            $(By.linkText(repositoryName)).click();
        }

        @Step("Make sure '{elementName}' element is displayed in left-side menu")
        public void checkElementDisplay(String elementName) {
            $(withText(elementName)).shouldBe(Condition.visible);
        }
    }
}
