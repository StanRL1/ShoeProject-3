package project.service;

import project.model.view.ItemViewModel;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

public interface CartService {
    void addToCart(Long id, HttpSession session);

    void delete(Long id, HttpSession session);

    BigDecimal price(HttpSession session);

    void deleteAll(HttpSession session);
}
