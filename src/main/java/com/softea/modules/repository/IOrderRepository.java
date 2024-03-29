/**
 * @Author xSallus
 * @Version 0.3.0
**/

package com.softea.modules.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import com.softea.modules.dto.OrderDTO;
import com.softea.modules.entity.Order;

public interface IOrderRepository {
	List<Order> findAll();
	List<Order> findByCreatedAt(
		LocalDateTime createdAt);
	Optional<Order> findById(String id);
	Order save(OrderDTO dto);
	Order patch(Order order);
}
