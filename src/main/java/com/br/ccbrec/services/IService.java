package com.br.ccbrec.services;

import com.br.ccbrec.dto.DTO;
import com.br.ccbrec.util.DataParameterWrapper;

public interface IService {
    DTO add(DTO dto);
    void delete(DataParameterWrapper dataParameterWrapper);
    void update(DTO dto);
}
