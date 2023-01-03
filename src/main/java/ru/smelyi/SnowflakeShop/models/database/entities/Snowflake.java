package ru.smelyi.SnowflakeShop.models.database.entities;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "snowflakes")
public class Snowflake {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "title", nullable = false, length = 100)
	private String title;

	@Column(name = "description", nullable = false, length = 1000)
	private String description;

	@Column(name = "price", nullable = false)
	private Double price;

	@Column(name = "image")
	private byte[] image;

	@OneToMany(mappedBy = "snowflake")
	private Set<CategorySnowflake> categorySnowflakes = new LinkedHashSet<>();

	@Column(name = "count", nullable = false)
	private Integer count;

	@OneToMany(mappedBy = "snowflake")
	private Set<Cart> carts = new LinkedHashSet<>();

	@OneToMany(mappedBy = "snowflake")
	private Set<OrderPosition> orderPositions = new LinkedHashSet<>();

	@Transient
	private boolean isInCart;

	@Transient
	private int countInCart;

	@Transient
	private List<Category> categories = new ArrayList<>();

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public Set<OrderPosition> getOrderPositions() {
		return orderPositions;
	}

	public void setOrderPositions(Set<OrderPosition> orderPositions) {
		this.orderPositions = orderPositions;
	}

	public int getCountInCart() {
		return countInCart;
	}

	public void setCountInCart(int countInCart) {
		this.countInCart = countInCart;
	}

	public boolean isInCart() {
		return isInCart;
	}

	public void setInCart(boolean inCart) {
		isInCart = inCart;
	}

	public Set<Cart> getCarts() {
		return carts;
	}

	public void setCarts(Set<Cart> carts) {
		this.carts = carts;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Snowflake() {
	}

	public Set<CategorySnowflake> getCategorySnowflakes() {
		return categorySnowflakes;
	}

	public void setCategorySnowflakes(Set<CategorySnowflake> categorySnowflakes) {
		this.categorySnowflakes = categorySnowflakes;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Snowflake{" +
				"id=" + id +
				", title='" + title + '\'' +
				", description='" + description + '\'' +
				", price=" + price +
				", image=" + Arrays.toString(image) +
				'}';
	}
}