package bg.softuni.gamestore;

import bg.softuni.gamestore.exceptions.LoggedUserException;
import bg.softuni.gamestore.exceptions.NoSuchEntityException;
import bg.softuni.gamestore.exceptions.ValidationException;
import bg.softuni.gamestore.services.ExecutorService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Runner implements CommandLineRunner {

   private final ExecutorService executorService;

   @Autowired
    public Runner(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public void run(String... args) throws Exception {
       try (Scanner s = new Scanner(System.in)){
           String input;

           while (!(input = s.nextLine()).equals("END")) {
               String output = executorService.execute(input);
               System.out.println(output);
           }

       } catch (ConstraintViolationException | ValidationException | LoggedUserException | NoSuchEntityException e) {
           System.out.println(e.getMessage());
       }
    }
}
