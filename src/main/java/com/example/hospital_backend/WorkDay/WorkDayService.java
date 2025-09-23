package com.example.hospital_backend.WorkDay;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkDayService {

    @Autowired
    WorkDayRepository WorkDayRepository;

    public Optional<WorkDay> getWorkDayById(Long id) {
        return WorkDayRepository.findById(id);
    }

    public List<WorkDay> getAllWorkDays() {
        return WorkDayRepository.findAll();
    }

    public WorkDay saveWorkDay(WorkDay WorkDay) {
        return WorkDayRepository.save(WorkDay);
    }

    public void deleteWorkDay(Long id) {
        WorkDayRepository.deleteById(id);
    }

}
