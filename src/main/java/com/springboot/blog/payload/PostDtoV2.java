package com.springboot.blog.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Data
public class PostDtoV2 {

    private long id;

    // title should not be null or empty
    // title should have at least 5 characters
    @NotEmpty
    @Size(min = 5, message="Post tittle should have at least 5 characters")
    private String title;

    // description should not be null or empty
    // description should have at least 10 characters
    @NotEmpty
    @Size(min = 10, message="Post description should have at least 10 characters")
    private String description;

    // content should not be null or empty
    @NotEmpty
    private String content;

    private Set<CommentDto> comments;

    private List<String> tags;

}
