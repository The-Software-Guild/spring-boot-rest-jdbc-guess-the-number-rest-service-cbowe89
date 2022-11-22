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
 * The {@code GameDatabaseDaoTest} class tests the GameDataBaseDao class
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class GameDatabaseDaoTest {
    @Autowired
    GameDao gameDao;

    @Autowired
    RoundDao roundDao;

    /**
     * No-args constructor for GameDatabaseDaoTest
     */
    public GameDatabaseDaoTest() {}

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
     * Tests the add and findById methods
     */
    @Test
    public void testAddGetGames() {
        // Declare and initialize GameService object
        GameService gameService = new GameService();

        // Create and add a new game to the dao
        Game game = gameService.newGame();
        gameDao.add(game);

        // Declare a new Game object, initialize by using gameDoa
        // to findById
        Game fromDao = gameDao.findById(game.getGameId());

        // Assert that game and fromDao are equal
        assertEquals(game.getGameId(), fromDao.getGameId());
    }

    /**
     * Tests the getAll method
     */
    @Test
    public void testGetAll() {
        // Declare and initialize GameService object
        GameService gameService = new GameService();

        // No games should exist to begin
        assertEquals(0, gameDao.getAll().size());

        // Create and add 2 new games to the dao
        Game game = gameService.newGame();
        Game game2 = gameService.newGame();
        gameDao.add(game);
        gameDao.add(game2);

        // Declare list with all games
        List<Game> games = gameDao.getAll();

        // Assert that 2 games are in the list of games
        assertEquals(2, games.size());
        // Assert that the list contains game
        assertTrue(games.contains(game));
        // Assert that the list contains game2
        assertTrue(games.contains(game2));
    }

    /**
     * Tests the update method
     */
    @Test
    public void testUpdate() {
        // Declare and initialize GameService object
        GameService gameService = new GameService();

        // Create and add a new game to the dao
        Game game = gameService.newGame();
        gameDao.add(game);

        // Set isFinished to true
        game.setIsFinished(true);

        // Update the game in the gameDao
        gameDao.update(game);

        // Declare new Game object, initialize with updated game
        Game updated = gameDao.findById(game.getGameId());

        // Assert that updated Game is finished
        assertTrue(updated.getIsFinished());
    }

    /**
     * Tests the delete method
     */
    @Test
    public void testDeleteById() {
        // Declare and initialize GameService object
        GameService gameService = new GameService();

        // Create and add 2 new games to the dao
        Game game = gameService.newGame();
        Game game2 = gameService.newGame();
        gameDao.add(game);
        gameDao.add(game2);

        // Delete game based on id
        gameDao.deleteById(game.getGameId());

        // Get list of games
        List<Game> games = gameDao.getAll();

        // Assert that games list only contains 1 game
        assertEquals(1, games.size());
    }
}
