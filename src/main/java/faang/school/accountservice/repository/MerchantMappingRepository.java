package faang.school.accountservice.repository;

import faang.school.accountservice.model.cashback.MerchantMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MerchantMappingRepository extends JpaRepository<MerchantMapping, Long> {
   @Query("SELECT m FROM MerchantMapping m WHERE m.cashbackTariff.id = :tariffId")
   List<MerchantMapping> findMerchantMappingsByTariffId(@Param("tariffId") Long tariffId);
}
