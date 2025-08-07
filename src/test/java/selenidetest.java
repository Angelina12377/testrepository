import org.junit.Test;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class selenidetest {

    @Test
    public void TestGoogle(){
        open("https://www.google.com/");
        $x("//input[@name='q']").setValue("Selenium").pressEnter();
    }
}
