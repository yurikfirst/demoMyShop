package ru.smelyi.SnowflakeShop.models.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.smelyi.SnowflakeShop.models.database.entities.Snowflake;

import java.util.List;

public interface SnowflakeRepository extends JpaRepository<Snowflake, Integer> {
	List<Snowflake> findAllByTitleContaining(String title);
}