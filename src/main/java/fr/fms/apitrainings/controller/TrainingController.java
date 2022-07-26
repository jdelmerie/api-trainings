package fr.fms.apitrainings.controller;

import fr.fms.apitrainings.entities.Training;
import fr.fms.apitrainings.errors.RecordNotFoundException;
import fr.fms.apitrainings.service.ImplCategoryService;
import fr.fms.apitrainings.service.ImplTrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/trainings")
    public List<Training> listOfTrainings() {
        return implTrainingService.getAll();
    }

    @GetMapping("/test")
    public void test() {
        Path path = Paths.get("uploads");
        System.out.println(path);
    }
    @PostMapping("/trainings")
    public Training saveTraining(@RequestBody Training training){
        return implTrainingService.save(training);
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
        URL url = this.getClass().getResource("/images/" + training.getImage());
        File file = new File(url.getPath());
        return Files.readAllBytes(Paths.get(String.valueOf(file)));
    }
}
