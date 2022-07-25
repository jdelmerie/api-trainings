package fr.fms.apitrainings;

import fr.fms.apitrainings.dao.CategoryRepository;
import fr.fms.apitrainings.dao.OrdersRepository;
import fr.fms.apitrainings.dao.TrainingRepository;
import fr.fms.apitrainings.entities.Category;
import fr.fms.apitrainings.entities.Orders;
import fr.fms.apitrainings.entities.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class ApiTrainingsApplication implements CommandLineRunner {

/*  @Autowired
  private TrainingRepository trainingRepository;

    @Autowired
    private CategoryRepository categoryRepository;*/

    public static void main(String[] args) {
        SpringApplication.run(ApiTrainingsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
   /* Category prog = categoryRepository.save(new Category(null, "Programmation"));
        Category framework = categoryRepository.save(new Category(null, "Framework"));
        Category cms = categoryRepository.save(new Category(null, "CMS"));

        trainingRepository.save(new Training(null, "Java", "Formation Java SE 8 sur 5 jours", 1500, 1, "java.png" , prog));
        trainingRepository.save(new Training(null, "DotNet", "Formation DotNet sur 3 jours", 1000, 1, "noimage.png", prog));
        trainingRepository.save(new Training(null, "Python", "Formation Python/Django sur 5 jours", 1500, 1, "python.png",  prog));
        trainingRepository.save(new Training(null, "PHP Frameworks", "Symfony sur 15 jours", 2500, 1, "symfony.png", framework));
        trainingRepository.save(new Training(null, "Spring", "Spring Core/MVC/Security sur 20 jours", 5500, 1, "noimage.png", framework));
        trainingRepository.save(new Training(null, "WordPress", "Découverte WP 8", 1800, 1, "noimage.png", cms));
        trainingRepository.save(new Training(null, "Dupral", "Prise en main", 1000, 1, "noimage.png", cms));*/
    }
}
