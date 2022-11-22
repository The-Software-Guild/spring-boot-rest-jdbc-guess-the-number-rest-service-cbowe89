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

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class GameDatabaseDaoTest {
    @Autowired
    GameDao gameDao;

    @Autowired
    RoundDao roundDao;

    public GameDatabaseDaoTest() {

    }

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


    @Test
    public void testAddGetGames() {
        // adds new game using dao
        GameService gameService = new GameService();
        Game game = gameService.newGame();
        gameDao.add(game);

        Game fromDao = gameDao.findById(game.getGameId());
        assertEquals(game.getGameId(), fromDao.getGameId());
    }

    @Test
    public void testGetAll() {
        //implement
    }


    @Test
    public void testUpdate() {
        GameService gameService = new GameService();
        Game game = gameService.newGame();
        gameDao.add(game);
        game.setIsFinished(true);
        gameDao.update(game);
        Game updated = gameDao.findById(game.getGameId());
        assertTrue(updated.getIsFinished());
    }

    @Test
    public void testDeleteById() {
        //implement
    }
}
