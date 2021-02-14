package lesson3;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

public class GitHubSelenideTest {

    @Test
    public void checkJUnit5ExampleAvailabilityTest() {
        String sectionName = "SoftAssertions";

        open("https://github.com/selenide/selenide");
        $(byText("Wiki")).click();
        $$(By.className("Box-row")).texts()
                                   .contains(sectionName);
        $(byText(sectionName)).click();
        $("#wiki-body").shouldHave(text("JUnit5"));
    }
}