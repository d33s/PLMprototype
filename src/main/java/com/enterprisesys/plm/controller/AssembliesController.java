package com.enterprisesys.plm.controller;

import com.enterprisesys.plm.model.Assembly;
import com.enterprisesys.plm.service.AssembliesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class AssembliesController {

    @Autowired
    private AssembliesService assembliesService;

    @RequestMapping(value = "/assemblies/upload", method = RequestMethod.POST)
    public String uploadFile(
            @RequestParam("assemblyName") String name,
            @RequestParam("file") MultipartFile uploadFile) {

        if (uploadFile.isEmpty()) {
            return "Select file first!";
        }
        else {
            try {
                assembliesService.saveUploadedFile(name, uploadFile);
                return "Upload succesful";
            }
            catch (IOException e) {
                return e.toString();
            }
        }
    }

    @RequestMapping("/assemblies/all")
    public List<AssembliesService.AssemblyWithoutObj> getAllAssemblies() {
        return assembliesService.getAllAssemblies();
    }

    @RequestMapping("/assemblies/all/modif")
    public List<AssembliesService.AssemblyToModif> getAllAssembliesToMod() {
        return assembliesService.getAllAssembliesToModify();
    }

    @RequestMapping("/assemblies/{id}")
    public AssembliesService.AssemblyWithoutObj getOneAssembly(@PathVariable Integer id){
        return assembliesService.getAssembly(id);
    }

    @RequestMapping(value = "/assemblies", method = RequestMethod.POST)
    public void addAssembly(@RequestBody Assembly assembly){
        assembliesService.addAssembly(assembly);
    }

//    @RequestMapping(value = "/assemblies/{id}", method = RequestMethod.PUT)
//    public void updateAssembly(@RequestBody Assembly assembly, @PathVariable Integer id){
//        assembly.setIdAssembly(id);
//        assembliesService.updateAssembly(assembly);
//    }

    @RequestMapping(value = "/assemblies/{id}", method = RequestMethod.PUT)
    public void updateAssembly(@RequestBody Assembly assembly, @PathVariable Integer id){
        assembly.setIdAssembly(id);
        assembliesService.updateAssemblyName(assembly);
    }

    @RequestMapping(value = "/assemblies/del/{id}", method = RequestMethod.DELETE)
    public void deleteAssembly(@PathVariable Integer id){
        assembliesService.deleteAssembly(id);
    }

    @RequestMapping(value = "/assemblies/del/all", method = RequestMethod.DELETE)
    public void deleteAll(){
        assembliesService.dropAssembliesTable();
    }

}