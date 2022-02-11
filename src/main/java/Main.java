import com.google.gson.Gson;
import model.Article;
import model.ArticlesResponse;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

public class Main {

    private static final String COUNTRY = "pl";
    private static final String CATEGORY = "business";
    private static final String PAGE_SIZE = "100";
    private static final String API_KEY = "7ca4f6397d2f4b4e8bbeb31217781e6e";
    private static final String RESULT_TEXT_FILE = "result.txt";

    public static void main(String[] args) throws IOException {

        URL url = new URL("https://newsapi.org/v2/top-headlines?country=" + COUNTRY + "&category=" + CATEGORY +
                "&pageSize=" + PAGE_SIZE + "&apiKey=" + API_KEY);

        InputStreamReader inputStreamReader = new InputStreamReader(url.openStream());

        ArticlesResponse articles = new Gson().fromJson(inputStreamReader, ArticlesResponse.class);

        inputStreamReader.close();

        List<Article> articlesList = articles.getArticles();

        File file = new File(RESULT_TEXT_FILE);
        FileWriter fileWriter = new FileWriter(file);

        System.out.println(articlesList.size());


        for (Article article : articlesList) {
            fileWriter.write(article.getTitle() + ":" + article.getDescription() + ":" + article.getAuthor() + '\n');
        }

        fileWriter.close();

    }

}
