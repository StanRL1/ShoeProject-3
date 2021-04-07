package project.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import project.model.entities.Brand;
import project.model.entities.Item;
import project.model.entities.enums.Gender;
import project.model.services.ItemServiceModel;
import project.repository.BrandRepository;
import project.repository.ItemRepository;
import project.service.FrontPageService;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Service
public class FrontPageServiceImpl implements FrontPageService {

    private final ItemRepository itemRepository;
    private final BrandRepository brandRepository;
    private final ModelMapper modelMapper;
    private List<Item> items;
    @Autowired

    public FrontPageServiceImpl(ItemRepository itemRepository, ModelMapper modelMapper,BrandRepository brandRepository) {
        this.itemRepository = itemRepository;
        this.modelMapper = modelMapper;
        this.brandRepository=brandRepository;
        this.items = this.itemRepository.findAll();
    }

    @PostConstruct
    public void afterInit(){
        if(this.itemRepository.findAllimgUrl().size()<3){
            Brand brand=new Brand();
            brand.setName("Nike");
            this.brandRepository.saveAndFlush(brand);
            System.out.println();
            Item item1= new Item();
            item1.setId(1);
            item1.setPrice(BigDecimal.valueOf(235));
            item1.setGender(Gender.MALE);
            item1.setDescription("Best Shoe Ever");
            item1.setAddedBy("admin");
            item1.setName("Air Max");
            item1.setBrand(this.brandRepository.findByName("Nike").get());
            item1.setImgUrl("https://www.shooos.bg/media/catalog/product/cache/20/image/9df78eab33525d08d6e5fb8d27136e95/n/i/nike-air-force-1-flyknit-2.0-av3042-0011.jpg");
            itemRepository.saveAndFlush(item1);

            Item item2= new Item();
            item2.setId(2);
            item2.setPrice(BigDecimal.valueOf(234));
            item2.setGender(Gender.MALE);
            item2.setDescription("Best Shoe Ever");
            item2.setAddedBy("admin");
            item2.setName("Air Max");
            item2.setBrand(this.brandRepository.findByName("Nike").get());
            item2.setImgUrl("https://www.shooos.bg/media/catalog/product/cache/20/image/9df78eab33525d08d6e5fb8d27136e95/n/i/nike-air-force-1-flyknit-2.0-av3042-0011.jpg");
            itemRepository.saveAndFlush(item2);

            Item item3=new Item();
            item3.setImgUrl("https://www.shooos.bg/media/catalog/product/cache/20/image/9df78eab33525d08d6e5fb8d27136e95/n/i/nike-air-force-1-flyknit-2.0-av3042-0011.jpg");
            item3.setName("Air ForceFlyknit");
            item3.setAddedBy("admin");
            item3.setDescription("You will fall in love with this shoe");
            item3.setGender(Gender.FEMALE);
            item3.setPrice(BigDecimal.valueOf(200));
            item3.setBrand(this.brandRepository.findByName("Nike").get());
            item3.setId(3);
            this.itemRepository.saveAndFlush(item3);
            items=itemRepository.findAll();
            System.out.println();
            if(items.size()<3){
                throw new IllegalArgumentException("not enough offers");
            }
        }
    }

    @Override
    public ItemServiceModel firstImage() {
        System.out.println();
        Item itemServiceModel=this.items.get(0);
        return this.modelMapper.map(itemServiceModel,ItemServiceModel.class);
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
