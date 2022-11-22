package GuessNumberGame.dao;

import GuessNumberGame.model.Game;

import java.util.List;

public interface GameDao {
    Game add(Game game);

    List<Game> getAll();

    Game findById(int id);

    // true if item exists and is updated
    boolean update(Game game);

    // true if item exists and is deleted
    boolean deleteById(int id);
}
