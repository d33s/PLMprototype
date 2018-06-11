package com.enterprisesys.plm.service;


import com.enterprisesys.plm.model.Assembly;
import com.enterprisesys.plm.model.Part;
import com.enterprisesys.plm.model.WarehousePart;
import com.enterprisesys.plm.repository.PartsRepo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class PartsService {

	@Autowired
	private PartsRepo partsRepository;

	//Save the uploaded file to this folder
	private final static String dir = System.getProperty("user.dir");
	private static String UPLOADED_FOLDER = dir + "\\src\\main\\resources\\static\\pdf\\prts\\";
	private static String UPLOADED_FOLDER_TARGET = dir + "\\target\\classes\\static\\pdf\\prts\\";

	public static class PartWithAssemblyId{
		//default json naming (same as variables names)
		@Getter @Setter Integer idPart;
		@Getter @Setter String partName;
		@Getter @Setter Integer idAssembly;
		@Getter @Setter String pdfPath;

		public PartWithAssemblyId(){
		}

		public PartWithAssemblyId(Integer id, String name, Integer assId, String pdf){
			this.idPart = id;
			this.partName = name;
			this.idAssembly = assId;
			this.pdfPath = pdf;
		}
	}

	public List<PartWithAssemblyId> getAllParts() {
		List<Part> parts = new ArrayList<>();
		partsRepository.findAll().forEach(parts::add);

		List<PartWithAssemblyId> partsWithAssId = new ArrayList<>();
		for (Part prt: parts) {
			partsWithAssId.add(new PartWithAssemblyId(prt.getIdPart(), prt.getPartName(), prt.getAssembly().getIdAssembly(), prt.getPdfPath()));
		}
		return partsWithAssId;
	}

	public List<PartWithAssemblyId> getAllPartsOfAssemblyManyToOne(Integer assemblyId){
		List<Part> parts = new ArrayList<>();
		partsRepository.findByAssemblyIdAssembly(assemblyId).forEach(parts::add);

		List<PartWithAssemblyId> partsWithAssId = new ArrayList<>();
		for (Part prt: parts) {
			partsWithAssId.add(new PartWithAssemblyId(prt.getIdPart(), prt.getPartName(), prt.getAssembly().getIdAssembly(), prt.getPdfPath()));
		}
		return partsWithAssId;
	}

	public PartWithAssemblyId getPart(Integer id){
		Part found = new Part(partsRepository.findById(id).orElse(null));
		PartWithAssemblyId toClient = new PartWithAssemblyId(found.getIdPart(), found.getPartName(), found.getAssembly().getIdAssembly(), found.getPdfPath());
		return toClient;
	}

	public void addPart(Part part){
		partsRepository.save(part);
	}

	public void updatePart(Part part) { partsRepository.save(part); }

	public void modifyPartData(Part part) {
		partsRepository.updatePartData(part.getIdPart(), part.getPartName(), part.getAssembly(), part.getPdfPath());
	}

	public void deletePart(Integer id) {
		partsRepository.deleteById(id);
	}

	public void dropPartsTable(){
		partsRepository.deleteAll();
	}

	public List<Part> getAllPartsOfAssemblyByAssemblyName(String assemblyName) {
		List<Part> parts = new ArrayList<>();
		partsRepository.findByAssemblyAssemblyName(assemblyName).forEach(parts::add);
		return parts;
	}

	public void deleteByAssembly(String assemblyName){
		partsRepository.deletePartByAssemblyAssemblyName(assemblyName);
	}

	public void saveUploadedFile(String name, Assembly ass, MultipartFile file) throws IOException {

		if (!file.isEmpty()) {
			byte[] bytes = file.getBytes();
			//zapis w src
			Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
			Files.write(path, bytes);
			//zapis w targecie
			Path pathTarget = Paths.get(UPLOADED_FOLDER_TARGET + file.getOriginalFilename());
			Files.write(pathTarget, bytes);
		} else System.out.println("empty file");

		Part part = new Part();
//        VERSION WITH SAVING BLOB INTO DB
		try {
			part.setPartName(name);
			part.setAssembly(ass);
			part.setPdf(file.getBytes());
			part.setPdfPath("../pdf/prts/" + file.getOriginalFilename());
		}
		catch (IOException e){
			e.printStackTrace();
		}

		partsRepository.save(part);
	}

}