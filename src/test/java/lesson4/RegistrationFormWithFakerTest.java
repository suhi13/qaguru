package lesson4;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegistrationFormWithFakerTest {

    @BeforeAll
    static void setup() {
        Configuration.startMaximized = true;
    }

    @Test
    public void checkRegistrationWithFakerTest() {
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String emailAddress = faker.internet().emailAddress();
        String gender = "Male";
        String mobile = faker.phoneNumber().subscriberNumber(10);
        Date date = faker.date().birthday(18, 55);
        String dateOfBirth = new SimpleDateFormat("d MMM yyyy").format(date);
        String subject = "Arts";
        String hobby = "Reading";
        String picture = "test-img.png";
        String address = faker.address().fullAddress();
        String state = "Uttar Pradesh";
        String city = "Merrut";
        File cv = new File(String.format("src/test/resources/%s", picture));

        open("https://demoqa.com/automation-practice-form");
        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(emailAddress);
        $(byText(gender)).click();
        $("#userNumber").setValue(mobile);
        $("#dateOfBirthInput").sendKeys(Keys.CONTROL + "a");
        $("#dateOfBirthInput").sendKeys(dateOfBirth + Keys.ENTER);
        $(By.xpath(".//div[@id='subjectsContainer']")).click();
        $(By.xpath(".//input[@id='subjectsInput']")).setValue(subject).pressEnter();
        $("#hobbies-checkbox-2").parent().click();
        $("#currentAddress").setValue(address);
        $(By.xpath(".//div[text()='Select State']")).scrollIntoView(true).click();
        $(By.xpath(".//div[contains(@id,'1')]")).click();
        $(By.xpath(".//div[text()='Select City']")).click();
        $(By.xpath(".//div[contains(@id,'2')]")).click();
        $("#uploadPicture").uploadFile(cv);
        $("#submit").click();

        $(".modal-title").shouldHave(text("Thanks for submitting the form"));
        $$(".table-responsive tr").filterBy(text("Student name")).shouldHave(texts(firstName + " " + lastName));
        $$(".table-responsive tr").filterBy(text("Student email")).shouldHave(texts(emailAddress));
        $$(".table-responsive tr").filterBy(text("Gender")).shouldHave(texts(gender));
        $$(".table-responsive tr").filterBy(text("Mobile")).shouldHave(texts(mobile));
        $$(".table-responsive tr").filterBy(text("Date of birth"))
                                  .shouldHave(texts(new SimpleDateFormat("dd MMMM,yyyy").format(date)));
        $$(".table-responsive tr").filterBy(text("Subjects")).shouldHave(texts(subject));
        $$(".table-responsive tr").filterBy(text("Hobbies")).shouldHave(texts(hobby));
        $$(".table-responsive tr").filterBy(text("Picture")).shouldHave(texts(picture));
        $$(".table-responsive tr").filterBy(text("Address")).shouldHave(texts(address));
        $$(".table-responsive tr").filterBy(text("State and City")).shouldHave(texts(state + " " + city));
        $("#closeLargeModal").click();
    }
}
