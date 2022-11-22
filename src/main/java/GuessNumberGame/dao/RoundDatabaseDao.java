package GuessNumberGame.dao;

import GuessNumberGame.model.Round;

import java.sql.*;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

/**
 * The {@code RoundDatabaseDao} class is responsible for interactions
 * with the rounds table in the SQL database.
 */
@Repository
public class RoundDatabaseDao implements RoundDao {
    // Declare JdbcTemplate object
    private final JdbcTemplate jdbcTemplate;

    /**
     * RoundDatabaseDao constructor initializes JdbcTemplate object
     * @param jdbcTemplate JdbcTemplate object
     */
    @Autowired
    public RoundDatabaseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Adds a round to the database
     * @param round round object to be added
     * @return round object that was added
     */
    @Override
    public Round add(Round round) {
        // Declare and initialize SQL statement
        final String sql = "INSERT INTO rounds(gameId, timestamp, guess, guessResult)"
                + " VALUES(?,?,?,?)";

        // Declare and initialize new GeneratedKeyHolder
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        // JdbcTemplate accepts 2 parameters, a PreparedStatementCreator
        // and a GeneratedKeyHolder
        jdbcTemplate.update((Connection conn) -> {
            // Generate a PreparedStatement from a Connection
            PreparedStatement statement = conn.prepareStatement(
                    sql, Statement.RETURN_GENERATED_KEYS);
            // Get the Round's information
            statement.setInt(1, round.getGameId());
            statement.setTimestamp(2, round.getTimeStamp());
            statement.setString(3, round.getGuess());
            statement.setString(4, round.getGuessResult());
            // Return the prepared statement
            return statement;
        }, keyHolder);

        // Set the round's id to the key from the GeneratedKeyHolder
        round.setId(keyHolder.getKey().intValue());

        // Return the round
        return round;
    }

    /**
     * Gets all rounds from the database
     * @return list of all rounds
     */
    @Override
    public List<Round> getAll() {
        // Declare and initialize SQL statement
        final String sql = "SELECT id, gameId, timestamp, guess, guessResult "
                + "FROM rounds";

        // Return all rounds by using the SQL statement to query the database
        return jdbcTemplate.query(sql, new RoundMapper());
    }

    /**
     * Finds all rounds for a Game based on a provided game_id
     * @param game_id game id
     * @return list of Round objects
     */
    @Override
    public List<Round> getAllOfGame(int game_id) {
        // Declare and initialize SQL statement
        final String sql = "SELECT * FROM rounds WHERE gameId = ?"
                + " ORDER BY timestamp";

        // Return a list of Round objects associated with the
        // game_id by using the SQL statement to query the database
        return jdbcTemplate.query(sql, new RoundMapper(), game_id);
    }

    /**
     * Finds a round based on a provided round_id
     * @param round_id id of round to retrieve
     * @return round associated with the round_id
     */
    @Override
    public Round findById(int round_id) {
        // Declare and initialize SQL statement
        final String sql = "SELECT id, gameId, timestamp, guess, guessResult "
                + "FROM rounds WHERE id = ?";

        // Return the round associated with the round_id by using the SQL
        // statement to query the database
        return jdbcTemplate.queryForObject(sql, new RoundMapper(), round_id);
    }

    /**
     * Updates a round's values
     * @param round round object
     * @return false (rounds are not to be updated)
     */
    @Override public boolean update(Round round) {
        return false;
    }

    /**
     * Deletes a round that corresponds to the round_id received
     * @param round_id round id
     * @return boolean for successful/unsuccessful round delete
     */
    @Override
    public boolean deleteById(int round_id) {
        // Declare and initialize SQL statement
        final String sql = "DELETE FROM rounds WHERE id = ?";

        // Updates and returns a boolean by using the SQL statement to
        // delete a round from the database
        return jdbcTemplate.update(sql, round_id) > 0;
    }

    /**
     * Inner class to turn a row of the ResultSet into an object
     */
    private static final class RoundMapper implements RowMapper<Round> {
        /**
         * Looks at a single row of the ResultSet, pulls out the
         * data to create a new Round object
         * @param rs result set
         * @param index index of row to look at
         * @return Round object
         * @throws SQLException if error occurs with database
         */
        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round round = new Round();
            round.setId(rs.getInt("id"));
            round.setGameId(rs.getInt("gameId"));
            round.setTimeStamp(rs.getTimestamp("timestamp"));
            round.setGuess(rs.getString("guess"));
            round.setGuessResult(rs.getString("guessResult"));
            return round;
        }
    }
}
