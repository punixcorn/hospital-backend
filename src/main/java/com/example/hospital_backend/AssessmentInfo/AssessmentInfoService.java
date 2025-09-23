package com.example.hospital_backend.AssessmentInfo;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssessmentInfoService {

    @Autowired
    AssessmentInfoRepository AssessmentInfoRepository;

    public Optional<AssessmentInfo> getAssessmentInfoById(Long id) {
        return AssessmentInfoRepository.findById(id);
    }

    public List<AssessmentInfo> getAllAssessmentInfos() {
        return AssessmentInfoRepository.findAll();
    }

    public AssessmentInfo saveAssessmentInfo(AssessmentInfo AssessmentInfo) {
        return AssessmentInfoRepository.save(AssessmentInfo);
    }

    public void deleteAssessmentInfo(Long id) {
        AssessmentInfoRepository.deleteById(id);
    }

}
