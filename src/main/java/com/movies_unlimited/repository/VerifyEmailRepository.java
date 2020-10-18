package com.movies_unlimited.repository;

import com.movies_unlimited.entity.VerifyEmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  VerifyEmailRepository extends JpaRepository<VerifyEmailEntity, Integer> {

}
