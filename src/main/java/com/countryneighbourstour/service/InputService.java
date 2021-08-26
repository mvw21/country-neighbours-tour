package com.countryneighbourstour.service;

import com.countryneighbourstour.model.dto.InputDto;
import com.countryneighbourstour.model.entity.Result;

import java.io.IOException;

public interface InputService {
    Result getData(InputDto inputDto) throws IOException;
}
