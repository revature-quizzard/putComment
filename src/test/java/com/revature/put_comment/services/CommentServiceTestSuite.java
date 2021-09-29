package com.revature.put_comment.services;

import com.revature.put_comment.repos.CommentsRepo;
import com.revature.put_comment.models.Comment;
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

    @BeforeEach
    void setUp(){
        mockCommentsRepo = mock(CommentsRepo.class);
        sut = new CommentService(mockCommentsRepo);
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
    public void isValid_returnsTrue_givenValidThread(){
        Comment comment1 = new Comment("commentId", "owner", Arrays.asList("tag1", "tag2"), "2021-09-28T12:05:13.628",
                Arrays.asList("subforumId", "threadId"), 0, "description", "threadId", "subject");

        boolean testResult = sut.isValid(comment1);

        assertTrue(testResult);
    }


}
