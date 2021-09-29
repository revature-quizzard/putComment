package com.revature.put_comment.services;

import com.revature.put_comment.models.Comment;
import com.revature.put_comment.repos.CommentsRepo;

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

    public boolean isValid(Comment comment){
        //#TODO add validation logic
        return true;
    }

}
