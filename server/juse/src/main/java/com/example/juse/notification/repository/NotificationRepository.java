package com.example.juse.notification.repository;

import com.example.juse.notification.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findFirst5ByReceiverIdOrderByCreatedAtDesc(long receiverId);

    @Query(
            "SELECT n FROM Notification n " +
                    "WHERE n.receiver.id = :receiverId " +
                    "AND (:isRead IS NULL OR n.isRead = :isRead) " +
                    "ORDER BY n.createdAt DESC"
    )
    Page<Notification> getPagedNotificationWithCondition(
            Pageable pageable,
            @Param("receiverId") long receiverId,
            @Param("isRead") Boolean isRead
    );

}
