package project.serviceimpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import project.model.entities.Item;
import project.model.entities.enums.Gender;
import project.repository.BrandRepository;
import project.repository.ItemRepository;
import project.service.impl.FrontPageServiceImpl;

import java.math.BigDecimal;
import java.util.List;

@ExtendWith(MockitoExtension.class)

public class FrontPageServiceImplTest {

    private FrontPageServiceImpl serviceToTest;

    Item item1,item2,item3;
    @Mock
    private BrandRepository mockBrandRepository;
    @Mock
    ItemRepository mockItemRepo;
    @Mock
    private List<Item> mockItems;


    @BeforeEach
    public void setUp(){
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
        item3 = new Item();
        item3.setName("item3");
        item3.setAddedBy("addedby3");
        item3.setDescription("3333333333");
        item3.setGender(Gender.MALE);
        item3.setImgUrl("333333333");
        item3.setPrice(BigDecimal.TEN);
        item3.setId(3);

        serviceToTest=new FrontPageServiceImpl(mockItemRepo,new ModelMapper(),mockBrandRepository);

    }

}
