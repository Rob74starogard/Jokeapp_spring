package pl.akademiaspecjalistowit.jokeappspring.joke.service.provider;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.akademiaspecjalistowit.jokeappspring.joke.dto.JokeDto;
import pl.akademiaspecjalistowit.jokeappspring.joke.mapper.JokeDtoMapper;
import pl.akademiaspecjalistowit.jokeappspring.joke.model.Joke;

@Service
public class JokeApiProvider implements JokeProvider {

    private final HttpClient httpClient;

    public JokeApiProvider(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public Joke getJoke() {
        HttpRequest request = HttpRequest.newBuilder()
            .GET()
            .uri(URI.create("https://v2.jokeapi.dev/joke/Any"))
            .build();
        return getResponse(request);
    }

    @Override
    public Joke getJokeByCategory(String category) {
        HttpRequest request = HttpRequest.newBuilder()
            .GET()
            .uri(URI.create("https://v2.jokeapi.dev/joke/" + category))
            .build();
        return getResponse(request);
    }

    private Joke getResponse(HttpRequest request) {
        try {
            HttpResponse<String> response = httpClient.send(
                request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return JokeDtoMapper.toJoke(objectMapper.readValue(response.body(), JokeDto.class));
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
