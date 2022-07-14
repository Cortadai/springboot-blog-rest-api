package com.springboot.blog.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@ApiModel(description = "Comment model information")
@Data
public class CommentDto {

    @ApiModelProperty(value = "Comment ID")
    private long id;

    // name should not be null or empty
    // name should have at least 5 characters
    @ApiModelProperty(value = "Name of the user who made the comment")
    @NotEmpty(message = "Name should not be null or empty")
    @Size(min = 5, message="Comment name should have at least 5 characters")
    private String name;

    // email should not be null or empty
    // email should have at least 10 characters
    @ApiModelProperty(value = "Email of the user who made the comment")
    @NotEmpty(message = "Name should not be null or empty")
    @Size(min = 10, message="Comment email should have at least 10 characters")
    private String email;

    // body should not be null or empty
    // body should have at least 10 characters
    @ApiModelProperty(value = "The content of the comment")
    @NotEmpty(message = "Body should not be null or empty")
    @Size(min = 10, message="Comment body should have at least 10 characters")
    private String body;

}
