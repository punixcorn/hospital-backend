package com.example.hospital_backend.NurseWorkDay;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NurseWorkDayService {

    @Autowired
    NurseWorkDayRepository NurseWorkDayRepository;

    public Optional<NurseWorkDay> getNurseWorkDayById(Long id) {
        return NurseWorkDayRepository.findById(id);
    }

    public List<NurseWorkDay> getAllNurseWorkDays() {
        return NurseWorkDayRepository.findAll();
    }

    public NurseWorkDay saveNurseWorkDay(NurseWorkDay NurseWorkDay) {
        return NurseWorkDayRepository.save(NurseWorkDay);
    }

    public void deleteNurseWorkDay(Long id) {
        NurseWorkDayRepository.deleteById(id);
    }

}
