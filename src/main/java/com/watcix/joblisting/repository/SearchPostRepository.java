package com.watcix.joblisting.repository;

import com.watcix.joblisting.model.Post;

import java.util.List;

public interface SearchPostRepository {
    List<Post> searchByText(String text);
}
