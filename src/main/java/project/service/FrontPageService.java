package project.service;

import project.model.services.ItemServiceModel;
import project.model.view.ItemViewModel;

public interface FrontPageService {
    public ItemServiceModel firstImage();
    public ItemServiceModel secondImage();
    public ItemServiceModel thirdImage();
    public void reload();
    public void init();
}
