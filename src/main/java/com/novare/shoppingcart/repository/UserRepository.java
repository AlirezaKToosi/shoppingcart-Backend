package com.novare.shoppingcart.repository;

import com.novare.shoppingcart.model.TodoItem;
import com.novare.shoppingcart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
    User save(TodoItem item);

}
