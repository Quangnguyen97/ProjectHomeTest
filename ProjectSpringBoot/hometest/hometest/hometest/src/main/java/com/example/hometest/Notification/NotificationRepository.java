package com.example.hometest.Notification;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

        String mQueryPushPromotion = "SELECT a.notification_token " +
                        "FROM tbl_User a " +
                        "INNER JOIN tbl_Account b " +
                        "ON a.user_id=b.account_user_id " +
                        "AND b.account_balance>=200 " +
                        "ORDER BY a.user_id DESC";

        // Account have balance >= 200
        @Query(value = mQueryPushPromotion, nativeQuery = true)
        List<String> pushPromotionNotification();

        String mQueryPushAll = "SELECT a.user_id, a.full_name, a.notification_token, " +
                        "b.account_number, b.account_balance " +
                        "FROM tbl_User a " +
                        "INNER JOIN tbl_Account b " +
                        "ON a.user_id=b.account_user_id " +
                        "ORDER BY a.user_id DESC";

        @Query(value = mQueryPushAll, nativeQuery = true)
        List<Notification> pushAllNotification();
}
