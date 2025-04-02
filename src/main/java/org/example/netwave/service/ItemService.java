package org.example.netwave.service;

import jakarta.transaction.Transactional;
import org.example.netwave.dto.ItemDTO;

import java.util.List;

public interface ItemService {

    @Transactional
    void saveItem(ItemDTO itemDTO);

    List<ItemDTO> getAllItems();

    ItemDTO getItemByName(String name);

    void updateItem(ItemDTO itemDTO);

    void deleteItem(int id);

    ItemDTO getItemById(int id);

    int getTotalItemCount();
}
