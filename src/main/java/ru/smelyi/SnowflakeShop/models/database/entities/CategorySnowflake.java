package ru.smelyi.SnowflakeShop.models.database.entities;

import javax.persistence.*;

@Entity
@Table(name = "category_snowflake")
public class CategorySnowflake {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "snowflake_id", nullable = false)
	private Snowflake snowflake;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	public CategorySnowflake() {
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Snowflake getSnowflake() {
		return snowflake;
	}

	public void setSnowflake(Snowflake snowflake) {
		this.snowflake = snowflake;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "CategorySnowflake{" +
				"id=" + id +
				", snowflake=" + snowflake +
				", category=" + category +
				'}';
	}
}