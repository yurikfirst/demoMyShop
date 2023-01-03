package ru.smelyi.SnowflakeShop.models.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.smelyi.SnowflakeShop.models.database.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}