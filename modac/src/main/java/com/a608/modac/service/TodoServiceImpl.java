package com.a608.modac.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a608.modac.model.category.Category;
import com.a608.modac.model.todo.Todo;
import com.a608.modac.model.user.User;
import com.a608.modac.repository.CategoryRepository;
import com.a608.modac.repository.RoomRepository;
import com.a608.modac.repository.TodoRepository;
import com.a608.modac.model.todo.TodoRequest;
import com.a608.modac.model.todo.TodoResponse;
import com.a608.modac.repository.UserRepository;

@Service
public class TodoServiceImpl implements TodoService{
	private final TodoRepository todoRepository;
	private final UserRepository userRepository;
	private final CategoryRepository categoryRepository;
	public TodoServiceImpl(TodoRepository todoRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
		this.todoRepository = todoRepository;
		this.userRepository = userRepository;
		this.categoryRepository = categoryRepository;
	}

	@Autowired
	private S3Service s3Service;

	@Override
	public TodoResponse saveTodo(final TodoRequest todoRequest) {

		User user = userRepository.findById(todoRequest.getUsersSeq()).orElseThrow(() -> new NoSuchElementException("NoUser"));
		Category category = categoryRepository.findById(todoRequest.getCategoriesName())
			.orElseThrow(() -> new NoSuchElementException("NoCategory"));

		Todo save = todoRepository.save(todoRequest.toEntity(user, category));
		return TodoResponse.builder().todo(save).build();
	} // todo 작성.

	@Override
	public TodoResponse findTodo(final Long seq) {
		Todo todo = todoRepository.findById(seq).orElseThrow(() -> new NoSuchElementException("NoTodo"));
		TodoResponse todoResponse = new TodoResponse(todo);
		String templateContent = s3Service.read(todo.getCategory().getTemplateFilepath()); // S3에 저장된 템플릿 정보 가져오기
		todoResponse.setTemplateContent(templateContent); // 템플릿 정보를 TodoResponse에 담기
		return todoResponse;
	}

	@Override
	public TodoResponse updateTodo(final Long seq, final TodoRequest todoRequest){
		Todo findTodo = todoRepository.findById(seq).orElseThrow(() -> new NoSuchElementException("NoTodo"));
		Category category = categoryRepository.findById(todoRequest.getCategoriesName()).orElseThrow(() -> new NoSuchElementException("NoCategory"));
		findTodo.updateTodo(todoRequest.getTitle(), todoRequest.getStatus(), todoRequest.getTotalSecond(), category);

		Todo save = todoRepository.save(findTodo);

		return new TodoResponse(save);
	} // todo 수정 (내용, 상태, 시간)


	@Override
	public void deleteTodo(final Long seq) {
		todoRepository.findById(seq).orElseThrow(() -> new NoSuchElementException("NoTodo"));
		todoRepository.deleteById(seq);
	} // todo 삭제

	@Override
	public List<TodoResponse> findTodosByUsersSeq(final Long usersSeq) {
		final List<Todo> findTodos = todoRepository.findTodosByUser_Seq(usersSeq);
		return findTodos.stream().map(TodoResponse::new).collect(Collectors.toList());
	} // todo 목록조회



}