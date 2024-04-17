package pl.akademiaspecjalistowit.jokeappspring.joke;

import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.akademiaspecjalistowit.jokeappspring.joke.model.Joke;
import pl.akademiaspecjalistowit.jokeappspring.joke.service.JokeService;

@RestController
@AllArgsConstructor
@RequestMapping("/jokes")
public class JokeController {

    private final JokeService jokeService;

    @GetMapping
    public Joke getRandomJoke(@RequestParam(value = "category", required = false)
                                  Optional<String> category) {
        return category.map(jokeService::getJoke)
            .orElse(jokeService.getJoke());
    }


//    @GetMapping
//    public ResponseEntity<Object> getRandomJoke(@RequestParam(value = "category", required = false)
//                                                    Optional<String> category) {
//        try {
//            Joke joke = category.map(jokeService::getJoke)
//                .orElse(jokeService.getJoke());
//            return ResponseEntity.ok(joke);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//
//    }

    @PostMapping
    public void saveJoke(@RequestBody Joke joke) {
        System.out.println(joke);
    }
}
