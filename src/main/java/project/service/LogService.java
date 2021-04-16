package project.service;

import project.model.entities.LogEntity;

import java.util.List;

public interface LogService {
    void createLog(String action, Long itemId);
    List<LogEntity> findAllLogs();
}
