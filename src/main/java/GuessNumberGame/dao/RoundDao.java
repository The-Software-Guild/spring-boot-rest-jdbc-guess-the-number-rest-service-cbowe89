package GuessNumberGame.dao;

import GuessNumberGame.model.Round;

import java.util.List;

public interface RoundDao {
    Round add(Round round);

    List<Round> getAll();

    List<Round> getAllOfGame(int gameId);

    Round findById(int id);

    // True if Round exists and is Updated in the database
    boolean update(Round round);

    // True if Round exists and is Deleted from the database
    boolean deleteById(int id);
}
