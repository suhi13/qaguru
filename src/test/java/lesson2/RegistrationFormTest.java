package lesson2;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

        List<String> expectedDataList = new ArrayList<>();
        expectedDataList.add(String.format("%s %s", firstName, lastName));
        expectedDataList.add(emailAddress);
        expectedDataList.add(gender);
        expectedDataList.add(mobile);
        expectedDataList.add(String.format("%s %s,%s", dateOfBirth, monthOfBirth, yearOfBirth));
        expectedDataList.add(subject);
        expectedDataList.add(hobby);
        expectedDataList.add(picture);
        expectedDataList.add(address);
        expectedDataList.add(String.format("%s %s", state, city));

        open("https://demoqa.com/automation-practice-form");
        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(emailAddress);
        $(byText(gender)).click();
        $("#userNumber").setValue(mobile);
        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOptionByValue("4");
        $(".react-datepicker__year-select").click();
        $(".react-datepicker__year-select").selectOptionByValue("2003");
        $x(".//div[contains(@class,'react-datepicker__week')]/div[contains(text(),'16')]").click();
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

        ElementsCollection userDataElementsList = $$(By.xpath(".//tr/td[2]"));
        List<String> userDataInModal = userDataElementsList.texts();

        $(By.className("modal-open")).shouldBe(visible);
        assertEquals(expectedDataList, userDataInModal, "Incorrect user data was displayed in " +
                "registration modal");

        $("#closeLargeModal").click();
    }
}
