package com.revature.putComment.services;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.revature.putComment.repos.CommentsRepo;
import com.revature.putComment.models.Comment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class CommentServiceTestSuite {

    CommentService sut;
    CommentsRepo mockCommentsRepo;
    LambdaLogger mockLambdaLogger;

    @BeforeEach
    void setUp(){
        mockCommentsRepo = mock(CommentsRepo.class);
        mockLambdaLogger = mock(LambdaLogger.class);
        sut = new CommentService(mockCommentsRepo, mockLambdaLogger);
    }

    @AfterEach
    void tearDown(){
        sut = null;
        reset(mockCommentsRepo);
    }


    /**
     * author - Charles Mettee
     */
    @Test
    public void isValid_returnsFalse_givenNullComment(){
        Comment comment = null;

        boolean testResult = sut.isValid(comment);

        assertFalse(testResult);
    }

    /**
     * author - Charles Mettee
     */
    @Test
    public void isValid_returnsFalse_givenNullOrEmptyId(){
        Comment comment1 = new Comment(null, "owner", Arrays.asList("tag1", "tag2"), "2021-09-28T12:05:13.628",
                Arrays.asList("subforumId", "threadId"), 0, "description", "threadId", "subject");
        Comment comment2 = new Comment("", "owner", Arrays.asList("tag1", "tag2"), "2021-09-28T12:05:13.628",
                Arrays.asList("subforumId", "threadId"), 0, "description", "threadId", "subject");
        Comment comment3 = new Comment("      ", "owner", Arrays.asList("tag1", "tag2"), "2021-09-28T12:05:13.628",
                Arrays.asList("subforumId", "threadId"), 0, "description", "threadId", "subject");

        boolean testResult1 = sut.isValid(comment1);
        boolean testResult2 = sut.isValid(comment2);
        boolean testResult3 = sut.isValid(comment3);

        assertFalse(testResult1);
        assertFalse(testResult2);
        assertFalse(testResult3);
    }

    /**
     * author - Charles Mettee
     */
    @Test
    public void isValid_returnsFalse_givenAncestorsNullOrNotSizeTwo(){
        Comment comment1 = new Comment("commentId", "owner", Arrays.asList("tag1", "tag2"), "2021-09-28T12:05:13.628",
                null, 0, "description", "threadId", "subject");
        Comment comment2 = new Comment("commentId", "owner", Arrays.asList("tag1", "tag2"), "2021-09-28T12:05:13.628",
                Arrays.asList("threadId"), 0, "description", "threadId", "subject");

        boolean testResult1 = sut.isValid(comment1);
        boolean testResult2 = sut.isValid(comment2);

        assertFalse(testResult1);
        assertFalse(testResult2);
    }

    /**
     * author - Charles Mettee
     */
    @Test
    public void isValid_returnsFalse_givenNullOrEmptyParent(){
        Comment comment1 = new Comment("commentId", "owner", Arrays.asList("tag1", "tag2"), "2021-09-28T12:05:13.628",
                Arrays.asList("subforumId", "threadId"), 0, "description", null, "subject");
        Comment comment2 = new Comment("commentId", "owner", Arrays.asList("tag1", "tag2"), "2021-09-28T12:05:13.628",
                Arrays.asList("subforumId", "threadId"), 0, "description", "", "subject");
        Comment comment3 = new Comment("commentId", "owner", Arrays.asList("tag1", "tag2"), "2021-09-28T12:05:13.628",
                Arrays.asList("subforumId", "threadId"), 0, "description", "     ", "subject");

        boolean testResult1 = sut.isValid(comment1);
        boolean testResult2 = sut.isValid(comment2);
        boolean testResult3 = sut.isValid(comment3);

        assertFalse(testResult1);
        assertFalse(testResult2);
        assertFalse(testResult3);
    }

    /**
     * author - Charles Mettee
     */
    @Test
    public void isValid_returnsFalse_givenNullOrEmptyDescription(){
        Comment comment1 = new Comment("commentId", "owner", Arrays.asList("tag1", "tag2"), "2021-09-28T12:05:13.628",
                Arrays.asList("subforumId", "threadId"), 0, null, "threadId", "subject");
        Comment comment2 = new Comment("commentId", "owner", Arrays.asList("tag1", "tag2"), "2021-09-28T12:05:13.628",
                Arrays.asList("subforumId", "threadId"), 0, "", "threadId", "subject");
        Comment comment3 = new Comment("commentId", "owner", Arrays.asList("tag1", "tag2"), "2021-09-28T12:05:13.628",
                Arrays.asList("subforumId", "threadId"), 0, "     ", "threadId", "subject");

        boolean testResult1 = sut.isValid(comment1);
        boolean testResult2 = sut.isValid(comment2);
        boolean testResult3 = sut.isValid(comment3);

        assertFalse(testResult1);
        assertFalse(testResult2);
        assertFalse(testResult3);
    }

    /**
     * author - Charles Mettee
     */
    @Test
    public void isValid_returnsFalse_givenNullOrInvalidDate(){
        Comment comment1 = new Comment("commentId", "owner", Arrays.asList("tag1", "tag2"), null,
                Arrays.asList("subforumId", "threadId"), 0, "description", "threadId", "subject");
        Comment comment2 = new Comment("commentId", "owner", Arrays.asList("tag1", "tag2"), "date",
                Arrays.asList("subforumId", "threadId"), 0, "description", "threadId", "subject");

        boolean testResult1 = sut.isValid(comment1);
        boolean testResult2 = sut.isValid(comment2);

        assertFalse(testResult1);
        assertFalse(testResult2);
    }

    /**
     * author - Charles Mettee
     */
    @Test
    public void isValid_returnsFalse_givenNullOrEmptyOwner(){
        Comment comment1 = new Comment("commentId", null, Arrays.asList("tag1", "tag2"), "2021-09-28T12:05:13.628",
                Arrays.asList("subforumId", "threadId"), 0, "description", "threadId", "subject");
        Comment comment2 = new Comment("commentId", "", Arrays.asList("tag1", "tag2"), "2021-09-28T12:05:13.628",
                Arrays.asList("subforumId", "threadId"), 0, "description", "threadId", "subject");
        Comment comment3 = new Comment("commentId", "     ", Arrays.asList("tag1", "tag2"), "2021-09-28T12:05:13.628",
                Arrays.asList("subforumId", "threadId"), 0, "description", "threadId", "subject");

        boolean testResult1 = sut.isValid(comment1);
        boolean testResult2 = sut.isValid(comment2);
        boolean testResult3 = sut.isValid(comment3);

        assertFalse(testResult1);
        assertFalse(testResult2);
        assertFalse(testResult3);
    }

    /**
     * author - Charles Mettee
     */
    @Test
    public void isValid_returnsTrue_givenValidThread(){
        Comment comment1 = new Comment("commentId", "owner", Arrays.asList("tag1", "tag2"), "2021-09-28T12:05:13.628",
                Arrays.asList("subforumId", "threadId"), 0, "description", "threadId", "subject");

        boolean testResult = sut.isValid(comment1);

        assertTrue(testResult);
    }

    /**
     * author - Charles Mettee
     */
    @Test
    public void updateThreads_throwsException_givenInvalidThread(){
        Comment comment = null;
        boolean testResult = false;

        try{
            sut.updateComment(comment);
        } catch (Exception e){
            testResult = true;
        }

        assertTrue(testResult);
    }

    /**
     * author - Charles Mettee
     */
    @Test
    public void updateThreads_isSuccessful_givenValidThread(){
        Comment comment = new Comment("commentId", "owner", Arrays.asList("tag1", "tag2"), "2021-09-28T12:05:13.628",
                Arrays.asList("subforumId", "threadId"), 0, "description", "threadId", "subject");

        try{
            sut.updateComment(comment);
        } catch (Exception e){

        }

        verify(mockCommentsRepo, times(1)).updateComment(comment);

    }


}
