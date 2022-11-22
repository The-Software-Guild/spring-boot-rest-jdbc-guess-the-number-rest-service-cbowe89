package GuessNumberGame.dao;

import GuessNumberGame.model.Game;

import java.util.List;

public interface GameDao {
    Game add(Game game);

    List<Game> getAll();

    Game findById(int id);

    // True if game exists and is Updated in the database
    boolean update(Game game);

    // True if Game exists and is Deleted from database
    boolean deleteById(int id);
}
