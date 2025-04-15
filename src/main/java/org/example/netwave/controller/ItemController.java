package org.example.netwave.controller;

import org.example.netwave.dto.ItemDTO;
import org.example.netwave.entity.Supplier;
import org.example.netwave.repo.SupplierRepo;
import org.example.netwave.service.ItemService;
import org.example.netwave.utill.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private SupplierRepo supplierRepo;

    private static final String UPLOAD_DIR = "uploads/";

    @PostMapping(value = "/save", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil saveItem(@RequestPart("item") ItemDTO itemDTO,
                                 @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            if (image != null && !image.isEmpty()) {
                String fileName = saveImage(image);
                itemDTO.setImage(fileName);
            }

            itemService.saveItem(itemDTO);
            return new ResponseUtil(201, "Item saved successfully", itemDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseUtil(500, "Failed to save item: " + e.getMessage(), null);
        }
    }

    @GetMapping("/getAll")
    public ResponseUtil getAllItems() {
        List<ItemDTO> items = itemService.getAllItems();
        return new ResponseUtil(200, "All items retrieved", items);
    }

    @GetMapping("/getById/{id}")
    public ResponseUtil getItemById(@PathVariable int id) {
        ItemDTO item = itemService.getItemById(id);
        return new ResponseUtil(200, "Item found", item);
    }

    @GetMapping("/getByName/{name}")
    public ResponseUtil getItemByName(@PathVariable String name) {
        ItemDTO item = itemService.getItemByName(name);
        return new ResponseUtil(200, "Item found", item);
    }

    @PutMapping(value = "/update/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseUtil updateItem(@PathVariable int id,
                                   @RequestParam("name") String name,
                                   @RequestParam("description") String description,
                                   @RequestParam(value = "price", required = false, defaultValue = "0") double price,
                                   @RequestParam(value = "category", required = false) String category,
                                   @RequestParam(value = "stockQuantity", required = false, defaultValue = "0") int stockQuantity,
                                   @RequestParam(value = "supplierId", required = false, defaultValue = "0") int supplierId,
                                   @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            ItemDTO existingItem = itemService.getItemById(id);
            if (existingItem == null) {
                return new ResponseUtil(404, "Item not found", null);
            }

            existingItem.setName(name);
            existingItem.setDescription(description);

            if (price > 0) existingItem.setPrice(price);
            if (category != null && !category.isEmpty()) existingItem.setCategory(category);
            if (stockQuantity > 0) existingItem.setStockQuantity(stockQuantity);
            if (supplierId > 0) existingItem.setSupplierId(supplierId);

            if (image != null && !image.isEmpty()) {
                if (existingItem.getImage() != null && !existingItem.getImage().isEmpty()) {
                    Path oldImagePath = Paths.get(UPLOAD_DIR + existingItem.getImage());
                    Files.deleteIfExists(oldImagePath);
                }

                String newFileName = saveImage(image);
                existingItem.setImage(newFileName);
            }

            itemService.updateItem(existingItem);
            return new ResponseUtil(200, "Item updated successfully", existingItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseUtil(500, "Failed to update item: " + e.getMessage(), null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseUtil deleteItem(@PathVariable int id) {
        try {
            ItemDTO existingItem = itemService.getItemById(id);
            if (existingItem == null) {
                return new ResponseUtil(404, "Item not found", null);
            }

            String imagePath = existingItem.getImage();
            if (imagePath != null && !imagePath.isEmpty()) {
                Path filePath = Paths.get(UPLOAD_DIR + imagePath);
                Files.deleteIfExists(filePath);
            }

            itemService.deleteItem(id);
            return new ResponseUtil(200, "Item deleted successfully", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseUtil(500, "Failed to delete item: " + e.getMessage(), null);
        }
    }

    private String saveImage(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        Path destination = Paths.get(UPLOAD_DIR + fileName);
        Files.copy(file.getInputStream(), destination);
        return fileName;
    }
}