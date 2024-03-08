package com.study.todo_list.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.study.todo_list.Dao.TodoDao;
import com.study.todo_list.vo.Todo;

@WebServlet("/todos")
public class SelectTodoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SelectTodoServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	TodoDao todoDao = TodoDao.getInstance();
	List<Todo> todos = todoDao.getTodoListAll();
	
	Gson gson = new Gson();
	
	Map<String, Object> responseMap = new HashMap<>();
	responseMap.put("todo", todos);
	
	response.setStatus(200);
	response.setContentType("application/json");
	response.getWriter().print(gson.toJson(responseMap));
	
	}


}
