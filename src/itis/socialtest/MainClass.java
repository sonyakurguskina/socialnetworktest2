package itis.socialtest;

import itis.socialtest.entities.Author;
import itis.socialtest.entities.Post;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/*
 * В папке resources находятся два .csv файла.
 * Один содержит данные о постах в соцсети в следующем формате: Id автора, число лайков, дата, текст
 * Второй содержит данные о пользователях  - id, никнейм и дату рождения
 *
 * Напишите код, который превратит содержимое файлов в обьекты в package "entities"
 * и осуществите над ними следующие опреации:
 *
 * 1. Выведите в консоль все посты в читабельном виде, с информацией об авторе.
 * 2. Выведите все посты за сегодняшнюю дату
 * 3. Выведите все посты автора с ником "varlamov"
 * 4. Проверьте, содержит ли текст хотя бы одного поста слово "Россия"
 * 5. Выведите никнейм самого популярного автора (определять по сумме лайков на всех постах)
 *
 * Для выполнения заданий 2-5 используйте методы класса AnalyticsServiceImpl (которые вам нужно реализовать).
 *
 * Требования к реализации: все методы в AnalyticsService должны быть реализованы с использованием StreamApi.
 * Использование обычных циклов и дополнительных переменных приведет к снижению баллов, но допустимо.
 * Парсинг файлов и реализация методов оцениваются ОТДЕЛЬНО
 *
 *
 * */

public class MainClass {

    private static final String COMMA_DELIMITER = ",";
    private List<Post> allPosts;

    private AnalyticsService analyticsService = new AnalyticsServiceImpl();

    public static void main(String[] args) {
        new MainClass().run("/resources/PostDatabase.csv", "/resources/Authors.csv");
    }

    private void run(String postsSourcePath, String authorsSourcePath) {
        try {
            BufferedReader authorReader = new BufferedReader(new FileReader(authorsSourcePath));

            long userId = 0;
            String username;
            String birthday;

            ArrayList<String[]> arrayList = new ArrayList<>();

            //Author parsing
            String line;
            while ((line = authorReader.readLine()) != null) {
                arrayList.add(line.split("\n"));
            }
            authorReader.close();

            ArrayList<Author> allAuthors = new ArrayList<>();
            for (String[] data : arrayList) {
                String[] currentLine = data[0].split(",");
                userId = Long.parseLong(currentLine[0]);
                username = currentLine[1];
                birthday = currentLine[2];
                Author author = new Author(userId, username, birthday);
                allAuthors.add(author);
            }

            //Post parsing
            BufferedReader postsReader = new BufferedReader(new FileReader(postsSourcePath));
            while ((line = postsReader.readLine()) != null) {
                arrayList.add(line.split("\n"));
            }
            postsReader.close();

            arrayList.clear();

            int authorId = 0;
            StringBuilder content = new StringBuilder();
            long likesCounter;
            String date;


            for (String[] data : arrayList) {
                String[] currentLine = data[0].split(",");

                authorId = Integer.parseInt(currentLine[0]);
                likesCounter = Long.parseLong(currentLine[1]);
                date = currentLine[2];

                for (int j = 3; j < currentLine.length; j++) {
                    content.append(currentLine[j]);
                }

                for (Author author : allAuthors) {
                    if (author.getId() == authorId) {
                        Post post = new Post(date, content.toString(), likesCounter, author);
                        allPosts.add(post);
                    }
                }
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}