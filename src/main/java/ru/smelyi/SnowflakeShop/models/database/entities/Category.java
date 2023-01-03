package ru.smelyi.SnowflakeShop.models.database.entities;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "title", nullable = false, length = 100)
	private String title;

	@OneToMany(mappedBy = "category")
	private Set<CategorySnowflake> categorySnowflakes = new LinkedHashSet<>();

	@Transient
	private boolean checked = false;

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Category() {
	}

	public Set<CategorySnowflake> getCategorySnowflakes() {
		return categorySnowflakes;
	}

	public void setCategorySnowflakes(Set<CategorySnowflake> categorySnowflakes) {
		this.categorySnowflakes = categorySnowflakes;
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
		return "Category{" +
				"id=" + id +
				", title='" + title + '\'' +
				'}';
	}
}