package com.springboot.blog.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@ApiModel(description = "Post model information")
@Data
public class PostDto {

    @ApiModelProperty(value = "Blog post ID")
    private long id;

    // title should not be null or empty
    // title should have at least 5 characters
    @ApiModelProperty(value = "Blog post title")
    @NotEmpty
    @Size(min = 5, message="Post tittle should have at least 5 characters")
    private String title;

    // description should not be null or empty
    // description should have at least 10 characters
    @ApiModelProperty(value = "Blog post description")
    @NotEmpty
    @Size(min = 10, message="Post description should have at least 10 characters")
    private String description;

    // content should not be null or empty
    @ApiModelProperty(value = "Blog post content")
    @NotEmpty
    private String content;

    @ApiModelProperty(value = "Blog post comments")
    private Set<CommentDto> comments;

}
