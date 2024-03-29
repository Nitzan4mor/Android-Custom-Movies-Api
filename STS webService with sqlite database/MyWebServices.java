package nitzan.mor.project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.List;

import java.sql.ResultSet;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyWebServices  {
	
	
	// creating empty Movie list 
	static List<Movie> movies = new ArrayList<Movie>();
	
	
	// when we receive GET request - send select to the sqlite database 
	// clear the Movie list and add the movies we received from the database to the Movie list
	@RequestMapping( value = "/movies", method = RequestMethod.GET)
	public List<Movie> doGetMovies(){
		Connection connection = null;
	      try{
	        connection = DriverManager.getConnection("jdbc:sqlite:movies.db");
	        Statement statement = connection.createStatement();
	        statement.setQueryTimeout(30);  
	        ResultSet rs = statement.executeQuery("select * from movies");
	        movies.clear();
	        while(rs.next())
	        {
	          movies.add(new Movie(rs.getString("name") , rs.getString("rate") , rs.getString("actors"), rs.getString("imageUrl")));
	        }
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
		return movies;
	}
	
	// when we receive send POST request - take the request body and adjust it to Movie object
	// and send insert request to the sqlite database
	@RequestMapping( value = "/movies" , method = RequestMethod.POST)
	public String doPostMovies(@RequestBody Movie movie) {
		String result = "Failed to add new movie";
		Connection connection = null;
	      try{
	        connection = DriverManager.getConnection("jdbc:sqlite:movies.db");
	        Statement statement = connection.createStatement();
	        statement.setQueryTimeout(30);  
	        statement.executeUpdate("insert into movies values\r\n" + 
	        		"('"+ movie.getName() +"',\r\n" + 
	        		"'"+ movie.getRate()+"' ,\r\n" + 
	        		"'"+movie.getActors()+"',\r\n" + 
	        		"'"+movie.getImageUrl()+"')");
	        result = "new movie added";
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
		return result;
	}
	
	// when we receive PUT request - take the request body and adjust it to Movie object
	// take the name parameter and adjust it to String
	// send update request to the sqlite database
	@RequestMapping( value = "/movies/{name}", method = RequestMethod.PUT)
	public String doPutMovies(@RequestBody Movie movie, @PathVariable("name") String name)
	{
		String result = "Failed to update movie";
		Connection connection = null;
	      try{
	        connection = DriverManager.getConnection("jdbc:sqlite:movies.db");
	        Statement statement = connection.createStatement();
	        statement.setQueryTimeout(30);  
	        statement.executeUpdate("update movies\r\n" + 
	        		"set name = '"+movie.getName()+"',\r\n" + 
	        		" rate='"+ movie.getRate() +"',\r\n" + 
	        		" actors='"+ movie.getActors()+"',\r\n" + 
	        		" imageUrl = '"+ movie.getImageUrl() +"'\r\n" + 
	        		" where name = '"+ name +"'");
	        result = "movie updated";
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
		return result;
	}

	//when we receive DELETE request - take the name parameter and adjust it to String
	//and send delete request to the sqlite database
	@RequestMapping( value = "/movies/{name}", method = RequestMethod.DELETE)
	public String doDeleteMovies(@PathVariable("name") String name)
	{
		String result = "Failed to delete movie";
		Connection connection = null;
	      try{
	        connection = DriverManager.getConnection("jdbc:sqlite:movies.db");
	        Statement statement = connection.createStatement();
	        statement.setQueryTimeout(30);  
	        statement.executeUpdate("delete from movies\r\n" + 
	        		" where name = '"+name+"'");
	        result = "movie deleted";
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
		return result;
	}
	

}
