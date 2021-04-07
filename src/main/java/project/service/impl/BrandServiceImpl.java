package project.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.model.entities.Brand;
import project.model.services.BrandServiceModel;
import project.repository.BrandRepository;
import project.service.BrandService;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final ModelMapper modelMapper;

    public BrandServiceImpl(BrandRepository brandRepository, ModelMapper modelMapper) {
        this.brandRepository = brandRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Brand findByUsername(String username) {
        return this.brandRepository.findByName(username).orElse(null);
    }

    @Override
    public void saveBrand(BrandServiceModel brand) {
        this.brandRepository.saveAndFlush(this.modelMapper.map(brand,Brand.class));
    }
}
