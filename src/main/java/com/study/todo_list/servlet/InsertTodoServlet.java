package com.study.todo_list.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.study.todo_list.Dao.TodoDao;
import com.study.todo_list.vo.Todo;

@WebServlet("/todo")
public class InsertTodoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public InsertTodoServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringBuilder builder = new StringBuilder();
		String readTodo = null;
		
		BufferedReader reader = request.getReader();
		
		while((readTodo = reader.readLine()) != null) {
			builder.append(readTodo);
		}
	
		Gson gson = new Gson();
		
		Todo todo = gson.fromJson(builder.toString(), Todo.class);
		
		TodoDao todoDao = TodoDao.getInstance();
		
		int successCount= todoDao.addTodo(todo);
		
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("status", 201);
		responseMap.put("todo", "할것");
		responseMap.put("successCount", successCount);
		
		response.setStatus(201);
		response.setContentType("application/json");
		
		PrintWriter writer = response.getWriter();
		writer.print(gson.toJson(responseMap));
		
	}

}
