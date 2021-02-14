package lesson3;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.actions;
import static com.codeborne.selenide.Selenide.open;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DragAndDropTest {

    @Test
    public void dragAndDropTest() {
        open("https://the-internet.herokuapp.com/drag_and_drop");
        actions().dragAndDrop($("#column-a"), $("#column-b"))
                 .build()
                 .perform();
        Assertions.assertEquals($("#column-b").text(), "A", "Element A didn't replace Element B");
    }
}