package com.prime.rushhour.domain.activity.repository;

import com.prime.rushhour.domain.activity.entity.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    void deleteActivitiesByProviderId(Long id);

    @Query("SELECT a.provider.id FROM Activity a WHERE a.id = :id")
    Long findProviderIdByActivityId(@Param("id") Long id);

    Page<Activity> findActivitiesByProviderId(Pageable pageable, Long id);
}