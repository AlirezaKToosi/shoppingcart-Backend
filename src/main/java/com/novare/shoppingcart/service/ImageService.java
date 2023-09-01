package com.novare.shoppingcart.service;


import com.novare.shoppingcart.model.Image;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface ImageService {
    Image create(Image image);
    List<Image> viewAll();
    Image viewById(long id);
}