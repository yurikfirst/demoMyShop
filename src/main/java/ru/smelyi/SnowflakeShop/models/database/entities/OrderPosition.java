package ru.smelyi.SnowflakeShop.models.database.entities;

import javax.persistence.*;

@Entity
@Table(name = "order_positions")
public class OrderPosition {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "snowflake_id", nullable = false)
	private Snowflake snowflake;

	@Column(name = "count", nullable = false)
	private Integer count;

	@Column(name = "price_one", nullable = false)
	private Double priceOne;

	@Transient
	private String snowflakeTitle = "";

	@Transient
	private int snowflakeId = 0;

	public int getSnowflakeId() {
		return snowflakeId;
	}

	public void setSnowflakeId(int snowflakeId) {
		this.snowflakeId = snowflakeId;
	}

	public String getSnowflakeTitle() {
		return snowflakeTitle;
	}

	public void setSnowflakeTitle(String snowflakeTitle) {
		this.snowflakeTitle = snowflakeTitle;
	}

	public OrderPosition() {
	}

	public Double getPriceOne() {
		return priceOne;
	}

	public void setPriceOne(Double priceOne) {
		this.priceOne = priceOne;
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

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "OrderPosition{" +
				"id=" + id +
				", order=" + order +
				", snowflake=" + snowflake +
				", count=" + count +
				", priceOne=" + priceOne +
				'}';
	}
}