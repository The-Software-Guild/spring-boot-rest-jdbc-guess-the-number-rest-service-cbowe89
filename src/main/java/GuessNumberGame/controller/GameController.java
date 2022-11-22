package GuessNumberGame.controller;

import GuessNumberGame.service.GameService;
import GuessNumberGame.dao.GameDao;
import GuessNumberGame.dao.RoundDao;
import GuessNumberGame.model.Round;
import GuessNumberGame.model.Game;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class GameController {
    // Declare GameDao and RoundDao objects
    private final GameDao gameDao;
    private final RoundDao roundDao;

    /**
     * GameController constructor initializes GameDao and RoundDao objects
     * @param gameDao GameDao object
     * @param roundDao RoundDao object
     */
    public GameController(GameDao gameDao, RoundDao roundDao) {
        this.gameDao = gameDao;
        this.roundDao = roundDao;
    }

    /**
     * Creates a new game and add it to the database
     * @return new game
     */
    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public Game create() {
        // Declare and initialize GameService object and Game object
        GameService gameService = new GameService();
        Game game = gameService.newGame();

        // Add game to database
        gameDao.add(game);

        // Return new game
        // getGames will hide answer before returning it to the user
        return gameService.getGames(game);
    }

    /**
     * Finds game by gameId, creates a new round, adds the new round
     * to the database
     * @param body game round
     * @return new round
     */
    @PostMapping("/guess")
    @ResponseStatus(HttpStatus.CREATED)
    public Round guessNumber(@RequestBody Round body) {
        // Find game based on gameId from 'body' round
        Game game = gameDao.findById(body.getGameId());

        // Declare and initialize new GameService object
        GameService gameService = new GameService();

        // Create a new round
        Round round = gameService.guessNumber(game, body.getGuess(), gameDao);

        // Add new round to database and return it
        return roundDao.add(round);
    }

    /**
     * Gets a list of all games and returns them
     * @return all games
     */
    @GetMapping("/game")
    public List<Game> all() {
        // Declare a list and add all Game objects to it
        List<Game> games = gameDao.getAll();

        // Declare and initialize new GameService object
        GameService gameService = new GameService();

        // GameService gets all games from the games list
        gameService.getAllGames(games);

        // Return all games
        return games;
    }

    /**
     * Accepts an id and returns the corresponding game
     * @param id game id
     * @return game that corresponds to the id passed
     */
    @GetMapping("game/{id}")
    public Game getGameById(@PathVariable int id) {
        // Declare a Game object, initialize with game matching the id
        Game game = gameDao.findById(id);

        // Declare and initialize new GameService object
        GameService gameService = new GameService();

        // GameService returns the game that was retrieved
        return gameService.getGames(game);
    }

    /**
     * Accepts a gameId and returns all rounds associated with it
     * @param gameId game id
     * @return all rounds for the corresponding game id
     */
    @GetMapping("rounds/{gameId}")
    public List<Round> getGameRounds(@PathVariable int gameId) {
        // Return all round for the gameId passed
        return roundDao.getAllOfGame(gameId);
    }
}
