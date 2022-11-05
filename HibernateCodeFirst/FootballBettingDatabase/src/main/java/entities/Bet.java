package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table
public class Bet extends BaseEntity {

    @Column
    private BigDecimal betMoney;

    @Column
    private Date timeOfBet;

    @ManyToOne
    private User user;

    public Bet() {
    }

    public BigDecimal getBetMoney() {
        return betMoney;
    }

    public void setBetMoney(BigDecimal betMoney) {
        this.betMoney = betMoney;
    }

    public Date getTimeOfBet() {
        return timeOfBet;
    }

    public void setTimeOfBet(Date timeOfBet) {
        this.timeOfBet = timeOfBet;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
