package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Comment Controller")
@Tag(name = "Comment Controller", description = "CRUD REST APIs for Comment resources")
@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @ApiOperation(value = "REST API to create a new comment by post-id")
    @PostMapping("/create/{postId}")
    public ResponseEntity<CommentDto> createComment(
            @PathVariable(value = "postId") long postId,
            @Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "REST API to retrieve all comments per post by post-id")
    @GetMapping("/perPost/{postId}")
    public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postId") long postId){
        return commentService.getCommentsByPostId(postId);
    }

    @ApiOperation(value = "REST API to retrieve a single comment by post-id and comment-id")
    @GetMapping("/single/{postId}/{commentId}")
    public ResponseEntity<CommentDto>getCommentByPostId(
            @PathVariable(value = "postId") long postId,
            @PathVariable(value = "commentId") long commentId){
        CommentDto commentDto = commentService.getCommentById(postId, commentId);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    @ApiOperation(value = "REST API to update a single comment by post-id and comment-id")
    @PutMapping("/edit/{postId}/{commentId}")
    public ResponseEntity<CommentDto>editComment(
            @PathVariable(value = "postId") long postId,
            @PathVariable(value = "commentId") long commentId,
            @Valid @RequestBody CommentDto commentDto){
        CommentDto responseDto = commentService.updateComment(postId, commentId, commentDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @ApiOperation(value = "REST API to delete a single comment by post-id and comment-id")
    @DeleteMapping("/delete/{postId}/{commentId}")
    public ResponseEntity<String>deleteComment(
            @PathVariable(value = "postId") long postId,
            @PathVariable(value = "commentId") long commentId){
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
    }

}
