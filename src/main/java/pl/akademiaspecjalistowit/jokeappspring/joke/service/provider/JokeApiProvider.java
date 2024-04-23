package pl.akademiaspecjalistowit.jokeappspring.joke.service.provider;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.akademiaspecjalistowit.jokeappspring.joke.dto.JokeDto;
import pl.akademiaspecjalistowit.jokeappspring.joke.mapper.JokeDtoMapper;
import pl.akademiaspecjalistowit.jokeappspring.joke.model.Joke;
import pl.akademiaspecjalistowit.jokeappspring.joke.service.provider.exception.JokeDataProviderException;

@Service
public class JokeApiProvider implements JokeProvider {


    @Value("https://v2.jokeapi.dev/joke/")
    private String urlToJokeApi;

    @Autowired
    private HttpClient httpClient;

    @Override
    public Joke getJoke() {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(urlToJokeApi + "Any"))
                .build();
        return getResponse(request);
    }

    @Override
    public Joke getJokeByCategory(String category) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(urlToJokeApi + "joke/" + category))
                .build();
        return getResponse(request);
    }

    private Joke getResponse(HttpRequest request) {
        try {
            HttpResponse<String> response = httpClient.send(
                    request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = null;
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Joke joke = JokeDtoMapper.toJoke(objectMapper.readValue(response.body(), JokeDto.class));
            return joke;
        } catch (Exception ex) {
            throw new JokeDataProviderException("Failed to call external API", ex);
        }
    }
}
