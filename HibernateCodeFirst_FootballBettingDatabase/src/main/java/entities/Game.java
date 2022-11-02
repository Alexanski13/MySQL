package entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table
public class Game extends BaseEntity  implements Serializable {

    @OneToOne
    @JoinColumn
    private Team homeTeam;

    @OneToOne
    @JoinColumn
    private Team awayTeam;

    @Column
    private Short homeGoals;

    @Column
    private Short awayGoals;

    @Column
    private Date dateAndTime;

    @Column
    private Double homeTeamWinBetRate;

    @Column
    private Double awayTeamWinBetRate;

    @Column
    private Double drawGameBetRate;

    @ManyToOne
    @JoinColumn
    private Round rounds;

    @ManyToOne
    @JoinColumn
    private Competition competition;

    public Game() {
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Short getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(Short homeGoals) {
        this.homeGoals = homeGoals;
    }

    public Short getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(Short awayGoals) {
        this.awayGoals = awayGoals;
    }

    public Date getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Date dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public Double getHomeTeamWinBetRate() {
        return homeTeamWinBetRate;
    }

    public void setHomeTeamWinBetRate(Double homeTeamWinBetRate) {
        this.homeTeamWinBetRate = homeTeamWinBetRate;
    }

    public Double getAwayTeamWinBetRate() {
        return awayTeamWinBetRate;
    }

    public void setAwayTeamWinBetRate(Double awayTeamWinBetRate) {
        this.awayTeamWinBetRate = awayTeamWinBetRate;
    }

    public Double getDrawGameBetRate() {
        return drawGameBetRate;
    }

    public void setDrawGameBetRate(Double drawGameBetRate) {
        this.drawGameBetRate = drawGameBetRate;
    }

    public Round getRounds() {
        return rounds;
    }

    public void setRounds(Round rounds) {
        this.rounds = rounds;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }
}
