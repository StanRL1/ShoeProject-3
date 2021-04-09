package project.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import project.exeptions.ObjectNotFoundException;
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
            Brand brand = new Brand();
            if(brandRepository.findAll().size()<1) {
                brand.setName("Nike");
                this.brandRepository.saveAndFlush(brand);

            }
            Item item1= new Item();
            item1.setId(1);
            item1.setPrice(BigDecimal.valueOf(235));
            item1.setGender(Gender.MALE);
            item1.setDescription("Best Shoe Ever");
            item1.setAddedBy("admin");
            item1.setName("Air Max");
            item1.setBrand(this.brandRepository.findByName("Nike").orElseThrow(ObjectNotFoundException::new));
            item1.setImgUrl("https://static.nike.com/a/images/c_limit,w_592,f_auto/t_product_v1/5b337ffe-0fda-42d5-8fa7-fc9511987930/air-max-97-womens-shoe-XD9m01.png");
            itemRepository.saveAndFlush(item1);

            Item item2= new Item();
            item2.setId(2);
            item2.setPrice(BigDecimal.valueOf(234));
            item2.setGender(Gender.MALE);
            item2.setDescription("Best Shoe Ever");
            item2.setAddedBy("admin");
            item2.setName("Air Max 90");
            item2.setBrand(this.brandRepository.findByName("Nike").get());
            item2.setImgUrl("https://static.nike.com/a/images/t_PDP_864_v1/f_auto,b_rgb:f5f5f5/73e3275e-c0a9-4556-9383-cd95fb09ed87/air-max-90-shoe-wV1m7n.png");
            itemRepository.saveAndFlush(item2);

            Item item3=new Item();
            item3.setImgUrl("https://www.obuvki.bg/media/catalog/product/cache/image/650x650/0/0/0000199870750_1__jf.jpg");
            item3.setName("Air ForceFlyknit Mid");
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

    @Override
    public void init() {
        afterInit();
    }
}
