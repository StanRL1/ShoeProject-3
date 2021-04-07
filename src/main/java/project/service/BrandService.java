package project.service;

import project.model.entities.Brand;
import project.model.services.BrandServiceModel;

public interface BrandService {
    Brand findByUsername(String username);
    void saveBrand(BrandServiceModel brand);
}
