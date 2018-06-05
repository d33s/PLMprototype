package com.enterprisesys.plm.warehouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @RequestMapping(value = "/warehouse/upload", method = RequestMethod.POST)
    public String uploadFile(
            @RequestParam("partName") String name,
            @RequestParam("availablePieces") String availablePieces,
            @RequestParam("description") String description,
            @RequestParam("pdf") MultipartFile uploadFile) {

        if (uploadFile.isEmpty()) {
            return "Select file first!";
        }
        else {
            try {
                warehouseService.saveUploadedFile(name, availablePieces, description, uploadFile);
                return "Upload succesful";
            }
            catch (IOException e) {
                return e.toString();
            }
        }
    }

    @RequestMapping("/warehouse/all")
    public List<WarehouseService.WarehousePartWithoutPdfObj> getAllParts() {
        return warehouseService.getAllParts();
    }

    @RequestMapping("/warehouse/all/modif")
    public List<WarehouseService.WarehousePartToModify> getAllPartsToMod() {
        return warehouseService.getAllPartsToModify();
    }

    @RequestMapping("/warehouse/{id}")
    public WarehouseService.WarehousePartWithoutPdfObj getOnePart(@PathVariable Integer id){
        return warehouseService.getPart(id);
    }

    @RequestMapping(value = "/warehouse", method = RequestMethod.POST)
    public void addPart(@RequestBody WarehousePart prt){
        warehouseService.addPart(prt);
    }

    @RequestMapping(value = "/warehouse/{id}", method = RequestMethod.PUT)
    public void updatePart(@RequestBody WarehousePart prt, @PathVariable Integer id){
        prt.setIdPart(id);
        warehouseService.updatePartData(prt);
    }

    @RequestMapping(value = "/warehouse/del/{id}", method = RequestMethod.DELETE)
    public void deletePart(@PathVariable Integer id){
        warehouseService.deletePart(id);
    }

    @RequestMapping(value = "/warehouse/del/all", method = RequestMethod.DELETE)
    public void deleteAll(){
        warehouseService.dropWarehouseTable();
    }

}