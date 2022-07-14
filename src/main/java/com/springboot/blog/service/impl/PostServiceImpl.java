package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        // convert DTO into Post Entity
        Post post = mapToEntity(postDto);
        Post savedPost = postRepository.save(post);

        // convert Post Entity into DTO
        PostDto postDtoResponse = mapToDto(savedPost);
        return postDtoResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> postsPerPage = postRepository.findAll(pageable);

        // get content for page object
        List<Post> postList = postsPerPage.getContent();

        //List<Post> postList = postRepository.findAll();
        //return postList.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        List<PostDto> content = postList.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(postsPerPage.getNumber());
        postResponse.setPageSize(postsPerPage.getSize());
        postResponse.setTotalElements(postsPerPage.getTotalElements());
        postResponse.setTotalPages(postsPerPage.getTotalPages());
        postResponse.setLast(postsPerPage.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post existingPost = postRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        existingPost.setTitle(postDto.getTitle());
        existingPost.setDescription(postDto.getDescription());
        existingPost.setContent(postDto.getContent());
        Post responsePost = postRepository.save(existingPost);
        return mapToDto(responsePost);
    }

    @Override
    public void deletePostById(long id) {
        Post existingPost = postRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(existingPost);
    }

    // convert entity into DTO
    private PostDto mapToDto(Post post){
        /*
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
         */
        return modelMapper.map(post, PostDto.class);
    }

    // convert DTO into entity
    private Post mapToEntity(PostDto postDto){
        /*
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
         */
        return modelMapper.map(postDto, Post.class);
    }
}
