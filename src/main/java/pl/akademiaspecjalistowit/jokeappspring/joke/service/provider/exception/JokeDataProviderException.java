package pl.akademiaspecjalistowit.jokeappspring.joke.service.provider.exception;

import pl.akademiaspecjalistowit.jokeappspring.joke.service.JokeServiceException;

public class JokeDataProviderException extends JokeServiceException {
    public JokeDataProviderException(String message) {
        super(message);
    }

    public JokeDataProviderException(String message, Throwable cause) {
        super(message, cause);
    }
}
