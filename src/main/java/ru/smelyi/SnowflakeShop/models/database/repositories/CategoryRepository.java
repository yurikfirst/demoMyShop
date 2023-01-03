package ru.smelyi.SnowflakeShop.models.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.smelyi.SnowflakeShop.models.database.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}