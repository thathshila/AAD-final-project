package org.example.netwave.service.impl;

import jakarta.transaction.Transactional;
import org.example.netwave.dto.ItemDTO;
import org.example.netwave.entity.Item;
import org.example.netwave.entity.Supplier;
import org.example.netwave.repo.ItemRepo;
import org.example.netwave.repo.SupplierRepo;
import org.example.netwave.service.ItemService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private SupplierRepo supplierRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    @Override
    public void saveItem(ItemDTO itemDTO) {
        // Convert DTO to entity
        Item item = modelMapper.map(itemDTO, Item.class);

        // Handle the supplier relationship
        if (itemDTO.getSupplierId() > 0) {
            Optional<Supplier> supplier = supplierRepo.findById(itemDTO.getSupplierId());
            supplier.ifPresent(item::setSupplier);
        }

        // Save the item
        itemRepo.save(item);

        // Update the DTO with generated ID
        itemDTO.setItemId(item.getItemId());
    }

    @Override
    public List<ItemDTO> getAllItems() {
        List<Item> items = itemRepo.findByIsDeletedFalse();
        return modelMapper.map(items, new TypeToken<List<ItemDTO>>() {}.getType());
    }

    @Override
    public ItemDTO getItemByName(String name) {
        Item item = itemRepo.findByName(name)
                .orElseThrow(() -> new RuntimeException("Item not found with name: " + name));
        return modelMapper.map(item, ItemDTO.class);
    }

    @Override
    public ItemDTO getItemById(int id) {
        Item item = itemRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));
        return modelMapper.map(item, ItemDTO.class);
    }

    @Override
    public int getTotalItemCount() {
        return itemRepo.countItem();
    }

    @Transactional
    @Override
    public void updateItem(ItemDTO itemDTO) {
        // Check if item exists
        if (!itemRepo.existsById(itemDTO.getItemId())) {
            throw new RuntimeException("Item does not exist with id: " + itemDTO.getItemId());
        }

        // Convert DTO to entity
        Item item = modelMapper.map(itemDTO, Item.class);

        // Handle the supplier relationship
        if (itemDTO.getSupplierId() > 0) {
            Optional<Supplier> supplier = supplierRepo.findById(itemDTO.getSupplierId());
            supplier.ifPresent(item::setSupplier);
        }

        // Save the item
        itemRepo.save(item);
    }

    @Transactional
    @Override
    public void deleteItem(int id) {
        Item item = itemRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));
        item.setDeleted(true); // Soft delete
        itemRepo.save(item);
    }
}