package project.serviceimpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import project.model.entities.Comment;
import project.model.entities.Item;
import project.model.entities.enums.Gender;
import project.model.services.CommentServiceModel;
import project.repository.CommentRepository;
import project.repository.ItemRepository;
import project.service.impl.CommentServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceImplTest {

    private Item item1,item2;
    private Comment comment1,comment2;

    private CommentServiceImpl serviceToTest;
    @Mock
    CommentRepository mockCommentRepository;

    @Mock
    ItemRepository mockItemRepository;



    @BeforeEach
    public void setUp() {
        item1 = new Item();
        item1.setName("item1");
        item1.setAddedBy("addedby1");
        item1.setDescription("11111111111");
        item1.setGender(Gender.FEMALE);
        item1.setImgUrl("1111111111111");
        item1.setPrice(BigDecimal.TEN);
        item1.setId(1);

        comment1=new Comment();
        comment1.setItem(item1);
        comment1.setContent("11111111111111111111");
        comment1.setWriter("11111111111");

        item2 = new Item();
        item2.setName("item2");
        item2.setAddedBy("addedby2");
        item2.setDescription("22222222222");
        item2.setGender(Gender.MALE);
        item2.setImgUrl("222222222222");
        item2.setPrice(BigDecimal.TEN);
        item2.setId(2);

        comment2=new Comment();
        comment2.setItem(item2);
        comment2.setContent("222222222222");
        comment2.setWriter("22222222");


        serviceToTest=new CommentServiceImpl(mockCommentRepository,new ModelMapper(),mockItemRepository);
    }

    @Test
    public void testFindById(){
        when(mockCommentRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(comment1));

        CommentServiceModel testComment=serviceToTest.findById(Long.valueOf(1));

        Assertions.assertEquals(comment1.getContent(),testComment.getContent());
        Assertions.assertEquals(comment1.getItem().getId(),testComment.getItemId());
        Assertions.assertEquals(comment1.getWriter(),testComment.getWriter());

    }
    @Test
    public void testFindByItemId(){
        when(mockCommentRepository.findAllByItemId(Long.valueOf(1))).thenReturn(List.of(comment1));

        List<CommentServiceModel> testComments=serviceToTest.findCommentsById(Long.valueOf(1));
        System.out.println();
        Assertions.assertEquals(1, testComments.size());
        Assertions.assertEquals(comment1.getContent(),testComments.get(0).getContent());
        Assertions.assertEquals(comment1.getWriter(),testComments.get(0).getWriter());
        Assertions.assertEquals(comment1.getItem().getId(),testComments.get(0).getItemId());


    }

//    @Test
//    public void testDeleteComment(){
//
//        serviceToTest.deleteById(comment1.getId());
//
//        Assertions.assertEquals(0, mockCommentRepository.count());
//
//    }
//
//    @Test
//    public void testDeleteByItemId(){
//        this.mockCommentRepository.saveAndFlush(comment1);
//        serviceToTest.deleteCommentsByItemId(Long.valueOf(1));
//        Assertions.assertEquals(0, mockCommentRepository.count());
//
//    }


}
