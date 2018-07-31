package entity;

import java.sql.Date;
import java.util.UUID;

public class Comic {
	private UUID comicId;
	private String title;
	private Integer categoryId;
	private Integer price;
	private String publisher;
	private String authorName;
	private Date releaseDate;
	private String synopsis;
	private String link;
	private String image;
	private String createdUser;
	private Date createdDate;
	private String modifiedUser;
	private Date modifiedDate;

	public Comic() {

	}

	public Comic(UUID comicId, String title, Integer categoryId, Integer price, String publisher, String authorName,
			Date releaseDate, String synopsis, String link, String image, String createdUser, Date createdDate,
			String modifiedUser, Date modifiedDate) {
		this.comicId = comicId;
		this.title = title;
		this.categoryId = categoryId;
		this.price = price;
		this.publisher = publisher;
		this.authorName = authorName;
		this.releaseDate = releaseDate;
		this.synopsis = synopsis;
		this.link = link;
		this.image = image;
		this.createdUser = createdUser;
		this.createdDate = createdDate;
		this.modifiedUser = modifiedUser;
		this.modifiedDate = modifiedDate;
	}

	public UUID getComicId() {
		return comicId;
	}

	public void setComicId(UUID comicId) {
		this.comicId = comicId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}


}

