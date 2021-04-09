package project.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.model.view.ItemViewModel;
import project.service.CartService;
import project.service.ItemService;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
        private final ItemService itemService;
        private final ModelMapper modelMapper;

    public CartServiceImpl(ItemService itemService, ModelMapper modelMapper) {
        this.itemService = itemService;
        this.modelMapper = modelMapper;
    }


    @Override
    public void addToCart(Long id,HttpSession session) {
        List<ItemViewModel> wishlist= (List<ItemViewModel>) session.getAttribute("wishlist");

        if(wishlist==null||wishlist.size()==0){
            wishlist= new ArrayList<>();
            session.setAttribute("wishlist",wishlist);
        }
        boolean exists=false;
        ItemViewModel itemToAdd=this.modelMapper.map(this.itemService.findById(id),ItemViewModel.class);
        for (ItemViewModel i:wishlist) {
            if(i.getId().equals(itemToAdd.getId())){
                exists=true;
            }
        }
        if(!exists) {
            wishlist.add(itemToAdd);
        }
    }

    @Override
    public void delete(Long id, HttpSession session) {
        List<ItemViewModel> wishlist= (List<ItemViewModel>) session.getAttribute("wishlist");
        wishlist.removeIf(i -> i.getId().equals(id));

    }

    @Override
    public BigDecimal price(HttpSession session) {

        List<ItemViewModel> wishlist= (List<ItemViewModel>) session.getAttribute("wishlist");
        if(wishlist==null||wishlist.size()==0){
            return BigDecimal.ZERO;
        }

        BigDecimal price=new BigDecimal(0);

        for (ItemViewModel i:wishlist) {
            price= price.add(i.getPrice());
        }

        return price;
    }

    @Override
    public void deleteAll(HttpSession session) {
        List<ItemViewModel> itemViewModels= (List<ItemViewModel>) session.getAttribute("wishlist");
        if (itemViewModels!=null && itemViewModels.size()!=0){
            session.removeAttribute("wishlist");

        }

    }
}
