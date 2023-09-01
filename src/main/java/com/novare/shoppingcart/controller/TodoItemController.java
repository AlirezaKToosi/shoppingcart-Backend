package com.novare.shoppingcart.controller;


import com.novare.shoppingcart.model.Image;
import com.novare.shoppingcart.model.TodoItem;
import com.novare.shoppingcart.model.TodoItemDTO;
import com.novare.shoppingcart.model.User;
import com.novare.shoppingcart.service.ImageService;
import com.novare.shoppingcart.service.TodoItemService;
import com.novare.shoppingcart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/todo")
public class TodoItemController {
    @Autowired
    private TodoItemService todoItemService;

    @Autowired
    private UserService userService; // Add this line
    @Autowired
    private ImageService imageService;



    // display image
    @GetMapping("/display")
    public ResponseEntity<byte[]> displayImage(@RequestParam("id") long id) throws IOException, SQLException
    {
        Image image = imageService.viewById(id);
        byte [] imageBytes;
        imageBytes = image.getImage().getBytes(1,(int) image.getImage().length());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }

    @GetMapping("/{userEmail}")
    public ResponseEntity<List<TodoItemDTO>> getTodoItems(@PathVariable String userEmail) {
        // Fetch the user using the email
        User user = userService.getUserByEmail(userEmail);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<TodoItem> todoItems = todoItemService.getTodoItemsByUser(user);

        // Convert TodoItem objects to TodoItemDTO objects
        List<TodoItemDTO> todoItemsDTO = todoItems.stream()
                .map(item -> new TodoItemDTO(item.getTitle(), String.valueOf(item.getPrice()),
                        String.valueOf(item.getImage().getId())))
                .collect(Collectors.toList());

        return ResponseEntity.ok(todoItemsDTO);
    }
    @DeleteMapping("/deleteItem/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable Long itemId) {
        if (todoItemService.deleteItem(itemId)) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Item deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found.");
        }
    }
    @PostMapping("/addItem/{userEmail}")
    public ResponseEntity<TodoItem> addItem(
            @RequestParam("title") String title,
            @RequestParam("price") double price,
            @RequestParam("image") MultipartFile file,
            @PathVariable String userEmail
    ) throws IOException, SerialException, SQLException{
        User existingUser = userService.getUserByEmail(userEmail);
        if (existingUser != null) {

            try {
                byte[] bytes = file.getBytes();
                Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
                Image image = new Image();
                image.setImage(blob);
                imageService.create(image);
                // Create and save the TodoItem with the image path
                TodoItem newItem = new TodoItem(title, price, image);
                newItem.setUser(existingUser); // Set the user for the new TodoItem
                // Now, save the new item to the database
                TodoItem addedItem = todoItemService.addItem(newItem);
                  return ResponseEntity.status(HttpStatus.CREATED).body(addedItem);

            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
