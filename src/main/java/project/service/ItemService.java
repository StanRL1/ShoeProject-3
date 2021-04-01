package project.service;

import project.model.services.ItemServiceModel;

import java.util.List;

public interface ItemService {
    void createItem(ItemServiceModel itemServiceModel);
    List<ItemServiceModel> findAllItems();
    ItemServiceModel findById(Long id);

    void deleteById(Long id);

    List<ItemServiceModel> findAllItemsForUser(String username);
}
