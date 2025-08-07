import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class SearchPage {
    private final ElementsCollection articleTitles = $$x("//div//div");

    /**
     * Возвращает Title из первого товара
     */

    public String getTitleFromFirstArticle(){
        return articleTitles.first().getAttribute("title");

    }


}
