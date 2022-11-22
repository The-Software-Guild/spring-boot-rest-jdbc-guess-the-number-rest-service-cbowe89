package GuessNumberGame.dao;

import GuessNumberGame.model.Game;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

/**
 * The {@code GameDatabaseDao} class is responsible for interactions
 * with the games table in the SQL database.
 */
@Repository
public class GameDatabaseDao implements GameDao {
    // Declare JdbcTemplate object
    private final JdbcTemplate jdbcTemplate;

    /**
     * GameDatabaseDao constructor initializes JdbcTemplate object
     * @param jdbcTemplate JdbcTemplate object
     */
    @Autowired
    public GameDatabaseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Adds a Game to the database
     * @param game game object to be added
     * @return game object that was added
     */
    @Override
    public Game add(Game game) {
        // Declare and initialize SQL statement
        final String sql = "INSERT INTO games(answer, isFinished) VALUES(?,?)";

        // Declare and initialize new GeneratedKeyHolder
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        // JdbcTemplate accepts 2 parameters, a PreparedStatementCreator
        // and a GeneratedKeyHolder
        jdbcTemplate.update((Connection conn) -> {
            // Generate a PreparedStatement from a Connection
            PreparedStatement statement = conn.prepareStatement(
                    sql, Statement.RETURN_GENERATED_KEYS);
            // Get the Game answer and isFinished
            statement.setString(1, game.getAnswer());
            statement.setBoolean(2, game.getIsFinished());
            // Return the prepared statement
            return statement;
        }, keyHolder);

        // Set the game's id to the key from the GeneratedKeyHolder
        game.setGameId(keyHolder.getKey().intValue());

        // Return the game
        return game;
    }

    /**
     * Gets all games from database
     * @return List of all games
     */
    @Override
    public List<Game> getAll() {
        // Declare and initialize SQL statement
        final String sql = "SELECT id, answer, isFinished FROM games";

        // Return all games by using the SQL statement to query the database
        return jdbcTemplate.query(sql, new GameMapper());
    }

    /**
     * Finds a game based on a provided game_id
     * @param game_id id of the game to retrieve
     * @return game associated with the game_id
     */
    @Override
    public Game findById(int game_id) {
        // Declare and initialize SQL statement
        final String sql = "SELECT id, answer, isFinished FROM games "
                + "WHERE id = ?";

        // Return the game associated with the game_id by using the SQL statement
        // to query the database
        return jdbcTemplate.queryForObject(sql, new GameMapper(), game_id);
    }

    /**
     * Updates a game's answer and isFinished
     * @param game game object
     * @return boolean for successful/unsuccessful game update
     */
    @Override
    public boolean update(Game game) {
        // Declare and initialize SQL statement
        final String sql = "UPDATE games SET answer = ?, isFinished = ? "
                + "WHERE id = ?";

        // Updates and returns a boolean by using the SQL statement to
        // update a game in the database
        return jdbcTemplate.update(sql, game.getAnswer(), game.getIsFinished(),
                game.getGameId()) > 0;
    }

    /**
     * Deletes a game that corresponds to the game_id received
     * @param game_id game id
     * @return boolean for successful/unsuccessful game delete
     */
    @Override
    public boolean deleteById(int game_id) {
        // Declare and initialize SQL statement
        final String sql = "DELETE FROM games WHERE id = ?";

        // Updates and returns a boolean by using the SQL statement to
        // delete a game from the database
        return jdbcTemplate.update(sql, game_id) > 0;
    }

    /**
     * Inner class to turn a row of the ResultSet into an object
     */
    private static final class GameMapper implements RowMapper<Game> {
        /**
         * Looks at a single row of the ResultSet, pulls out the
         * data to create a new Game object
         * @param rs result set
         * @param index index of row to look at
         * @return Game object
         * @throws SQLException if error occurs with database
         */
        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game game = new Game();
            game.setGameId(rs.getInt("id"));
            game.setAnswer(rs.getString("answer"));
            game.setIsFinished(rs.getBoolean("isFinished"));
            return game;
        }
    }
}
