package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SpringBootApplication
public class Exercise3Application {

	public static void main(String[] args) {
		SpringApplication.run(Exercise3Application.class, args);
		
		Connection connection = null;
	      try{
	        connection = DriverManager.getConnection("jdbc:sqlite:movies.db");
	        Statement statement = connection.createStatement();
	        statement.setQueryTimeout(30);  
	        statement.executeUpdate("create table movies (name string, rate string, actors string, imageUrl string)");
	      }catch(SQLException e){
	        System.err.println(e.getMessage());
	      }finally{
	        try{
	          if(connection != null)
	            connection.close();
	        }catch(SQLException e){
	          System.err.println(e.getMessage());
	        }
	      }
		
	}

}