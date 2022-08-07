package fr.fms.apitrainings.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.fms.apitrainings.entities.Training;
import fr.fms.apitrainings.errors.RecordNotFoundException;
import fr.fms.apitrainings.service.ImplCategoryService;
import fr.fms.apitrainings.service.ImplImageService;
import fr.fms.apitrainings.service.ImplTrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
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
    public void saveTraining(@RequestBody Training training) {
        training.setCategory(implCategoryService.getOneById(training.getCategory().getId()).get());
        implTrainingService.save(training);
    }

    @PutMapping("/training/{id}")
    public void updateTraining(@PathVariable("id") long id, @RequestBody Training training) {
        training.setCategory(implCategoryService.getOneById(training.getCategory().getId()).get());
        implTrainingService.save(training);
    }

    @DeleteMapping("/trainings/{id}")
    public void deleteTraining(@PathVariable("id") long id) {
        implTrainingService.delete(id);
    }

    @GetMapping("/training/{id}")
    public Training getTrainingById(@PathVariable("id") long id) {
        return implTrainingService.getOneById(id).orElseThrow(
                () -> new RecordNotFoundException("Id de formation " + id + " n'existe pas."));
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

    @PostMapping("/uploadImage")
    public ResponseEntity<Error> uploadImage(@RequestParam("image") MultipartFile image, @RequestParam("trainingId") long trainingId) {
        try {
            Training training = implTrainingService.getOneById(trainingId).get();
            training.setImage(image.getOriginalFilename());
            implImageService.save(image);
            implTrainingService.save(training);
        } catch (Exception e) {
            String message = "Could not upload the file: " + image.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Error(message));
        }
        return null;
    }
}
