package com.franchise.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.franchise.Entity.UploadedImage;

public interface ImageUploadService {
	UploadedImage uploadImage(MultipartFile file);

	List<UploadedImage> getAllImages();

}
