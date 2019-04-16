package com.om.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.om.controller.FileDownloadController;
import com.om.exceptions.StorageFileNotFoundException;
import com.om.services.StorageService;

public class ServiceUtils {
	private static final String key = "aesEncryptionKey";
	private static final String initVector = "encryptionIntVec";
	

	public static String encrypt(String value) {
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(value.getBytes());
			return Base64.getEncoder().encodeToString(encrypted);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static String decrypt(String encrypted) {
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));
			return new String(original);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	public static List<String> listUploadedFiles(String[] criterias, StorageService storageService) throws IOException {
		List<String> listPaths = storageService.loadAll()
				.map(path -> MvcUriComponentsBuilder.fromMethodName(FileDownloadController.class, "serveFile", path.getFileName().toString()).build().toString()).collect(Collectors.toList());
		List<String> listPathsByCriterias = new ArrayList<>();
		if (criterias.length > 0) {
			for (String pathVar : listPaths) {
				System.out.println(pathVar);
				for (int i = 0; i < criterias.length; i++) {
					if (pathVar.contains(criterias[i])) {
						listPathsByCriterias.add(pathVar);
					}
				}

			}
		}
		return listPathsByCriterias;
	}

	public static void storeFile(MultipartFile file, String fileName, StorageService storageService) {
		storageService.store(file, fileName);
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}
