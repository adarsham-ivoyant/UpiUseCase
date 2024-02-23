package com.ivoyant.upiusecase.transcation.service;

import com.ivoyant.upiusecase.transcation.dto.TranscationDto;
import com.ivoyant.upiusecase.transcation.model.Transcations;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Transactional
public interface TranscationService {
    boolean save(TranscationDto transcationDto);
}
