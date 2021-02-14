package lesson4;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.github.javafaker.Faker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        List<String> expectedDataList = new ArrayList<>();
        expectedDataList.add(String.format("%s %s", firstName, lastName));
        expectedDataList.add(emailAddress);
        expectedDataList.add(gender);
        expectedDataList.add(mobile);
        expectedDataList.add(new SimpleDateFormat("dd MMMM,yyyy").format(date));
        expectedDataList.add(subject);
        expectedDataList.add(hobby);
        expectedDataList.add(picture);
        expectedDataList.add(address);
        expectedDataList.add(String.format("%s %s", state, city));

        open("https://demoqa.com/automation-practice-form");
        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(emailAddress);
        $(By.xpath(String.format(".//label[text()='%s']", gender))).click();
        $("#userNumber").setValue(mobile);
        $("#dateOfBirthInput").sendKeys(Keys.CONTROL + "a");
        $("#dateOfBirthInput").sendKeys(dateOfBirth + Keys.ENTER);
        $(By.xpath(".//div[@id='subjectsContainer']")).click();
        $(By.xpath(".//input[@id='subjectsInput']")).setValue(subject)
                                                    .pressEnter();
        $("#hobbies-checkbox-2").parent()
                                .click();
        $("#currentAddress").setValue(address);
        $(By.xpath(".//div[text()='Select State']")).scrollIntoView(true)
                                                    .click();
        $(By.xpath(".//div[contains(@id,'1')]")).click();
        $(By.xpath(".//div[text()='Select City']")).click();
        $(By.xpath(".//div[contains(@id,'2')]")).click();

        File cv = new File(String.format("src/test/resources/%s", picture));
        $("#uploadPicture").uploadFile(cv);
        $("#submit").click();

        ElementsCollection userDataElementsList = $$(By.xpath(".//tr/td[2]"));
        List<String> userDataInModal = userDataElementsList.texts();

        $(By.className("modal-open")).shouldBe(visible);

        Assertions.assertLinesMatch(expectedDataList, userDataInModal, "Incorrect userdata was displayed in " +
                "registration modal");

        $("#closeLargeModal").click();
    }
}
