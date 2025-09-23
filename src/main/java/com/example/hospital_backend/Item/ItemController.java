package com.example.hospital_backend.Item;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/Items")
@CrossOrigin(origins = "*")
public class ItemController {

    @Autowired
    private ItemService ItemService;

    // Get all Items
    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> Items = ItemService.getAllItems();
        return ResponseEntity.ok(Items);
    }

    // Get Item by ID
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable String id) {
        Optional<Item> Item = ItemService.getItemById(id);
        return Item.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // Create a new Item
    @PostMapping("/create")
    public ResponseEntity<Item> createItem(@RequestBody Item Item) {
        try {
            Item savedItem = ItemService.saveItem(Item);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Update an existing Item
    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable String id, @RequestBody Item Item) {
        Optional<Item> existingItem = ItemService.getItemById(id);
        if (existingItem.isPresent()) {
            Item.setId(id); // Ensure the ID is set correctly
            Item updatedItem = ItemService.saveItem(Item);
            return ResponseEntity.ok(updatedItem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a Item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable String id) {
        Optional<Item> existingItem = ItemService.getItemById(id);
        if (existingItem.isPresent()) {
            ItemService.deleteItem(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
