package org.example.netwave.repo;
import org.example.netwave.entity.ActivePackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
public interface ActivePackageRepo extends JpaRepository<ActivePackage, Integer> {

    List<ActivePackage> findByPhoneNumber(String phoneNumber);

    List<ActivePackage> findByPhoneNumberAndIsActiveTrue(String phoneNumber);

    @Query("SELECT ap FROM ActivePackage ap WHERE ap.phoneNumber = :phoneNumber AND ap.packageId = :packageId AND ap.isActive = true")
    List<ActivePackage> findActivePackagesByPhoneNumberAndPackageId(
            @Param("phoneNumber") String phoneNumber,
            @Param("packageId") String packageId
    );
}