package com.enterprisesys.plm.parts;

import com.enterprisesys.plm.assemblies.Assembly;
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


    public class PartWithAssemblyId{
        //default json naming (same as variables names)
        @Getter @Setter Integer idPart;
        @Getter @Setter String partName;
        @Getter @Setter Integer idAssembly;

        PartWithAssemblyId(Integer id, String name, Integer assId){
            this.idPart = id;
            this.partName = name;
            this.idAssembly = assId;
        }
    }

    public List<PartWithAssemblyId> getAllParts() {
        List<Part> parts = new ArrayList<>();
        partsRepository.findAll().forEach(parts::add);

        List<PartWithAssemblyId> partsWithAssId = new ArrayList<>();
        for (Part prt: parts) {
            partsWithAssId.add(new PartWithAssemblyId(prt.getIdPart(), prt.getPartName(), prt.getAssembly().getIdAssembly()));
        }
        return partsWithAssId;
    }

    public List<PartWithAssemblyId> getAllPartsOfAssemblyManyToOne(Integer assemblyId){
        List<Part> parts = new ArrayList<>();
        partsRepository.findByAssemblyIdAssembly(assemblyId).forEach(parts::add);

        List<PartWithAssemblyId> partsWithAssId = new ArrayList<>();
        for (Part prt: parts) {
            partsWithAssId.add(new PartWithAssemblyId(prt.getIdPart(), prt.getPartName(), prt.getAssembly().getIdAssembly()));
        }
        return partsWithAssId;
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


//    public static class PartNoAssemblyName{
//        @Getter @Setter private Integer idPart;
//        @Getter @Setter private String partName;
//        public PartNoAssemblyName(){}
//        public PartNoAssemblyName(Integer id, String name){
//            this.setIdPart(id);
//            this.setPartName(name);
//        }
//    }

//    public List<Part> getAllPartsOfAssembly(String assemblyName) {
//        List<Part> parts = new ArrayList<>();
//        partsRepository.getPartsByAssemblyName(assemblyName).forEach(parts::add);
//        return parts;
//    }

//    public List<PartNoAssemblyName> getSortedPartsOfAssembly(String assemblyName) {
//        List<Part> parts = new ArrayList<>();
//        partsRepository.getSortedPartsByAssemblyName(assemblyName).forEach(parts::add);
//
//        List<PartNoAssemblyName> prtsNoAssemblyName = new ArrayList<>();
//        for (Part prt : parts) {
//            prtsNoAssemblyName.add(new PartNoAssemblyName(prt.getIdPart(), prt.getPartName()));
//        }
//        return prtsNoAssemblyName;
//    }

//    public void deleteByAssembly(String assemblyName){
//        partsRepository.deletePartsByAssemblyName(assemblyName);
//    }

}
