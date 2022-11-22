package GuessNumberGame.model;

import java.sql.Timestamp;
import java.util.Objects;

public class Round {
    private int id;
    int gameId;
    Timestamp timestamp;
    String guess;
    String result;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public Timestamp getTimeStamp() {
        return timestamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timestamp = timeStamp;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public String getGuessResult() {
        return result;
    }

    public void setGuessResult(String guessResult) {
        this.result = guessResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Round round = (Round) o;
        return id == round.id
                && gameId == round.gameId
                && Objects.equals(timestamp, round.timestamp)
                && Objects.equals(guess, round.guess)
                && Objects.equals(result, round.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gameId, timestamp, guess, result);
    }
}
