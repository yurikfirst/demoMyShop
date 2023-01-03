package ru.smelyi.SnowflakeShop.models.database.entities;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "title", nullable = false, length = 100)
	private String title;

	@OneToMany(mappedBy = "role")
	private Set<RoleUser> roleUsers = new LinkedHashSet<>();

	public Role() {
	}

	public Set<RoleUser> getRoleUsers() {
		return roleUsers;
	}

	public void setRoleUsers(Set<RoleUser> roleUsers) {
		this.roleUsers = roleUsers;
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
		return "Role{" +
				"id=" + id +
				", title='" + title + '\'' +
				'}';
	}
}