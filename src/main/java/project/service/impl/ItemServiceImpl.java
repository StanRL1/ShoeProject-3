package project.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.model.entities.Brand;
import project.model.entities.Item;
import project.model.services.ItemServiceModel;
import project.repository.BrandRepository;
import project.repository.CommentRepository;
import project.repository.ItemRepository;
import project.repository.UserRepository;
import project.service.BrandService;
import project.service.CommentService;
import project.service.ItemService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    private final ModelMapper modelMapper;
    private final ItemRepository itemRepository;
    private final CommentService commentService;
    private final UserRepository userRepository;
    private final BrandService brandService;
    @Autowired

    public ItemServiceImpl(ModelMapper modelMapper, ItemRepository itemRepository, UserRepository userRepository,CommentService commentService,BrandService brandService) {
        this.modelMapper = modelMapper;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.commentService=commentService;
        this.brandService=brandService;
    }


    @Override
    public void createItem(ItemServiceModel itemServiceModel) {
        Item item=this.modelMapper.map(itemServiceModel,Item.class);

        Brand brand=this.brandService.findByUsername("Nike");
        if(brand!=null){
            item.setBrand(brand);
        }else{
            brand=new Brand();
            brand.setName("Nike");
            item.setBrand(brand);
        }

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
       // this.commentService.deleteCommentsByItemId(id);
    }

    @Override
    public List<ItemServiceModel> findAllItemsForUser(String username) {
        return this.itemRepository.findAllByAddedBy(username).stream().map(item -> {
            return this.modelMapper.map(item,ItemServiceModel.class);
        }).collect(Collectors.toList());
    }

}
