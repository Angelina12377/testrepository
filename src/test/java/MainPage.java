import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$x;

/**
 * Главная страница сайта Aliexpress.com
*/
public class MainPage {
    private final SelenideElement textBoxInput = $x ("//input[@type='text']");

    /**
     * Ввод слова Dresses для поиска платьев среди товаров и нажимается кнопка Enter
     * @param searchString поисковая строка
     */
    public SearchPage search(String searchString){
        textBoxInput.setValue(searchString);
        textBoxInput.sendKeys(Keys.ENTER);
        return new SearchPage();
    }

    public MainPage (String url){
        Selenide.open(url);
    }

}
