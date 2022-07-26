package fr.fms.apitrainings.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.fms.apitrainings.entities.Training;
import fr.fms.apitrainings.errors.RecordNotFoundException;
import fr.fms.apitrainings.service.ImplCategoryService;
import fr.fms.apitrainings.service.ImplImageService;
import fr.fms.apitrainings.service.ImplTrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class TrainingController {

    @Autowired
    private ImplTrainingService implTrainingService;

    @Autowired
    private ImplCategoryService implCategoryService;

    @Autowired
    private ImplImageService implImageService;

    @GetMapping("/trainings")
    public List<Training> listOfTrainings() {
        return implTrainingService.getAll();
    }

    @PostMapping("/trainings")
    public void saveTraining(@RequestParam("image") MultipartFile image, @RequestParam("training") String trainingJson) throws JsonProcessingException {
        Training training = new ObjectMapper().readValue(trainingJson, Training.class);
        training.setCategory(implCategoryService.getOneById(training.getCategory().getId()).get());
//        String extention = implImageService.getFileExtention(image.getOriginalFilename());
//        String filename = training.getName() + "." + extention;
        if (training.getImage() != null) {
            training.setImage(image.getOriginalFilename());
        } else {
            training.setImage("noimage.png");
        }
        System.out.println(training);
        try {
            implImageService.save(image);
        } catch (Exception e) {
            System.out.println("Could not upload the file: " + image.getOriginalFilename() + "!");
        }
        implTrainingService.save(training);
    }

    @PutMapping("/training/{id}")
    public Training updateTraining(@RequestBody Training training) {
        return implTrainingService.save(training);
    }

    @DeleteMapping("/trainings/{id}")
    public void deleteTraining(@PathVariable("id") long id) {
        implTrainingService.delete(id);
    }

    @GetMapping("/training/{id}")
    public Training getTrainingById(@PathVariable("id") long id) {
        return implTrainingService.getOneById(id).orElseThrow(
                () -> new RecordNotFoundException("Id de formation " + id + " n'existe pas."));
      /*  if(training.isPresent()){
            return new ResponseEntity<>(training.get(), HttpStatus.OK);
        }
        return null;*/
    }

    @GetMapping("/categorie/{id}/trainings")
    public List<Training> getArticlesByCat(@PathVariable("id") long id) {
        return implTrainingService.getByCategory(id);
    }

    @GetMapping(path = "/trainingImage/{id}")
    public byte[] getTrainingImage(@PathVariable("id") Long id) throws Exception {
        Training training = implTrainingService.getOneById(id).get();
        return Files.readAllBytes(Paths.get("uploads").resolve(training.getImage()));
    }
}
