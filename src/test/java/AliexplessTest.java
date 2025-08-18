import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

public class AliexplessTest extends basetest {
    private final static String BASE_URL = "https://aliexpress.com/";
    private final static String SEARCH_STRING = "DRESS";
    private final static String SEARCH_WORD = "DRESS";

    @Test
    public void checkTitle(){
        MainPage mainPage = new MainPage(BASE_URL);
        mainPage.search(SEARCH_STRING);
        SearchPage searchPage = new SearchPage();
        String title = searchPage.getTitleFromFirstArticle();
        title.contains(SEARCH_WORD);
    }
}
