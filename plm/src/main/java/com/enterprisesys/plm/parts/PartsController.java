package com.enterprisesys.plm.parts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PartsController {

    @Autowired
    private PartsService partsService;

    @RequestMapping("/parts/all")
    public List<Part> getAllParts() {
        return partsService.getAllParts();
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