package com.example.juse.repository.notification;

import com.example.juse.board.repository.BoardRepository;
import com.example.juse.helper.stubservice.NotificationStub;
import com.example.juse.notification.entity.Notification;
import com.example.juse.notification.repository.NotificationRepository;
import com.example.juse.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.assertj.core.api.Assertions.*;


import java.util.List;

//@TestPropertySource(
//        properties = "spring.jpa.properties.javax.persistence.validation.mode=none"
//)
@DataJpaTest()
public class NotificationRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("set all notification as read")
    public void givenUserId_thenAllNotificationSetAsRead() {

        long userId = 1L;
        NotificationStub notificationStub = new NotificationStub(userId, 5);
        List<Notification> notificationList = notificationStub.getNotificationStubList();

        userRepository.save(notificationList.get(0).getReceiver());

        for (int i = 0; i < notificationList.size(); i++) {
            boardRepository.save(notificationList.get(i).getBoard());
        }

        notificationRepository.saveAll(notificationList);

        notificationRepository.setAllUnreadNotificationAsReadByUserId(userId);

        List<Notification> notificationList1 = notificationRepository.findAllNotificationByReceiverId(userId);

        assertThat(notificationList1).isNotEmpty();
        assertThat(notificationList1.get(0).isRead()).isTrue();
        assertThat(notificationList1.get(1).isRead()).isTrue();
        assertThat(notificationList1.stream().anyMatch(n -> !n.isRead())).isFalse();

    }

    @Test
    @DisplayName("pc test")
    public void whenDifferentListRetrieved_thenReferenceSame() {
        NotificationStub notificationStub = new NotificationStub(1L, 5);
        List<Notification> notificationList1 = notificationStub.getNotificationStubList();

        userRepository.save(notificationList1.get(0).getReceiver());

        for (int i = 0; i < notificationList1.size(); i++) {
            boardRepository.save(notificationList1.get(i).getBoard());
        }
        notificationRepository.saveAll(notificationList1);

        List<Notification> notificationList = entityManager.createNativeQuery(
                "SELECT * FROM NOTIFICATIONS"
        ).getResultList();

        List<Notification> someNotificationList = entityManager.createNativeQuery(
                "SELECT * FROM NOTIFICATIONS WHERE id BETWEEN 1 AND 3"
        ).getResultList();

        assertThat(notificationList).isNotEqualTo(someNotificationList);
    }
}
