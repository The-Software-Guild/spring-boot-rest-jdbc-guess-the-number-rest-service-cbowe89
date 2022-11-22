package GuessNumberGame.dao;

import GuessNumberGame.model.Round;

import java.sql.*;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class RoundDatabaseDao implements RoundDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RoundDatabaseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Round add(Round round) {

        final String sql = "INSERT INTO rounds(gameId, timestamp, guess, guessResult)"
                + " VALUES(?,?,?,?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, round.getGameId());
            statement.setTimestamp(2, round.getTimeStamp());
            statement.setString(3, round.getGuess());
            statement.setString(4, round.getGuessResult());
            return statement;

        }, keyHolder);

        round.setId(keyHolder.getKey().intValue());

        return round;
    }

    @Override
    public List<Round> getAll() {
        final String sql = "SELECT id, gameId, timestamp, guess, guessResult "
                + "FROM rounds";
        return jdbcTemplate.query(sql, new RoundMapper());
    }

    @Override
    public List<Round> getAllOfGame(int game_id) {
        final String sql = "SELECT * FROM rounds WHERE gameId = ?"
                + " ORDER BY timestamp";
        return jdbcTemplate.query(sql, new RoundMapper(), game_id);
    }

    @Override
    public Round findById(int round_id) {
        final String sql = "SELECT id, gameId, timestamp, guess, guessResult "
                + "FROM rounds WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new RoundMapper(), round_id);
    }

    @Override public boolean update(Round round) {
        return false;
    }

    @Override
    public boolean deleteById(int round_id) {
        final String sql = "DELETE FROM rounds WHERE id = ?";
        return jdbcTemplate.update(sql, round_id) > 0;
    }

    private static final class RoundMapper implements RowMapper<Round> {
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
