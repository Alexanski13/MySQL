package entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table
public class PlayerStatistics implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @Id
    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @Column(name = "scoredGoals")
    private short scoredGoals;

    @Column(name = "assists")
    private short assists;

    @Column(name = "minutes_played")
    private short minutesPlayed;

    public PlayerStatistics() {
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public short getScoredGoals() {
        return scoredGoals;
    }

    public void setScoredGoals(short scoredGoals) {
        this.scoredGoals = scoredGoals;
    }

    public short getAssists() {
        return assists;
    }

    public void setAssists(short assists) {
        this.assists = assists;
    }

    public short getMinutesPlayed() {
        return minutesPlayed;
    }

    public void setMinutesPlayed(short minutesPlayed) {
        this.minutesPlayed = minutesPlayed;
    }
}
