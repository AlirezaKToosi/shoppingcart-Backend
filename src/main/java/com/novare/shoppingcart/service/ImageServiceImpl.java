package com.novare.shoppingcart.service;


import com.novare.shoppingcart.model.Image;
import com.novare.shoppingcart.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Image create(Image image) {
        return imageRepository.save(image);
    }
    @Override
    public List<Image> viewAll() {
        return (List<Image>) imageRepository.findAll();
    }
    @Override
    public Image viewById(long id) {
        return imageRepository.findById(id).get();
    }
}
