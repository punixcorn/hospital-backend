package com.example.hospital_backend.Item;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    @Autowired
    ItemRepository ItemRepository;

    public Optional<Item> getItemById(String id) {
        return ItemRepository.findById(id);
    }

    public List<Item> getAllItems() {
        return ItemRepository.findAll();
    }

    public Item saveItem(Item Item) {
        return ItemRepository.save(Item);
    }

    public void deleteItem(String id) {
        ItemRepository.deleteById(id);
    }

}
