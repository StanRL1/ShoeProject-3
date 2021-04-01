package project.web;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.model.view.ItemViewModel;
import project.service.ItemService;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/items")
@RestController
public class ItemRestController {

    private final ItemService itemService;
    private final ModelMapper modelMapper;
    @Autowired

    public ItemRestController(ItemService itemService, ModelMapper modelMapper) {
        this.itemService = itemService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/api")
    public ResponseEntity<List<ItemViewModel>> findAll(){
        List<ItemViewModel> itemViewModels=this.itemService.findAllItems().stream().map(item->{
            return this.modelMapper.map(item,ItemViewModel.class);
        }).collect(Collectors.toList());
        System.out.println();
        return ResponseEntity.ok().body(itemViewModels);
    }
}
