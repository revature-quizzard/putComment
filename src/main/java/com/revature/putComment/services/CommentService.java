package com.revature.putComment.services;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.revature.putComment.models.Comment;
import com.revature.putComment.repos.CommentsRepo;

public class CommentService {

    private CommentsRepo commentsRepo;
    private LambdaLogger lambdaLogger;

    /**
     * @param commentsRepo
     * @author - Charles Mettee
     */
    public CommentService(CommentsRepo commentsRepo, LambdaLogger lambdaLogger) {
        this.commentsRepo = commentsRepo;
        this.lambdaLogger = lambdaLogger;
    }

    /**
     * @param comment - The Comment to be sent to the repo for updating in the database
     * @throws Exception
     *
     * @author - Charles Mettee
     */
    public void updateComment(Comment comment) throws Exception{
        if(!isValid(comment)){
            throw new Exception("An error occurred");
        }
        lambdaLogger.log("The provided comment is valid; Update successful.");
        commentsRepo.updateComment(comment);
    }

    /**
     * @param comment - The comment being validated
     * @return - a boolean value representing whether the comment is valid
     *
     * @author - Charles Mettee
     */
    public boolean isValid(Comment comment){
        if(comment == null){
            lambdaLogger.log("Update rejected because the specified comment does not exist.");
            return false;
        }
        if(comment.getId() == null || comment.getId().trim().equals("")){
            lambdaLogger.log("Update rejected because the ID of the specified comment does not exist.");
            return false;
        }
        if(comment.getAncestors() == null || comment.getAncestors().size() != 2) {
            lambdaLogger.log("Update rejected because the specified comment contains fewer than two ancestors.");
            return false;
        }
        if(comment.getParent() == null || comment.getParent().trim().equals("")){
            lambdaLogger.log("Update rejected because the specified comment does not belong to a thread.");
            return false;
        }
        if(comment.getDescription() == null || comment.getDescription().trim().equals("")){
            lambdaLogger.log("Update rejected because the specified comment does not contain a body.");
            return false;
        }
        if(comment.getDate_created() == null || comment.getDate_created().length() != 23){
            lambdaLogger.log("Update rejected because the specified comment has an incorrectly formatted date.");
            return false;
        }
        if(comment.getOwner() == null || comment.getOwner().trim().equals("")){
            lambdaLogger.log("Update rejected because the specified comment has no owner.");
            return false;
        }
        return true;
    }

}
