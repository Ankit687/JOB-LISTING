package com.watcix.joblisting.repository.impl;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.AggregateIterable;
import com.watcix.joblisting.model.Post;
import com.watcix.joblisting.repository.SearchPostRepository;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
public class SearchPostRepositoryImpl implements SearchPostRepository {

    @Autowired
    private MongoClient mongoClient;

    @Autowired
    private MongoConverter mongoConverter;

    @Override
    public List<Post> searchByText(String text) {
        final List<Post> posts = new ArrayList<>();

        MongoDatabase database = mongoClient.getDatabase("watcix");
        MongoCollection<Document> collection = database.getCollection("JobPost");

        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$search",
                        new Document("text", new Document("query", text).append("path", Arrays.asList("techs", "desc", "profile")))),
                        new Document("$sort", new Document("exp", 1L)),
                        new Document("$limit", 5L)));

        result.forEach(document -> posts.add(mongoConverter.read(Post.class, document)));

        return posts;
    }
}
