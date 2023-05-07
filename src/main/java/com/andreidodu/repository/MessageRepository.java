package com.andreidodu.repository;

import com.andreidodu.model.Conversation;
import com.andreidodu.model.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {
    @Query("SELECT DISTINCT new com.andreidodu.model.Conversation(" +
            "   m.userFrom.username, " +
            "   m.userFrom.id, " +
            "   m.job.id, " +
            "   m.job.title" +
            ") " +
            "from Message m " +
            "where m.userTo.username like ?1 " +
            "group by m.userFrom.username, m.userFrom.id, m.job.id, m.job.title")
    List<Conversation> findConversationsByUsername(String username);

    @Query("select m from Message m where m.userTo.id in (?1) AND m.userFrom.id in (?1) AND m.job.id = ?2 order by createdDate")
    List<Message> findByJobUserToUserFrom(List<Long> ids, Long jobId);
}