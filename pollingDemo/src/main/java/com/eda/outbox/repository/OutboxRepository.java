package com.eda.outbox.repository;

import com.eda.outbox.entity.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutboxRepository extends JpaRepository<OutboxEvent, String> {

    @Query(value = """
        SELECT * FROM outbox_events
        WHERE published = false
        ORDER BY created_at ASC
        LIMIT :batchSize
        """, nativeQuery = true)
    List<OutboxEvent> findUnpublishedEvents(int batchSize);
}

