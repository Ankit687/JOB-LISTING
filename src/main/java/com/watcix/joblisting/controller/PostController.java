package com.watcix.joblisting.controller;

import com.watcix.joblisting.model.Post;
import com.watcix.joblisting.repository.PostRepository;
import com.watcix.joblisting.repository.SearchPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private SearchPostRepository searchPostRepository;

    @ApiIgnore
    @RequestMapping(value = "/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }

    @GetMapping("/posts")
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @GetMapping("/posts/{text}")
    public List<Post> search(@PathVariable String text) {
        return searchPostRepository.searchByText(text);
    }

    @GetMapping("/post/{id}")
    public Post getPostById(@PathVariable String id) {
        return postRepository.findById(id).get();
    }

    @PostMapping("/add-post")
    public Post addPost(@RequestBody Post post) {
        return postRepository.save(post);
    }


}
