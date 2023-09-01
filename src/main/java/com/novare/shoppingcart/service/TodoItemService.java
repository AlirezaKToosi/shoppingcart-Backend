package com.novare.shoppingcart.service;

import com.novare.shoppingcart.model.TodoItem;
import com.novare.shoppingcart.model.User;
import com.novare.shoppingcart.repository.TodoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoItemService {
    @Autowired
    private TodoItemRepository todoItemRepository;

    public List<TodoItem> getTodoItemsByUser(User user) {
        return todoItemRepository.findAllByUser(user);
    }
    public boolean deleteItem(Long itemId) {
        Optional<TodoItem> itemOptional = todoItemRepository.findById(itemId);
        if (itemOptional.isPresent()) {
            todoItemRepository.delete(itemOptional.get());
            return true;
        }
        return false;
    }

    public TodoItem addItem(TodoItem newItem) {
        return todoItemRepository.save(newItem);
    }
}
