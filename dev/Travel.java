package dev;

import java.io.Serializable;
import java.math.BigDecimal;

public class Travel implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private String country;
    private Season season;
    private Priority priority;
    private String name;
    private BigDecimal budget;

    public Travel() {

    }

    public Travel(int id, String country, Season season, Priority priority, String name, BigDecimal budget) {
        this.id = id;
        this.country = country;
        this.season = season;
        this.priority = priority;
        this.name = name;
        this.budget = budget;

    }

    //GET METHODS
    public int getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public Season getSeason() {
        return season;
    }

    public Priority getPriority() {
        return priority;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    //SET METHODS
    public void setId(int id) {
        this.id = id;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    @Override
    public String toString() {
        return String.format(
                "| %-3d | %-15s | %-10s | %-10s | %-15s | %-10s |",
                id, country, season, priority, name, budget
        );
    }

}
