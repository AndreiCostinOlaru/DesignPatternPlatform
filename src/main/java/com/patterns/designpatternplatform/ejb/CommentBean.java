package com.patterns.designpatternplatform.ejb;

import com.patterns.designpatternplatform.common.CommentDto;
import com.patterns.designpatternplatform.common.CommentNotificationSystem;
import com.patterns.designpatternplatform.entities.*;
import com.patterns.designpatternplatform.abstractClasses.CommentComponent;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class CommentBean extends CommentComponent {
    private static final Logger LOG = Logger.getLogger(CommentBean.class.getName());
    private static List<CommentDto> comments=new ArrayList<>();
    @PersistenceContext
    EntityManager entityManager;

    @Inject
    UserBean userBean;

    CommentNotificationSystem commentNotificationSystem=new CommentNotificationSystem();


    public List<CommentDto> copyCommentsToDto(List<Comment> commentList){//converts a list of comments into a list of commentDtos objects and registers the owners of the comments as observers
        List<CommentDto> commentDtos=new ArrayList<>();
        for(Comment comment:commentList){
            CommentDto commentDto=new CommentDto(comment.getId(),comment.getContent(),comment.getIndentLevel(),comment.getOwner().getUsername(),comment.getTimestamp(),comment.getReplies(),comment.getParentComment());
            commentDtos.add(commentDto);
            commentNotificationSystem.registerObserver(comment.getOwner(),comment.getOwner().getId());
        }
        return commentDtos;
    }

    public List<CommentDto> findRootCommentsById(long discussionId){//finds root comments in the database using the id of the discussion and checking for null parent comments
        try {
            LOG.info("findAllComments");
            TypedQuery<Comment> typedQuery = entityManager.createQuery("SELECT d FROM Comment d WHERE d.discussion.id = :discussionId AND d.parentComment IS NULL", Comment.class);
            typedQuery.setParameter("discussionId", discussionId);
            List<Comment> rootComments = typedQuery.getResultList();
            return copyCommentsToDto(rootComments);
        }
        catch (Exception e){
            throw new EJBException(e);
        }
    }

    public void displayCommentThread(long discussionId) {//finds all comments in a discussion and prepares comments to be displayed
        comments.clear();
        List<CommentDto> commentList=findRootCommentsById(discussionId);
        for (CommentDto comment:commentList) {
            displayComment(comment);
        }
    }

    public void displayComment(CommentDto comment) {//prepares comments to be displayed by adding indentation in a tree structure fashion
        comments.add(comment);
        List<CommentDto> replies=copyCommentsToDto(comment.getReplies());
        if (replies != null) {
            for (CommentDto reply : replies) {
                int indent=comment.getIndentLevel();
                reply.setIndentLevel(indent+20);
                displayComment(reply);
            }
        }
    }

    public static List<CommentDto> getComments() {
        return comments;
    }

    @Transactional
    public void createComment(long discussionId, String content, long owner, Date timestamp, Long parentComment) {// adds comment to the database based on input parameters
        LOG.info("createComment");
        Comment comment=new Comment();
        comment.setContent(content);
        comment.setIndentLevel(0);
        comment.setTimestamp(timestamp);
        User user=entityManager.find(User.class,owner);
        user.getComments().add(comment);
        comment.setOwner(user);
        commentNotificationSystem.registerObserver(user,user.getId());
        if(parentComment!=null){
            Comment parentCmt=entityManager.find(Comment.class,parentComment);
            parentCmt.getReplies().add(comment);
            comment.setParentComment(parentCmt);
            if(parentCmt.getOwner()!= comment.getOwner()) commentNotificationSystem.notifyCommentObservers(content,parentCmt.getOwner().getId(),user.getUsername());
        }
        Discussion discussion=entityManager.find(Discussion.class, discussionId);
        discussion.getComments().add(comment);
        comment.setDiscussion(discussion);
        entityManager.persist(comment);
    }
}
