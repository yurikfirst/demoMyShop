package ru.smelyi.SnowflakeShop.models.database.entities;

import javax.persistence.*;

@Entity
@Table(name = "cart")
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "snowflake_id", nullable = false)
	private Snowflake snowflake;

	@Column(name = "count", nullable = false)
	private Integer count;

	public Cart() {
	}

	public Cart(User user, Snowflake snowflake, Integer count) {
		this.user = user;
		this.snowflake = snowflake;
		this.count = count;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Snowflake getSnowflake() {
		return snowflake;
	}

	public void setSnowflake(Snowflake snowflake) {
		this.snowflake = snowflake;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Cart{" +
				"id=" + id +
				", user=" + user +
				", snowflake=" + snowflake +
				", count=" + count +
				'}';
	}
}