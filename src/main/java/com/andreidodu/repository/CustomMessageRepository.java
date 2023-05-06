package com.andreidodu.repository;

import com.andreidodu.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface CustomMessageRepository {
    Page<Message> findTopN(Sort sortBy, int pageSize);
    Page<Message> findNextN(Sort orderBy, Page<Message> previousPage);
}
