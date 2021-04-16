package project.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import project.model.entities.Item;
import project.model.entities.LogEntity;
import project.model.entities.UserEntity;
import project.repository.LogRepository;
import project.service.ItemService;
import project.service.LogService;
import project.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;
    private final ItemService itemService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public LogServiceImpl(LogRepository logRepository, ItemService itemService, UserService userService, ModelMapper modelMapper) {
        this.logRepository = logRepository;
        this.itemService = itemService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createLog(String action, Long itemId) {
        Item item= this.modelMapper.map(this.itemService.findById(itemId),Item.class);
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();

        LogEntity logEntity=new LogEntity();
        logEntity.setAction(action);
        logEntity.setDateTime(LocalDateTime.now());
        logEntity.setItem(item);
        logEntity.setUsername(username);
        logRepository.saveAndFlush(logEntity);
    }

    @Override
    public List<LogEntity> findAllLogs() {
        return this.logRepository.findAll();
    }

}
