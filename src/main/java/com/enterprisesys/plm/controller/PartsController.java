package com.enterprisesys.plm.controller;


import com.enterprisesys.plm.model.Part;
import com.enterprisesys.plm.service.PartsService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PartsController {

    @Autowired
    private PartsService partsService;

    @RequestMapping("/parts/all")
    public List<Part> getAllParts() {
        return partsService.getAllParts();
    }

    @RequestMapping("/parts/all-of-assembly/{assemblyName}")
    public List<Part> getPartsOfAssembly(@PathVariable String assemblyName) {
        return partsService.getAllPartsOfAssembly(assemblyName);
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
    public List<PartsService.PartNoAssemblyName> getSortedPartsOfSingleAssembly(@RequestBody SearchEntity search) {
        ArrayList<PartsService.PartNoAssemblyName> toReturn = new ArrayList<>();
        if(search.getSearchType().equals("searchID")){
            Integer id = Integer.parseInt(search.getUserInput());
            PartsService.PartNoAssemblyName searchResult = new PartsService.PartNoAssemblyName(partsService.getPart(id).getIdPart(), partsService.getPart(id).getPartName());
            toReturn.add(searchResult);
        }
        else if(search.getSearchType().equals("searchAssembly")){
            toReturn.addAll(partsService.getSortedPartsOfAssembly(search.getUserInput())) ;
        }
        return toReturn;
    }

    @RequestMapping("/parts/{id}")
    public Part getOnePart(@PathVariable Integer id){
        return partsService.getPart(id);
    }

    @RequestMapping(value = "/parts", method = RequestMethod.POST)
    public void addPart(@RequestBody Part part){
        partsService.addPart(part);
    }

    @RequestMapping(value = "/parts/{id}", method = RequestMethod.PUT)
    public void updatePart(@RequestBody Part part, @PathVariable Integer id){
        part.setIdPart(id);
        partsService.updatePart(part);
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

}