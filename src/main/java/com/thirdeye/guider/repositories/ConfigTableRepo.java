package com.thirdeye.guider.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thirdeye.guider.entity.ConfigTable;


@Repository
public interface ConfigTableRepo extends JpaRepository<ConfigTable, Long> {
}
