package com.enterprisesys.plm.parts;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PartsService {

    @Autowired
    private PartsRepo partsRepository;

    public static class PartNoAssemblyName{
        @Getter @Setter private Integer idPart;
        @Getter @Setter private String partName;
        public PartNoAssemblyName(){}
        public PartNoAssemblyName(Integer id, String name){
            this.setIdPart(id);
            this.setPartName(name);
        }
    }

    public List<Part> getAllParts() {
        List<Part> parts = new ArrayList<>();
        partsRepository.findAll().forEach(parts::add);
        return parts;
    }

    public List<Part> getAllPartsOfAssembly(String assemblyName) {
        List<Part> parts = new ArrayList<>();
        partsRepository.getPartsByAssemblyName(assemblyName).forEach(parts::add);
        return parts;
    }

    public List<PartNoAssemblyName> getSortedPartsOfAssembly(String assemblyName) {
        List<Part> parts = new ArrayList<>();
        partsRepository.getSortedPartsByAssemblyName(assemblyName).forEach(parts::add);

        List<PartNoAssemblyName> prtsNoAssemblyName = new ArrayList<>();
        for (Part prt : parts) {
            prtsNoAssemblyName.add(new PartNoAssemblyName(prt.getIdPart(), prt.getPartName()));
        }
        return prtsNoAssemblyName;
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
