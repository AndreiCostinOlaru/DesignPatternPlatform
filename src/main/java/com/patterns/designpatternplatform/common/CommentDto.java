package com.patterns.designpatternplatform.common;

import com.patterns.designpatternplatform.entities.Comment;
import com.patterns.designpatternplatform.entities.Pattern;

import java.util.Date;
import java.util.List;

public class CommentDto {
    private Long id;
    private String content;
    private int indentLevel;
    private String author;
    private Date timestamp;
    private List<Comment> replies;
    private Comment parentComment;

    public List<Comment> getReplies() {
        return replies;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public int getIndentLevel() {
        return indentLevel;
    }

    public String getAuthor() {
        return author;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setIndentLevel(int indentLevel) {
        this.indentLevel = indentLevel;
    }

    public CommentDto(Long id, String content, int indentLevel, String author, Date timestamp, List<Comment> replies, Comment parentComment) {
        this.id = id;
        this.content = content;
        this.indentLevel = indentLevel;
        this.author = author;
        this.timestamp = timestamp;
        this.replies = replies;
        this.parentComment = parentComment;
    }
}
