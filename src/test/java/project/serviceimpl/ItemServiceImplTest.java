package project.serviceimpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import project.model.entities.Item;
import project.model.entities.UserEntity;
import project.model.entities.enums.Gender;
import project.model.services.ItemServiceModel;
import project.repository.BrandRepository;
import project.repository.ItemRepository;
import project.repository.UserRepository;
import project.service.BrandService;
import project.service.impl.CommentServiceImpl;
import project.service.impl.ItemServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {

    private ItemServiceImpl serviceToTest;
    private CommentServiceImpl commentService;
    private Item item1,item2;
    private UserEntity user1,user2;
    private BrandService brandService;
    @Mock
    ItemRepository mockItemRepository;
    @Mock
    UserRepository mockUserRepository;

    @BeforeEach
    public void init(){
        item1 = new Item();
        item1.setName("item1");
        item1.setAddedBy("addedby1");
        item1.setDescription("11111111111");
        item1.setGender(Gender.FEMALE);
        item1.setImgUrl("1111111111111");
        item1.setPrice(BigDecimal.TEN);
        item1.setId(1);
        item2 = new Item();
        item2.setName("item2");
        item2.setAddedBy("addedby2");
        item2.setDescription("22222222222");
        item2.setGender(Gender.MALE);
        item2.setImgUrl("222222222222");
        item2.setPrice(BigDecimal.TEN);
        item2.setId(2);

        user1=new UserEntity();
        user1.setUsername("user1");

        user2=new UserEntity();
        user2.setUsername("user2");

        serviceToTest=new ItemServiceImpl(new ModelMapper(),mockItemRepository,mockUserRepository,commentService,brandService);

    }
    @Test
    public void testDeleteItem(){
        mockItemRepository.saveAndFlush(item1);
        serviceToTest.deleteById(item1.getId());

        Assertions.assertEquals(0, mockItemRepository.count());

    }

    @Test
    public void testFindById(){
        when(mockItemRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(item1));

        ItemServiceModel testItem=serviceToTest.findById(Long.valueOf(1));

        Assertions.assertEquals(item1.getAddedBy(),testItem.getAddedBy());
        Assertions.assertEquals(item1.getDescription(),testItem.getDescription());
        Assertions.assertEquals(item1.getGender(),testItem.getGender());
        Assertions.assertEquals(item1.getImgUrl(),testItem.getImgUrl());
        Assertions.assertEquals(item1.getName(),testItem.getName());
        Assertions.assertEquals(item1.getPrice(),testItem.getPrice());

    }

    @Test
    public void testFindAllItems(){
        when(mockItemRepository.findAll()).thenReturn(List.of(item1,item2));

        List<ItemServiceModel> testItems=this.serviceToTest.findAllItems();

        Assertions.assertEquals(item1.getAddedBy(),testItems.get(0).getAddedBy());
        Assertions.assertEquals(item1.getDescription(),testItems.get(0).getDescription());
        Assertions.assertEquals(item1.getGender(),testItems.get(0).getGender());
        Assertions.assertEquals(item1.getImgUrl(),testItems.get(0).getImgUrl());
        Assertions.assertEquals(item1.getName(),testItems.get(0).getName());
        Assertions.assertEquals(item1.getPrice(),testItems.get(0).getPrice());

        Assertions.assertEquals(item2.getAddedBy(),testItems.get(1).getAddedBy());
        Assertions.assertEquals(item2.getDescription(),testItems.get(1).getDescription());
        Assertions.assertEquals(item2.getGender(),testItems.get(1).getGender());
        Assertions.assertEquals(item2.getImgUrl(),testItems.get(1).getImgUrl());
        Assertions.assertEquals(item2.getName(),testItems.get(1).getName());
        Assertions.assertEquals(item2.getPrice(),testItems.get(1).getPrice());

        Assertions.assertEquals(2,testItems.size());

    }

    @Test
    public void testAllItemsForUser(){
        when(mockItemRepository.findAllByAddedBy("sami")).thenReturn(List.of(item1,item2));

        List<ItemServiceModel> testItems=this.serviceToTest.findAllItemsForUser("sami");

        Assertions.assertEquals(item1.getAddedBy(),testItems.get(0).getAddedBy());
        Assertions.assertEquals(item1.getDescription(),testItems.get(0).getDescription());
        Assertions.assertEquals(item1.getGender(),testItems.get(0).getGender());
        Assertions.assertEquals(item1.getImgUrl(),testItems.get(0).getImgUrl());
        Assertions.assertEquals(item1.getName(),testItems.get(0).getName());
        Assertions.assertEquals(item1.getPrice(),testItems.get(0).getPrice());

        Assertions.assertEquals(item2.getAddedBy(),testItems.get(1).getAddedBy());
        Assertions.assertEquals(item2.getDescription(),testItems.get(1).getDescription());
        Assertions.assertEquals(item2.getGender(),testItems.get(1).getGender());
        Assertions.assertEquals(item2.getImgUrl(),testItems.get(1).getImgUrl());
        Assertions.assertEquals(item2.getName(),testItems.get(1).getName());
        Assertions.assertEquals(item2.getPrice(),testItems.get(1).getPrice());

        Assertions.assertEquals(2,testItems.size());

    }




}
