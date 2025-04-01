package org.example.netwave.repo;

import org.example.netwave.entity.CreditBundle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Credit_BundleRepo extends JpaRepository<CreditBundle,Integer> {
    List<CreditBundle> findByIsDeletedFalse();

    @Query("SELECT c.bundleName FROM CreditBundle c")
    List<String> findAllCreditBundleName();

    @Query("SELECT c.bundleId FROM CreditBundle c WHERE c.bundleName =:name")
    Integer findBundleByNames(String name);
}

