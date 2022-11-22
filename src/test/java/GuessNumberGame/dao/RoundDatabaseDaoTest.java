package GuessNumberGame.dao;

import GuessNumberGame.service.GameService;
import GuessNumberGame.TestApplicationConfiguration;
import GuessNumberGame.model.Game;
import GuessNumberGame.model.Round;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The {@code RoundDatabaseDaoTest} class tests the RoundDataBaseDao class
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class RoundDatabaseDaoTest {
    @Autowired
    GameDao gameDao;

    @Autowired
    RoundDao roundDao;

    /**
     * No-args constructor for RoundDatabaseDaoTest
     */
    public RoundDatabaseDaoTest () {}

    /**
     * The setUp method empties the guessGame_tests database tables
     */
    @BeforeEach
    public void setUp() {
        List<Round> rounds = roundDao.getAll();
        for(Round round : rounds) {
            roundDao.deleteById(round.getId());
        }

        List<Game> games = gameDao.getAll();
        for(Game game : games) {
            gameDao.deleteById(game.getGameId());
        }
    }

    /**
     * Tests the add method (add a round)
     */
    @Test
    public void testAdd() {
        // Declare and initialize GameService object
        GameService gameService = new GameService();

        // Create and add a new game to the dao
        Game game = gameService.newGame();
        gameDao.add(game);

        // Create new round, set values of the round, and add round
        Round round = new Round();
        round.setGameId(game.getGameId());
        gameService.setTimeStamp(round);
        round.setGuess("1234");
        round.setGuessResult("e:2:p:1");
        roundDao.add(round);

        // Declare new Round object, initialize with new round
        Round fromDao = roundDao.findById(round.getId());

        // Assert round id equals fromDao id
        assertEquals(round.getId(), fromDao.getId());
    }

    /**
     * Tests the getAll method
     */
    @Test
    public void testGetAll() {
        // Declare and initialize GameService object
        GameService gameService = new GameService();

        // Create and add 2 new games to the dao
        Game game = gameService.newGame();
        Game game2 = gameService.newGame();
        gameDao.add(game);
        gameDao.add(game2);

        // Create and add 2 new rounds
        // (round for game, round2 for game2)
        Round round = new Round();
        round.setGuess("1111");
        round.setGameId(game.getGameId());
        Round round2 = new Round();
        round2.setGuess("2222");
        round2.setGameId(game2.getGameId());
        roundDao.add(round);
        roundDao.add(round2);

        // Get list of all rounds
        List<Round> rounds = roundDao.getAll();

        // Assert size of rounds list is 2
        assertEquals(2, rounds.size());
    }

    /**
     * Tests the getAllOfGame method
     */
    @Test
    public void testGetAllOfGame() {
        // Declare and initialize GameService object
        GameService gameService = new GameService();

        // Create and add 2 new games to the dao
        Game game = gameService.newGame();
        Game game2 = gameService.newGame();
        gameDao.add(game);
        gameDao.add(game2);

        // Get a list of all games
        List<Game> games = gameDao.getAll();

        // Create and add 3 new rounds
        // (round for game, round2 for game, and round3 for game2)
        Round round = new Round();
        round.setGuess("1111");
        round.setGameId(game.getGameId());
        Round round2 = new Round();
        round2.setGuess("2222");
        round2.setGameId(game.getGameId());
        Round round3 = new Round();
        round3.setGuess("3333");
        round3.setGameId(game2.getGameId());
        roundDao.add(round);
        roundDao.add(round2);
        roundDao.add(round3);

        // Get a list of all rounds for game (not game2)
        List<Round> rounds = roundDao.getAllOfGame(game.getGameId());

        // Assert that rounds list size is 2
        assertEquals(2, rounds.size());
        // Assert that the rounds list contains round
        assertTrue(rounds.contains(round));
        // Assert that the rounds list does NOT contain round 3 (for game2)
        assertFalse(rounds.contains(round3));
    }
}
