package com.example.hospital_backend.Item;

import java.time.LocalDateTime;
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
@RequestMapping("/api/items")
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
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
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
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody Item ItemRequest) {
        Optional<Item> existingItem = ItemService.getItemById(id);
        if (existingItem.isPresent()) {

            if(ItemRequest.getQuantity() <= 0) {
                ItemService.deleteItem(id);
                return ResponseEntity.noContent().build();
            }

            Item existingItemEntity = existingItem.get();
            Item updatedItemEntity = new Item(existingItemEntity.getId(),
                    existingItemEntity.getInventory_id(),
                    ItemRequest.getQuantity(),
                    existingItemEntity.getCreatedAt(),
                    LocalDateTime.now());

            Item updatedItem = ItemService.saveItem(updatedItemEntity);
            return ResponseEntity.ok(updatedItem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a Item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        Optional<Item> existingItem = ItemService.getItemById(id);
        if (existingItem.isPresent()) {
            ItemService.deleteItem(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
