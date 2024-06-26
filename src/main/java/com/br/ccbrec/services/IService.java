package com.br.ccbrec.services;

import com.br.ccbrec.dto.DTO;
import com.br.ccbrec.util.SplitedDate;

public interface IService {
    DTO add(DTO dto);
    void delete(SplitedDate splitedDate);
    void update(DTO dto);
}
