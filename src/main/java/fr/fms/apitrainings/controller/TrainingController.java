package fr.fms.apitrainings.controller;

import fr.fms.apitrainings.entities.Category;
import fr.fms.apitrainings.entities.Training;
import fr.fms.apitrainings.errors.RecordNotFoundException;
import fr.fms.apitrainings.service.ImplTrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class TrainingController {

    @Autowired
    private ImplTrainingService implTrainingService;

    @GetMapping("/trainings")
    public List<Training> listOfTrainings(){
        return implTrainingService.getAll();
    }

    @PostMapping("/trainings")
    public Training saveTraining(@RequestBody Training training){
        return implTrainingService.save(training);
    }

    @PutMapping("/training/{id}")
    public Training updateTraining(@RequestBody Training training){
        return  implTrainingService.save(training);
    }

    @DeleteMapping("/trainings/{id}")
    public void deleteTraining(@PathVariable("id") long id){
        implTrainingService.delete(id);
    }

    @GetMapping("/training/{id}")
    public Training getTrainingById(@PathVariable("id") long id){
        return implTrainingService.getOneById(id).orElseThrow(
                () -> new RecordNotFoundException("Id de formation " + id + " n'existe pas."));
      /*  if(training.isPresent()){
            return new ResponseEntity<>(training.get(), HttpStatus.OK);
        }
        return null;*/
    }

    @GetMapping("/categorie/{id}/trainings")
    public List<Training> getArticlesByCat(@PathVariable("id") long id){
        return implTrainingService.getByCategory(id);
    }
}
