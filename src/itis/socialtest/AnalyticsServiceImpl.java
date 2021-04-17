package itis.socialtest;

import itis.socialtest.entities.Post;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AnalyticsServiceImpl implements AnalyticsService {
    @Override
    public List<Post> findPostsByDate(List<Post> posts, String date) {

        return posts.stream().filter(current -> current.getDate().equals(date)).collect(Collectors.toList());
    }

    @Override
    public String findMostPopularAuthorNickname(List<Post> posts) {
        return null;
    }

    @Override
    public Boolean checkPostsThatContainsSearchString(List<Post> posts, String searchString) {

        return posts.stream().anyMatch(current -> current.getContent().contains(searchString));
    }

    @Override
    public List<Post> findAllPostsByAuthorNickname(List<Post> posts, String nick){
        return posts.stream().filter(current -> current.getAuthor().getNickname().equals(nick)).collect(Collectors.toList());
    }
}