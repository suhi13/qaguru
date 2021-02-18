package lesson2;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

import com.codeborne.selenide.Configuration;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.io.File;

public class RegistrationFormTest {

    @BeforeAll
    static void setup() {
        Configuration.startMaximized = true;
    }

    @Test
    public void checkRegistrationTest() {
        String firstName = "John";
        String lastName = "Dou";
        String emailAddress = String.format("johndou%s@yopmail.com", RandomStringUtils.randomNumeric(4));
        String gender = "Male";
        String mobile = RandomStringUtils.randomNumeric(10);
        String dateOfBirth = "16";
        String monthOfBirth = "May";
        String yearOfBirth = "2003";
        String subject = "Arts";
        String hobby = "Reading";
        String picture = "test-img.png";
        String address = "Green St. 24/254, NY, US";
        String state = "Uttar Pradesh";
        String city = "Merrut";

        open("https://demoqa.com/automation-practice-form");
        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(emailAddress);
        $(byText(gender)).click();
        $("#userNumber").setValue(mobile);
        $("#dateOfBirthInput").sendKeys(Keys.CONTROL + "a");
        $("#dateOfBirthInput").sendKeys(String.format("%s %s,%s", dateOfBirth, monthOfBirth, yearOfBirth)
                + Keys.ENTER);
        $("#subjectsInput").click();
        $("#subjectsInput").setValue(subject).pressEnter();
        $("#hobbies-checkbox-2").parent().click();
        $("#currentAddress").setValue(address);
        $(byText("Select State")).scrollIntoView(true).click();
        $x(".//div[contains(@id,'1')]").click();
        $(byText("Select City")).click();
        $x(".//div[contains(@id,'2')]").click();
        $("#uploadPicture").uploadFile(new File(String.format("src/test/resources/%s", picture)));
        $("#submit").click();

        $(".modal-title").shouldHave(text("Thanks for submitting the form"));
        $$(".table-responsive tr").filterBy(text("Student name")).shouldHave(texts(String.format("%s %s", firstName,
                lastName)));
        $$(".table-responsive tr").filterBy(text("Student email")).shouldHave(texts(emailAddress));
        $$(".table-responsive tr").filterBy(text("Gender")).shouldHave(texts(gender));
        $$(".table-responsive tr").filterBy(text("Mobile")).shouldHave(texts(mobile));
        $$(".table-responsive tr").filterBy(text("Date of birth")).shouldHave(texts(String.format("%s %s,%s",
                dateOfBirth, monthOfBirth, yearOfBirth)));
        $$(".table-responsive tr").filterBy(text("Subjects")).shouldHave(texts(subject));
        $$(".table-responsive tr").filterBy(text("Hobbies")).shouldHave(texts(hobby));
        $$(".table-responsive tr").filterBy(text("Picture")).shouldHave(texts(picture));
        $$(".table-responsive tr").filterBy(text("Address")).shouldHave(texts(address));
        $$(".table-responsive tr").filterBy(text("State and City")).shouldHave(texts(String.format("%s %s", state,
                city)));

        $("#closeLargeModal").click();
    }
}
