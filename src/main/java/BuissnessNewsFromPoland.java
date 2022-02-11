import com.google.gson.Gson;
import model.Article;
import model.ArticlesResponse;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class BuissnessNewsFromPoland {

    private static final String COUNTRY = "pl";
    private static final String CATEGORY = "business";
    private static final String PAGE_SIZE = "100";
    private static final String API_KEY = "7ca4f6397d2f4b4e8bbeb31217781e6e";
    private static final String RESULT_TEXT_FILE = "NewsFromPoland.txt";

    private URL url;
    private InputStreamReader inputStreamReader;
    private ArticlesResponse articlesResponse;
    private List<Article> articlesList;
    private File file;

    public BuissnessNewsFromPoland() {
    }

    public void run() {
        createUrl(COUNTRY, CATEGORY, PAGE_SIZE, API_KEY);
        openInputStreamFromUrl();
        getArticlesResponseFromInputStreamReader();
        closeInputStreamReader();
        articlesList = articlesResponse.getArticles();
        WriteArticlesToTextFile();
    }

    private void WriteArticlesToTextFile() {
        file = new File(RESULT_TEXT_FILE);
        final String SEPARATOR = ":";

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);

            for (Article article : articlesList) {
                fileWriter.write(article.getTitle() + SEPARATOR + article.getDescription() + SEPARATOR + article.getAuthor() + '\n');
            }
            fileWriter.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    private void getArticlesResponseFromInputStreamReader() {
        articlesResponse = new Gson().fromJson(inputStreamReader, ArticlesResponse.class);
    }

    private void openInputStreamFromUrl()  {
        try {
            inputStreamReader = new InputStreamReader(url.openStream());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void createUrl(String country, String category, String pageSize, String apiKey){
        try {
            url =  new URL("https://newsapi.org/v2/top-headlines?country=" + country + "&category=" + category +
                    "&pageSize=" + pageSize + "&apiKey=" + apiKey);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void closeInputStreamReader() {
        try {
            inputStreamReader.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
