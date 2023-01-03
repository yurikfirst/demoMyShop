package ru.smelyi.SnowflakeShop.models.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.smelyi.SnowflakeShop.models.database.entities.RoleUser;

public interface RoleUserRepository extends JpaRepository<RoleUser, Integer> {
}