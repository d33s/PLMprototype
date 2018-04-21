package com.enterprisesys.plm.parts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PartsService {

    @Autowired
    private PartsRepo partsRepository;

    public List<Part> getAllParts() {
        List<Part> parts = new ArrayList<>();
        partsRepository.findAll().forEach(parts::add);
        return parts;
    }

    public Part getPart(Integer id){
        return partsRepository.findById(id).orElse(null);
    }

    public void addPart(Part part){
        partsRepository.save(part);
    }

    public void updatePart(Part part) {
        partsRepository.save(part);
    }

    public void deletePart(Integer id) {
        partsRepository.deleteById(id);
    }

    public void dropPartsTable(){
        partsRepository.deleteAll();
    }

    public void deleteByAssembly(String assemblyName){
        partsRepository.deletePartsByAssemblyName(assemblyName);
    }
}
