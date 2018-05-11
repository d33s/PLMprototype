package com.enterprisesys.plm.assemblies;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class AssembliesService {

    @Autowired
    private AssembliesRepo assembliesRepository;

    //Save the uploaded file to this folder
    private final static String dir = System.getProperty("user.dir");
    private static String UPLOADED_FOLDER = dir + "\\src\\main\\resources\\static\\obj\\";
    private static String UPLOADED_FOLDER_TARGET = dir + "\\target\\classes\\static\\obj\\";

    class AssemblyWithoutObj {
        @Getter
        @Setter
        private Integer idAssembly;
        @Getter
        @Setter
        private String assemblyName;
        @Getter
        @Setter
        private String assemblyPath;

        private AssemblyWithoutObj() {
        }

        private AssemblyWithoutObj(Integer id, String name, String path) {
            this.setIdAssembly(id);
            this.setAssemblyName(name);
            this.setAssemblyPath(path);
        }
    }

    class AssemblyToModif {
        @Getter
        @Setter
        private Integer idAssembly;
        @Getter
        @Setter
        private String assemblyName;

        private AssemblyToModif() {
        }

        private AssemblyToModif(Integer id, String name) {
            this.setIdAssembly(id);
            this.setAssemblyName(name);
        }
    }

    public List<AssemblyWithoutObj> getAllAssemblies() {

        List<Assembly> assemblies = new ArrayList<>();
        assembliesRepository.findAll().forEach(assemblies::add);

        List<AssemblyWithoutObj> assembliesWithoutObj = new ArrayList<>();
        for (Assembly assembly : assemblies) {
            assembliesWithoutObj.add(new AssemblyWithoutObj(assembly.getIdAssembly(), assembly.getAssemblyName(), assembly.getPath()));
        }
        return assembliesWithoutObj;
    }

    public List<AssemblyToModif> getAllAssembliesToModify() {

        List<Assembly> assemblies = new ArrayList<>();
        assembliesRepository.findAll().forEach(assemblies::add);

        List<AssemblyToModif> assembliesToModify = new ArrayList<>();
        for (Assembly assembly : assemblies) {
            assembliesToModify.add(new AssemblyToModif(assembly.getIdAssembly(), assembly.getAssemblyName()));
        }
        return assembliesToModify;
    }

    public AssemblyWithoutObj getAssembly(Integer id) {
        Assembly searchedAssembly = assembliesRepository.findById(id).orElse(null);

        AssemblyWithoutObj searchedAssemblyWithoutObj = new AssemblyWithoutObj(searchedAssembly.getIdAssembly(), searchedAssembly.getAssemblyName(), searchedAssembly.getPath());
        return searchedAssemblyWithoutObj;
    }

    public void addAssembly(Assembly assembly) {
        assembliesRepository.save(assembly);
    }

    public void updateAssembly(Assembly assembly) {
        assembliesRepository.save(assembly);
    }

    public void updateAssemblyName(Assembly assembly) {
        assembliesRepository.updateAssemblyName(assembly.getIdAssembly(), assembly.getAssemblyName());
    }

    public void deleteAssembly(Integer id) {
        assembliesRepository.deleteById(id);
    }

    public void dropAssembliesTable() {
        assembliesRepository.deleteAll();
    }

    public void saveUploadedFile(String name, MultipartFile file) throws IOException {

        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            //sapis w src
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
            //zapis w targecie
            Path pathTarget = Paths.get(UPLOADED_FOLDER_TARGET + file.getOriginalFilename());
            Files.write(pathTarget, bytes);
        } else System.out.println("empty file");

        Assembly assembly = new Assembly();
        assembly.setAssemblyName(name);
        assembly.setPath("../obj/" + file.getOriginalFilename()); //tu sie ustala sciezka do wpisania do tabeli

//        VERSION WITH SAVING BLOB INTO DB
//            try {
//                assembly.setAssemblyName(name);
//                assembly.setObject(file.getBytes());
//                assembly.setPath("../obj/" + file.getOriginalFilename());
//            }
//            catch (IOException e){
//                e.printStackTrace();
//            }

        assembliesRepository.save(assembly);
    }

}
