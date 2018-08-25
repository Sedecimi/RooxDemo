package com.example.demo.Repository;


import com.example.demo.Entity.PartnerMappingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PartnerMappingRepository extends JpaRepository<PartnerMappingEntity, Long> {

    Page<PartnerMappingEntity> findByCustomerId(Long customerId, Pageable pageable);

    void delete(PartnerMappingEntity partnerMappingEntity);

}
