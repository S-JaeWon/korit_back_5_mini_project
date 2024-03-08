package com.study.todo_list.vo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data

public class Todo {
	private int todoid;
	private String content;
}
