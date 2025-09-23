package com.example.hospital_backend.HourlyData;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HourlyDataService {

    @Autowired
    HourlyDataRepository HourlyDataRepository;

    public Optional<HourlyData> getHourlyDataById(Long id) {
        return HourlyDataRepository.findById(id);
    }

    public List<HourlyData> getAllHourlyDatas() {
        return HourlyDataRepository.findAll();
    }

    public HourlyData saveHourlyData(HourlyData HourlyData) {
        return HourlyDataRepository.save(HourlyData);
    }

    public void deleteHourlyData(Long id) {
        HourlyDataRepository.deleteById(id);
    }

}
