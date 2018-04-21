package com.enterprisesys.plm.assemblies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AssembliesController {

    @Autowired
    private AssembliesService assembliesService;

    @RequestMapping("/assemblies/all")
    public List<Assembly> getAllAssemblies() {
        return assembliesService.getAllAssemblies();
    }

    @RequestMapping("/assemblies/{id}")
    public Assembly getOneAssembly(@PathVariable Integer id){
        return assembliesService.getAssembly(id);
    }

    @RequestMapping(value = "/assemblies", method = RequestMethod.POST)
    public void addAssembly(@RequestBody Assembly assembly){
        assembliesService.addAssembly(assembly);
    }

    @RequestMapping(value = "/assemblies/{id}", method = RequestMethod.PUT)
    public void updateAssembly(@RequestBody Assembly assembly, @PathVariable Integer id){
        assembly.setIdAssembly(id);
        assembliesService.updateAssembly(assembly);
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