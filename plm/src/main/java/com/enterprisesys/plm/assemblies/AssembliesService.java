package com.enterprisesys.plm.assemblies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssembliesService {

    @Autowired
    private AssembliesRepo assembliesRepository;

    public List<Assembly> getAllAssemblies() {
        List<Assembly> assemblies = new ArrayList<>();
        assembliesRepository.findAll().forEach(assemblies::add);
        return assemblies;
    }

    public Assembly getAssembly(Integer id){
        return assembliesRepository.findById(id).orElse(null);
    }

    public void addAssembly(Assembly assembly){
        assembliesRepository.save(assembly);
    }

    public void updateAssembly(Assembly assembly) {
        assembliesRepository.save(assembly);
    }

    public void deleteAssembly(Integer id) {
        assembliesRepository.deleteById(id);
    }

    public void dropAssembliesTable(){
        assembliesRepository.deleteAll();
    }

}
