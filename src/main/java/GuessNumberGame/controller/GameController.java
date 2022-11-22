package GuessNumberGame.controller;

import GuessNumberGame.service.GameService;
import GuessNumberGame.dao.GameDao;
import GuessNumberGame.dao.RoundDao;
import GuessNumberGame.model.Round;
import GuessNumberGame.model.Game;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class GameController {
    private final GameDao gameDao;
    private final RoundDao roundDao;

    public GameController(GameDao gameDao, RoundDao roundDao) {
        this.gameDao = gameDao;
        this.roundDao = roundDao;
    }

    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public Game create() {
        //implement create gameService object and game object
        GameService gameService = new GameService();
        Game game = gameService.newGame();

        //add to database
        gameDao.add(game);

        //getGame will hide answer before returning it to the user
        return gameService.getGames(game);
    }

    @PostMapping("/guess")
    @ResponseStatus(HttpStatus.CREATED)
    public Round guessNumber(@RequestBody Round body) {
        return roundDao.add(body);
    }

    @GetMapping("/game")
    public List<Game> all() {
        return gameDao.getAll();
    }

    @GetMapping("game/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable int gameId) {
        Game result = gameDao.findById(gameId);
        if (result == null)
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(result);
    }

    @GetMapping("rounds/{gameId}")
    public ResponseEntity<Round> getRoundById(@PathVariable int roundId) {
        Round result = roundDao.findById(roundId);
        if (result == null)
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(result);
    }
}
