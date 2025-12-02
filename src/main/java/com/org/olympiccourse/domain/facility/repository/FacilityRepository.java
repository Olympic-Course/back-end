package com.org.olympiccourse.domain.facility.repository;

import com.org.olympiccourse.domain.facility.entity.Category;
import com.org.olympiccourse.domain.facility.entity.Facility;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityRepository extends JpaRepository<Facility, Long> {

    List<Facility> findByCategoryIn(List<Category> filter);
}
