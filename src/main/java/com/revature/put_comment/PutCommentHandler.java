package com.revature.put_comment;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.revature.put_comment.models.Comment;
import com.revature.put_comment.repos.CommentsRepo;
import com.revature.put_comment.services.CommentService;

import java.time.LocalDateTime;

public class PutCommentHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final Gson mapper = new GsonBuilder().setPrettyPrinting().create();
    private final CommentsRepo commentsRepo = new CommentsRepo();
    private final CommentService commentService = new CommentService(commentsRepo);

    /**
     * @param requestEvent
     * @param context
     * @return
     * @author - Charles Mettee
     */
    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent requestEvent, Context context) {

        LambdaLogger logger = context.getLogger();
        logger.log("RECEIVED EVENT: " + requestEvent);

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
