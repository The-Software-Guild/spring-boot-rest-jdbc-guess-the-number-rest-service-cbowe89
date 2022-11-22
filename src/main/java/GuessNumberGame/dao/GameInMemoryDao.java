package GuessNumberGame.dao;

import GuessNumberGame.model.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code GameInMemoryDao} class is responsible for interactions
 * with Games in memory.
 */
public class GameInMemoryDao implements GameDao {
    // Declare and initialize List of Game objects
    private static final List<Game> games = new ArrayList<>();

    /**
     * Adds a game to the List of Game objects
     * @param game game to add
     * @return game which was added to the List
     */
    @Override
    public Game add(Game game) {
        // Get the next game id available
        int nextId = games.stream()
                .mapToInt(Game::getGameId)
                .max()
                .orElse(0) + 1;

        // Set game id
        game.setGameId(nextId);

        // Add game to List
        games.add(game);

        // Return game that was added
        return game;
    }

    /**
     * Gets and returns all games in list
     * @return List of Game objects
     */
    @Override
    public List<Game> getAll() {
        return new ArrayList<>(games);
    }

    /**
     * Retrieves and returns a Game object that corresponds to
     * the id passed to the method
     * @param id game id
     * @return Game object that corresponds to the id passed
     */
    @Override
    public Game findById(int id) {
        return games.stream()
                .filter(i -> i.getGameId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Finds index for Game to update, returns boolean indicating
     * if the index is less than the list size
     * @param game game object
     * @return boolean
     */
    @Override
    public boolean update(Game game) {
        // Declare and initialize index
        int index = 0;

        // Increment index variable while index is less than list size and
        // the current gameId is not the gameId for the game to update
        while (index < games.size()
                && games.get(index).getGameId() != game.getGameId())
            index++;

        // Set the game index if less than list size
        if (index < games.size())
            games.set(index, game);

        // Return boolean indicating if index is less than list size
        return index < games.size();
    }

    /**
     * Looks through list of games to remove a Game with a matching
     * id, returns a boolean indicating if a game was removed
     * @param id game id
     * @return boolean
     */
    @Override
    public boolean deleteById(int id) {
        return games.removeIf(i -> i.getGameId() == id);
    }
}
