package lesson5;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Condition.visible;
import static io.qameta.allure.Allure.step;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import io.qameta.allure.Allure;

public class LambdaStepsTest {

    private final static String BASE_URL = "https://github.com";
    private final static String REPOSITORY = "suhi13/y_qaguru";

    @Test
    public void lambdaStepsTest() {
        Allure.feature("Navigation");
        Allure.story("User can see menu elements");
        Allure.parameter("Repository", REPOSITORY);

        step("Open Base URL", () -> {
            open(BASE_URL);
        });

        step("Search repository", () -> {
            $(".header-search-input").as("Search field").setValue(REPOSITORY).submit();
        });

        step("Open repository in search results", () -> {
            $(By.linkText(REPOSITORY)).click();
        });

        step("Make sure 'Issues' element is displayed in left-side menu", () -> {
            $(withText("Issues")).shouldBe(visible);
        });
    }
}
