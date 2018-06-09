package com.enterprisesys.plm.controller;

import com.enterprisesys.plm.model.Assembly;
import com.enterprisesys.plm.model.Part;
import com.enterprisesys.plm.service.PartsService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PartsController {

    @Autowired
    private PartsService partsService;

    @RequestMapping(value = "/assemblies/{idAssembly}/parts/upload", method = RequestMethod.POST)
    public String uploadFile(
            @RequestParam("partName") String name,
            @PathVariable("idAssembly") Integer idAssembly,
            @RequestParam("pdf") MultipartFile uploadFile) {

        if (uploadFile.isEmpty()) {
            return "Select file first!";
        }
        else {
            try {
                Assembly assembly = new Assembly(idAssembly, "", null, "");
                partsService.saveUploadedFile(name, assembly, uploadFile);
                return "Upload succesful";
            }
            catch (IOException e) {
                return e.toString();
            }
        }
    }

    @RequestMapping("/parts/all")
    public List<PartsService.PartWithAssemblyId> getAllParts() {
        return partsService.getAllParts();
    }

    @RequestMapping("/parts/{id}")
    public PartsService.PartWithAssemblyId getOnePart(@PathVariable Integer id){
        return partsService.getPart(id);
    }

    @GetMapping("/assemblies/{idAssembly}/parts")
    public List<PartsService.PartWithAssemblyId> getPartsOfAssemblyManyToOne(@PathVariable Integer idAssembly) {
        return partsService.getAllPartsOfAssemblyManyToOne(idAssembly);
    }

    @PostMapping("/assemblies/{idAssembly}/parts")
    public void addPart(@RequestBody Part part, @PathVariable Integer idAssembly) {
        part.setAssembly(new Assembly(idAssembly, "", null, ""));
        partsService.addPart(part);
    }

    @PutMapping("/assemblies/{idAssembly}/parts/{id}")
    public void updatePart(@RequestBody Part part, @PathVariable Integer idAssembly, @PathVariable Integer id) {
        part.setAssembly(new Assembly(idAssembly, "", null, ""));
        partsService.modifyPartData(part);
    }

    @RequestMapping(value = "/parts/del/{id}", method = RequestMethod.DELETE)
    public void deletePart(@PathVariable Integer id){
        partsService.deletePart(id);
    }

    @RequestMapping(value = "/parts/del/all", method = RequestMethod.DELETE)
    public void deleteAll(){
        partsService.dropPartsTable();
    }

    @RequestMapping(value = "/parts/del/group/{assemblyName}", method = RequestMethod.DELETE)
    public void deleteByAssemblyName(@PathVariable String assemblyName){
        partsService.deleteByAssembly(assemblyName);
    }

    @DeleteMapping("/assemblies/{idAssembly}/parts/{id}")
    public void deleteTopic(@PathVariable Integer id) {
        partsService.deletePart(id);
    }

    static class SearchEntity{
        @Getter @Setter
        private String searchType;
        @Getter @Setter
        private String userInput;

        SearchEntity(){};

        SearchEntity(String search, String name){
            this.searchType = search;
            this.userInput = name;
        }
    }

    @RequestMapping("/parts/search")
    public List<PartsService.PartWithAssemblyId> getSortedPartsOfSingleAssembly(@RequestBody SearchEntity search) {
        ArrayList<PartsService.PartWithAssemblyId> toReturn = new ArrayList<>();
        if(search.getSearchType().equals("searchID")){
            Integer id = Integer.parseInt(search.getUserInput());
            toReturn.add(partsService.getPart(id));
        }
        else if(search.getSearchType().equals("searchAssembly")){
            ArrayList<Part> allData = new ArrayList<>();
            allData.addAll(partsService.getAllPartsOfAssemblyByAssemblyName(search.getUserInput()));
            for (Part prt : allData) {
                PartsService.PartWithAssemblyId part = new PartsService.PartWithAssemblyId(prt.getIdPart(), prt.getPartName(), prt.getAssembly().getIdAssembly(), prt.getPdfPath());
                toReturn.add(part);
            }
        }
        return toReturn;
    }

}