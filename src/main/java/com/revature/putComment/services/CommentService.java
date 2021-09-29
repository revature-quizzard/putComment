package com.revature.putComment.services;

import com.revature.putComment.models.Comment;
import com.revature.putComment.repos.CommentsRepo;

public class CommentService {

    private CommentsRepo commentsRepo;

    /**
     * @param commentsRepo
     * @author - Charles Mettee
     */
    public CommentService(CommentsRepo commentsRepo) {
        this.commentsRepo = commentsRepo;
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
            return false;
        }
        if(comment.getId() == null || comment.getId().trim().equals("")){
            return false;
        }
        if(comment.getAncestors() == null || comment.getAncestors().size() != 2) {
            return false;
        }
        if(comment.getParent() == null || comment.getParent().trim().equals("")){
            return false;
        }
        if(comment.getDescription() == null || comment.getDescription().trim().equals("")){
            return false;
        }
        if(comment.getDate_created() == null || comment.getDate_created().length() != 23){
            return false;
        }
        if(comment.getOwner() == null || comment.getOwner().trim().equals("")){
            return false;
        }
        return true;
    }

}
