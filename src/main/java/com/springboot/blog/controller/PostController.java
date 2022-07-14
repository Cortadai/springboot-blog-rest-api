package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostDtoV2;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "Post Controller")
@Tag(name = "Post Controller", description = "CRUD REST APIs for Post resources")
@RestController
@RequestMapping
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // create post
    @ApiOperation(value = "REST API to create a new post")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/v1/posts/new")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    // get all posts
    @ApiOperation(value = "REST API to retrieve all posts")
    @GetMapping("/api/v1/posts/all")
    public PostResponse getAllPosts(
            @RequestParam(value="pageNo", defaultValue = Constants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value="pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value="sortBy", defaultValue = Constants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value="sortDir", defaultValue = Constants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    // get single post v1
    @ApiOperation(value = "REST API to retrieve a single post by id - V1")
    @GetMapping("/api/v1/posts/single/{id}")
    //@GetMapping(value = "/api/posts/single/{id}", params = "version=1")
    //@GetMapping(value = "/api/posts/single/{id}", headers = "X-API-VERSION=1")
    //@GetMapping(value = "/api/posts/single/{id}", produces = "application/vnd.blogapi.v1+json")
    public ResponseEntity<PostDto> getPostByIdV1(@PathVariable(name="id") long id){
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }

    // get single post v2
    @ApiOperation(value = "REST API to retrieve a single post by id - V2")
    @GetMapping("/api/v2/posts/single/{id}")
    //@GetMapping(value = "/api/posts/single/{id}", params = "version=2")
    //@GetMapping(value = "/api/posts/single/{id}", headers = "X-API-VERSION=2")
    //@GetMapping(value = "/api/posts/single/{id}", produces = "application/vnd.blogapi.v2+json")
    public ResponseEntity<PostDtoV2> getPostByIdV2(@PathVariable(name="id") long id){
        PostDto postDto = postService.getPostById(id);
        PostDtoV2 postDtoV2 = new PostDtoV2();
        postDtoV2.setId(postDto.getId());
        postDtoV2.setTitle(postDto.getTitle());
        postDtoV2.setDescription(postDto.getDescription());
        postDtoV2.setContent(postDto.getContent());
        postDtoV2.setComments(postDto.getComments());
        List<String> tags = new ArrayList<>();
        tags.add("Java");
        tags.add("Spring Boot");
        tags.add("AWS");
        postDtoV2.setTags(tags);
        return ResponseEntity.ok(postDtoV2);
    }

    // update post
    @ApiOperation(value = "REST API to update a single post by id")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/api/v1/posts/update/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name="id") long id){
        return new ResponseEntity<>(postService.updatePost(postDto, id), HttpStatus.OK);
    }

    // delete post
    @ApiOperation(value = "REST API to delete a single post by id")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/api/v1/posts/delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name="id") long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post entity deleted successfully", HttpStatus.OK);
    }

}
