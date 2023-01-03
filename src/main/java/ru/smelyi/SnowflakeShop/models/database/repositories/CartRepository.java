package ru.smelyi.SnowflakeShop.models.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.smelyi.SnowflakeShop.models.database.entities.*;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
	boolean existsByUserAndSnowflake(User user, Snowflake snowflake);

	Cart findByUserAndSnowflake(User user, Snowflake snowflake);

	List<Cart> findAllByUser(User user);

	void deleteByUser(User user);
}