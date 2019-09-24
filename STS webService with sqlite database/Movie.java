package nitzan.mor.project;

import java.io.Serializable;

// pojo class
public class Movie  implements Serializable {
	
	private String name;
	private String rate;
	private String actors;
	private String imageUrl;
	
	
	public Movie() {
		super();
	}


	public Movie(String name, String rate, String actors,String imageUrl) {
		super();
		this.imageUrl = imageUrl;
		this.name = name;
		this.rate = rate;
		this.actors = actors;
	}


	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getRate() {
		return rate;
	}


	public void setRate(String rate) {
		this.rate = rate;
	}


	public String getActors() {
		return actors;
	}


	public void setActors(String actors) {
		this.actors = actors;
	}
	
	@Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", rate='" + rate + '\'' +
                ", actors='" + actors + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

}
