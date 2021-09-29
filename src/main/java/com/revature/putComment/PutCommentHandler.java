package com.revature.putComment;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.revature.putComment.models.Comment;
import com.revature.putComment.repos.CommentsRepo;
import com.revature.putComment.services.CommentService;

import java.time.LocalDateTime;

public class PutCommentHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final Gson mapper = new GsonBuilder().setPrettyPrinting().create();

    /**
     * @param requestEvent
     * @param context
     * @return
     * @author - Charles Mettee
     */
    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent requestEvent, Context context) {

        LambdaLogger logger = context.getLogger();
        CommentsRepo commentsRepo = new CommentsRepo();
        CommentService commentService = new CommentService(commentsRepo, logger);

        Comment comment = mapper.fromJson(requestEvent.getBody(), Comment.class);
        comment.setDate_created(LocalDateTime.now().toString());

        APIGatewayProxyResponseEvent respEvent = new APIGatewayProxyResponseEvent();

        try{
            commentService.updateComment(comment);
            respEvent.setStatusCode(200);
            return respEvent;
        } catch (Exception e){
            respEvent.setStatusCode(400);
            return respEvent;
        }

    }
}
