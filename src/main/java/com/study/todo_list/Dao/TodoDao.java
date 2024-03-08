package com.study.todo_list.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.study.todo_list.config.DBConnectionMgr;
import com.study.todo_list.vo.Todo;

public class TodoDao {
	
	private static TodoDao instance;
	private DBConnectionMgr pool;
	
	private TodoDao() {
		pool = DBConnectionMgr.getInstance();
	}
	
	public static TodoDao getInstance() {
		if(instance == null) {
			instance = new TodoDao();
		}
		return instance;
	}
	
	public int addTodo(Todo todo) {
		
		int successCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = pool.getConnection();
			String sql = "insert into todo_list_tb values(0, ?)";
			pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			pstmt.setString(1, todo.getContent());
			
			successCount = pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				todo.setTodoid(rs.getInt(1));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return successCount;
	}
	
	public List<Todo> getTodoListAll() {
		List<Todo> todos = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = pool.getConnection();
			String sql = "select * from todo_list_tb";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Todo todo = Todo.builder()
						.todoid(rs.getInt(1))
						.content(rs.getString(2))
						.build();
				
				todos.add(todo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) { 
					pstmt.close();
				}
				if(con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return todos;
	}
	
}








