package ru.smelyi.SnowflakeShop.models.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.smelyi.SnowflakeShop.models.database.entities.CategorySnowflake;
import ru.smelyi.SnowflakeShop.models.database.entities.Snowflake;

import java.util.List;

public interface CategorySnowflakeRepository extends JpaRepository<CategorySnowflake, Integer> {
	List<CategorySnowflake> findAllBySnowflake(Snowflake snowflake);
}