package com.revature.putComment.repos;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.revature.putComment.models.Comment;

public class CommentsRepo {

    private final DynamoDBMapper dbReader;

    /**
     * Constructor for initializing the DynamoDBMapper;
     * @author - Charles Mettee
     */
    public CommentsRepo(){
        dbReader = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    }

    /**
     * @param comment - The Comment object being updated
     * @author - Charles Mettee
     */
    public void updateComment(Comment comment){
        dbReader.save(comment);
    }



}
