package com.example.Security.repository;


import com.example.Security.entity.AccountPreviewSetting;
import com.example.Security.entity.PaymentPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountPreviewSettingRepository extends JpaRepository<AccountPreviewSetting,Long> {

    AccountPreviewSetting findByAccountId(Long accountId);
}
