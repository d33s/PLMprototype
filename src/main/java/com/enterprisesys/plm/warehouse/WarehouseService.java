package com.enterprisesys.plm.warehouse;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class WarehouseService {

    @Autowired
    private WarehouseRepo warehouseRepository;

    //Save the uploaded file to this folder
    private final static String dir = System.getProperty("user.dir");
    private static String UPLOADED_FOLDER = dir + "\\src\\main\\resources\\static\\pdf\\";
    private static String UPLOADED_FOLDER_TARGET = dir + "\\target\\classes\\static\\pdf\\";

    class WarehousePartWithoutPdfObj {
        @Getter
        @Setter
        private Integer idPart;
        @Getter
        @Setter
        private String partName;
        @Getter
        @Setter
        private String availablePieces;
        @Getter
        @Setter
        private String description;
        @Getter
        @Setter
        private String pdfPath;

        private WarehousePartWithoutPdfObj() {
        }

        private WarehousePartWithoutPdfObj(Integer id, String name, String pieces, String desc, String path) {
            this.setIdPart(id);
            this.setPartName(name);
            this.setAvailablePieces(pieces);
            this.setDescription(desc);
            this.setPdfPath(path);
        }
    }

    class WarehousePartToModify {
        @Getter
        @Setter
        private Integer idPart;
        @Getter
        @Setter
        private String partName;
        @Getter
        @Setter
        private String availablePieces;
        @Getter
        @Setter
        private String description;

        private WarehousePartToModify() {
        }

        private WarehousePartToModify(Integer id, String name, String pieces, String desc) {
            this.setIdPart(id);
            this.setPartName(name);
            this.setAvailablePieces(pieces);
            this.setDescription(desc);
        }
    }

    public List<WarehousePartWithoutPdfObj> getAllParts() {

        List<WarehousePart> warehouseParts = new ArrayList<>();
        warehouseRepository.findAll().forEach(warehouseParts::add);

        List<WarehousePartWithoutPdfObj> warehousePartsWithoutPdf = new ArrayList<>();
        for (WarehousePart warehousePrt : warehouseParts) {
            warehousePartsWithoutPdf.add(new WarehousePartWithoutPdfObj(warehousePrt.getIdPart(), warehousePrt.getPartName(), warehousePrt.getAvailablePieces(), warehousePrt.getDescription(), warehousePrt.getPdfPath()));
        }
        return warehousePartsWithoutPdf;
    }

    public List<WarehousePartToModify> getAllPartsToModify() {

        List<WarehousePart> warehouseParts = new ArrayList<>();
        warehouseRepository.findAll().forEach(warehouseParts::add);

        List<WarehousePartToModify> warehousePartsToModify = new ArrayList<>();
        for (WarehousePart prt : warehouseParts) {
            warehousePartsToModify.add(new WarehousePartToModify(prt.getIdPart(), prt.getPartName(), prt.getAvailablePieces(), prt.getDescription()));
        }
        return warehousePartsToModify;
    }

    public WarehousePartWithoutPdfObj getPart(Integer id) {
        WarehousePart searchedPart = warehouseRepository.findById(id).orElse(null);

        WarehousePartWithoutPdfObj searchedPartWithoutObj = new WarehousePartWithoutPdfObj(searchedPart.getIdPart(), searchedPart.getPartName(), searchedPart.getAvailablePieces(), searchedPart.getDescription(), searchedPart.getPdfPath());
        return searchedPartWithoutObj;
    }

    public void addPart(WarehousePart part) {
        warehouseRepository.save(part);
    }

    public void updatePart(WarehousePart part) {
        warehouseRepository.save(part);
    }

    public void updatePartData(WarehousePart part) {
        warehouseRepository.updatePartData(part.getIdPart(), part.getPartName(), part.getAvailablePieces(), part.getDescription());
    }

    public void deletePart(Integer id) {
        warehouseRepository.deleteById(id);
    }

    public void dropWarehouseTable() {
        warehouseRepository.deleteAll();
    }

    public void saveUploadedFile(String name, String pieces, String desc, MultipartFile file) throws IOException {

        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            //sapis w src
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
            //zapis w targecie
            Path pathTarget = Paths.get(UPLOADED_FOLDER_TARGET + file.getOriginalFilename());
            Files.write(pathTarget, bytes);
        } else System.out.println("empty file");

        WarehousePart part = new WarehousePart();
//        VERSION WITH SAVING BLOB INTO DB
        try {
            part.setPartName(name);
            part.setAvailablePieces(pieces);
            part.setDescription(desc);
            part.setPdf(file.getBytes());
            part.setPdfPath("../pdf/" + file.getOriginalFilename());
        }
        catch (IOException e){
            e.printStackTrace();
        }

        warehouseRepository.save(part);
    }

}
