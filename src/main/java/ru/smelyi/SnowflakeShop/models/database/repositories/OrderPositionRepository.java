package ru.smelyi.SnowflakeShop.models.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.smelyi.SnowflakeShop.models.database.entities.OrderPosition;

public interface OrderPositionRepository extends JpaRepository<OrderPosition, Integer> {
}