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
     * @param requestEvent - The proxy event from AWS API Gateway
     * @param context - the context of the request
     * @return - response with HTTP status code
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
            Map<String, String> headers = new HashMap<>();
            headers.put("Access-Control-Allow-Headers", "Content-Type,X-Amz-Date,Authorization");
            headers.put("Access-Control-Allow-Origin", "*");
            respEvent.setHeaders(headers);
            return respEvent;
        } catch (Exception e){
            respEvent.setStatusCode(400);
            Map<String, String> headers = new HashMap<>();
            headers.put("Access-Control-Allow-Headers", "Content-Type,X-Amz-Date,Authorization");
            headers.put("Access-Control-Allow-Origin", "*");
            respEvent.setHeaders(headers);
            return respEvent;
        }

    }
}
