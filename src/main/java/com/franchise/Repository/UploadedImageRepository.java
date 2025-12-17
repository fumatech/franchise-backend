package com.franchise.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.franchise.Entity.UploadedImage;

@Repository
public interface UploadedImageRepository extends JpaRepository<UploadedImage, Long> {
}
