package ru.smelyi.SnowflakeShop.models.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.smelyi.SnowflakeShop.models.database.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	boolean existsByLogin(String login);
	boolean existsByLoginAndPassword(String login, String password);

	User findByLogin(String login);
}