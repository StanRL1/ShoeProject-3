package project.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import project.model.entities.Item;
import project.model.entities.enums.Gender;
import project.model.services.ItemServiceModel;
import project.repository.ItemRepository;
import project.service.FrontPageService;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Service
public class FrontPageServiceImpl implements FrontPageService {

    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;
    private List<Item> items;
    @Autowired

    public FrontPageServiceImpl(ItemRepository itemRepository, ModelMapper modelMapper) {
        this.itemRepository = itemRepository;
        this.modelMapper = modelMapper;
        this.items = this.itemRepository.findAll();
    }

    @PostConstruct
    public void afterInit(){
        if(this.itemRepository.findAllimgUrl().size()<3){
            Item item1= new Item();
            item1.setId(1);
            item1.setPrice(BigDecimal.valueOf(235));
            item1.setGender(Gender.MALE);
            item1.setDescription("Best Shoe Ever");
            item1.setAddedBy("admin");
            item1.setName("Air Max");
            item1.setImgUrl("https://www.shooos.bg/media/catalog/product/cache/20/image/9df78eab33525d08d6e5fb8d27136e95/n/i/nike-air-force-1-flyknit-2.0-av3042-0011.jpg");
            itemRepository.saveAndFlush(item1);
            if(itemRepository.findAllimgUrl().size()<3){
                throw new IllegalArgumentException("not enough offers");
            }
        }
    }

    @Override
    public ItemServiceModel firstImage() {

        return this.modelMapper.map(this.items.get(0),ItemServiceModel.class);
    }

    @Override
    public ItemServiceModel secondImage() {
     return this.modelMapper.map(this.items.get(1),ItemServiceModel.class);
    }

    @Override
    public ItemServiceModel thirdImage() {
        return this.modelMapper.map(this.items.get(2),ItemServiceModel.class);
    }

    @Scheduled(cron="0 0 * * * *")
    public void refresh(){
        Collections.shuffle(items);
    }
    @Override
    public void reload(){
        this.items=this.itemRepository.findAll();
    }
}
