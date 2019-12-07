package com.karaoke.management.service.helper;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.karaoke.management.entity.Food;
import com.karaoke.management.reponsitory.FoodRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

@Service
public class FileStorageService {

	private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }
    
    @Autowired
    FoodRepository foodRepository;
	
	public String storeFile(MultipartFile file, int id) {
		// Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        
        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            String[] arrName = fileName.split("\\.");
            String newFileName = "";
            if(arrName.length != 0) {
            	newFileName = id + "." + arrName[arrName.length-1];
            }else {
            	newFileName = fileName;
            }
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(newFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return newFileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
	}

	public byte[] getImage(int id) {
		Food food = foodRepository.findByFoodId(id);
		String fileName = "default.png";
		if(food != null) {
			fileName = food.getImgLink();
		}
		File fi = new File("D:/chong/UngDung/eclipse/eclipse-workspase/KaraokeManagement/src/main/resources/image/food/"+ fileName);
		byte[] bytes = null;
		try {
			bytes = Files.readAllBytes(fi.toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		byte[] encodeBase64 = Base64.getEncoder().encode(bytes);
        return encodeBase64;
	}

}
