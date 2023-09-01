package com.novare.shoppingcart.repository;

import com.novare.shoppingcart.model.TodoItem;
import com.novare.shoppingcart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {
    List<TodoItem> findAllByUser(User user);
    TodoItem save(User user);
}
