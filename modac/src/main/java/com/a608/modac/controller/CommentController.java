package com.a608.modac.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.a608.modac.model.comment.CommentRequest;
import com.a608.modac.model.comment.CommentResponse;
import com.a608.modac.service.CommentService;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/comments")
public class CommentController {

	// private static final String SUCCESS = "success";
	// private static final String FAIL = "fail";

	private final CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	// 댓글 작성 (POST)
	@PostMapping
	public ResponseEntity<?> createComment(@RequestBody final CommentRequest commentRequest) {
		CommentResponse comment = commentService.createComment(commentRequest);
		return new ResponseEntity<CommentResponse>(comment, HttpStatus.CREATED);
	}

	// 댓글 삭제 (DELETE)
	@DeleteMapping("/{seq}")
	public ResponseEntity<?> deleteComment(@PathVariable final Long seq) {
		commentService.deleteComment(seq);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// 댓글 목록조회 (GET)
	@GetMapping
	public ResponseEntity<?> readCommentByArticlesSeq(@RequestParam("article") final Long articlesSeq){
		return new ResponseEntity<>(commentService.readCommentByArticlesSeq(articlesSeq), HttpStatus.OK);
	}

}
