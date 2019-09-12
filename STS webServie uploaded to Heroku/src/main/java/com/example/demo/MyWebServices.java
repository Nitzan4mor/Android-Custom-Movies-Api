package com.example.demo;

import java.util.ArrayList;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyWebServices  {
	
	
	// creating Movie list and adding few movies so it won't be empty
	static List<Movie> movies = new ArrayList<Movie>();
	static {
		movies.add(new Movie("Jack Reacher", "70%", "Tom Cruise, Rosamund Pike, Richard Jenkins, David Oyelowo", "https://m.media-amazon.com/images/M/MV5BMTM1NjUxMDI3OV5BMl5BanBnXkFtZTcwNjg1ODM3OA@@._V1_UY98_CR0,0,67,98_AL_.jpg" ));
		movies.add(new Movie("Armageddon", "67%", "Bruce Willis, Billy Bob Thornton, Ben Affleck , Liv Tyler ", "https://m.media-amazon.com/images/M/MV5BMGM0NzE2YjgtZGQ4YS00MmY3LTg4ZDMtYjUwNTNiNTJhNTQ5XkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_UY98_CR0,0,67,98_AL_.jpg" ));
		movies.add(new Movie("Aladdin", "80%", "Scott Weinger, Robin Williams, Linda Larkin, Jonathan Freeman	", "https://m.media-amazon.com/images/M/MV5BY2Q2NDI1MjUtM2Q5ZS00MTFlLWJiYWEtNTZmNjQ3OGJkZDgxXkEyXkFqcGdeQXVyNTI4MjkwNjA@._V1_UX67_CR0,0,67,98_AL_.jpg"));
	}
	
	// when we receive GET request - return the movie list
	@RequestMapping( value = "/movies", method = RequestMethod.GET)
	public List<Movie> doGetMovies(){
		return movies;
	}
	
	// when we receive send POST request - take the request body and adjust it to Movie object
	// and add it to the movie list
	@RequestMapping( value = "/movies" , method = RequestMethod.POST)
	public String doPostMovies(@RequestBody Movie movie) {
		movies.add(movie);
		return "new movie added";
	}
	
	// when we receive PUT request - take the request body and adjust it to Movie object
	// take the name parameter and adjust it to String
	// iterate over the movie list, if there's a match -
	// we will change the original movie fields to the new movie fields provided in the request
	@RequestMapping( value = "/movies/{name}", method = RequestMethod.PUT)
	public String doPutMovies(@RequestBody Movie sent, @PathVariable("name") String name)
	{
		for(Movie m : movies)
		{
			if (m.getName().equals(name))
			{
				m.setName( sent.getName());
				m.setRate( sent.getRate());
				m.setActors(sent.getActors());
				m.setImageUrl(sent.getImageUrl());
				return "UPDATED!";
			}
		}
		return "NOT FOUND!";
	}

	//when we receive DELETE request - take the name parameter and adjust it to String
	//iterate over the movie list, if there a match than delete the movie from the list
	@RequestMapping( value = "/movies/{name}", method = RequestMethod.DELETE)
	public String doDeleteMovies(@PathVariable("name") String name)
	{
		for(Movie m : movies)
		{
			if (m.getName().equals(name))
			{
				movies.remove(m);
				return "Movie deleted!";
			}
		}
		return "Movie not found!";
	}
	

}