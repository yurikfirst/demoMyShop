package ru.smelyi.SnowflakeShop.models.database.entities;

import javax.persistence.*;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "create_date", nullable = false)
	private Instant createDate;

	@Column(name = "status", nullable = false)
	private Integer status;

	@OneToMany(mappedBy = "order")
	private Set<OrderPosition> orderPositions = new LinkedHashSet<>();

	@Transient
	private String statusDescription = "";

	@Transient
	private Double totalPrice = 0d;

	@Transient
	private String userFullName = "";

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public Set<OrderPosition> getOrderPositions() {
		return orderPositions;
	}

	public void setOrderPositions(Set<OrderPosition> orderPositions) {
		this.orderPositions = orderPositions;
	}

	public Order() {
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Instant getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Instant createDate) {
		this.createDate = createDate;
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
		return "Order{" +
				"id=" + id +
				", user=" + user +
				", createDate=" + createDate +
				", status=" + status +
				'}';
	}
}