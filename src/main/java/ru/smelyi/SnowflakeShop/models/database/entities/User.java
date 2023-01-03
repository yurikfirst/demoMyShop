package ru.smelyi.SnowflakeShop.models.database.entities;

import javax.persistence.*;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "login", nullable = false, length = 20)
	private String login;

	@Column(name = "password", nullable = false, length = 128)
	private String password;

	@Column(name = "full_name", nullable = false, length = 100)
	private String fullName;

	@Column(name = "email", length = 100)
	private String email;

	@Column(name = "registration_date", nullable = false, insertable = false, updatable = false)
	private Instant registrationDate;

	@Column(name = "last_login_date")
	private Instant lastLoginDate;

	@OneToMany(mappedBy = "user")
	private Set<RoleUser> roleUsers = new LinkedHashSet<>();

	@OneToMany(mappedBy = "user")
	private Set<Order> orders = new LinkedHashSet<>();

	@OneToMany(mappedBy = "user")
	private Set<Cart> carts = new LinkedHashSet<>();

	@Column(name = "phone", length = 20)
	private String phone;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Set<Cart> getCarts() {
		return carts;
	}

	public void setCarts(Set<Cart> carts) {
		this.carts = carts;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	public User() {
	}

	public User(String login, String password, String fullName, String email, Instant lastLoginDate) {
		this.login = login;
		this.password = password;
		this.fullName = fullName;
		this.email = email;
		this.lastLoginDate = lastLoginDate;
	}

	public Set<RoleUser> getRoleUsers() {
		return roleUsers;
	}

	public void setRoleUsers(Set<RoleUser> roleUsers) {
		this.roleUsers = roleUsers;
	}

	public Instant getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Instant lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Instant getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Instant registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", login='" + login + '\'' +
				", password='" + password + '\'' +
				", fullName='" + fullName + '\'' +
				", email='" + email + '\'' +
				", registrationDate=" + registrationDate +
				", lastLoginDate=" + lastLoginDate +
				'}';
	}
}