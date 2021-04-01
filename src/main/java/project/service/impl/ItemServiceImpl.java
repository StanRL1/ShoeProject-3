package project.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.model.entities.Item;
import project.model.services.ItemServiceModel;
import project.repository.ItemRepository;
import project.repository.UserRepository;
import project.service.ItemService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    private final ModelMapper modelMapper;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    @Autowired

    public ItemServiceImpl(ModelMapper modelMapper, ItemRepository itemRepository, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }


    @Override
    public void createItem(ItemServiceModel itemServiceModel) {
        Item item=this.modelMapper.map(itemServiceModel,Item.class);
        System.out.println();

        this.itemRepository.saveAndFlush(item);
    }

    @Override
    public List<ItemServiceModel> findAllItems() {

        return this.itemRepository.findAll().stream().map(item -> {
            ItemServiceModel itemServiceModel=this.modelMapper.map(item,ItemServiceModel.class);
            return itemServiceModel;
        }).collect(Collectors.toList());
    }

    @Override
    public ItemServiceModel findById(Long id) {
        return this.itemRepository.findById(id).map(item->{
            ItemServiceModel itemServiceModel=this.modelMapper.map(item,ItemServiceModel.class);
            return itemServiceModel;
        }).orElseThrow(()->new IllegalCallerException("Item not found"));
    }

    @Override
    public void deleteById(Long id) {
        this.itemRepository.deleteById(id);
    }

    @Override
    public List<ItemServiceModel> findAllItemsForUser(String username) {
        return this.itemRepository.findAllByAddedBy(username).stream().map(item -> {
            return this.modelMapper.map(item,ItemServiceModel.class);
        }).collect(Collectors.toList());
    }

}
