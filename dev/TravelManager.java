package dev;

import java.math.*;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class TravelManager implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Travel> travels;
    private int nextId;

    public TravelManager() {
        this.travels = new ArrayList<>();
        this.nextId = 1;
    }

    //METHOD TO ADD A NEW TRAVEL
    public void addTravel(String country, Season season, Priority priority, String name, BigDecimal budget) {
        Travel newTravel = new Travel(nextId++, country, season, priority, name, budget);
        travels.add(newTravel);
    }

    //METHOD TO GET ALL TRAVELS
    public List<Travel> getAllTravels() {
        return new ArrayList<>(travels);
    }

    //METHOD TO FIND A TRAVEL BY PRIORITY
    public List<Travel> findTravelsByPriority(Priority priority) {
        List<Travel> result = new ArrayList<>();
        for (Travel travel : travels) {
            if (travel.getPriority() == priority) {
                result.add(travel);
            }
        }
        return result;
    }

    //METHOD TO FIND A TRAVEL BY SEASON
    public List<Travel> findTravelsBySeason(Season season) {
        List<Travel> result = new ArrayList<>();
        for (Travel travel : travels) {
            if (travel.getSeason() == season) {
                result.add(travel);
            }
        }
        return result;
    }

    //METHOD TO GET TOTAL BUDGET
    public BigDecimal getTotalBudget() {
        BigDecimal total = BigDecimal.ZERO;
        for (Travel travel : travels) {
            total = total.add(travel.getBudget());
        }
        return total;
    }

    //METHOD TO CLEAR ALL TRAVELS
    public void clearAllTravels() {
        travels.clear();
        nextId = 1;
    }

    //METHOD TO GET THE NUMBER OF TRAVELS
    public int getNumberOfTravels() {
        return travels.size();
    }

    //METHOD TO CHECK IF THERE ARE ANY TRAVELS
    public boolean hasTravels() {
        return !travels.isEmpty();
    }

    //METHOD TO REMOVE A TRAVEL BY ID
    public boolean removeTravelById(long id) {
        return travels.removeIf(travel -> travel.getId() == id);
    }

    //METHOD TO GET A TRAVEL BY ID
    public Travel getTravelById(long id) {
        for (Travel travel : travels) {
            if (travel.getId() == id) {
                return travel;
            }
        }
        return null;
    }

    //METHOD TO UPDATE A TRAVEL BY ID
    public boolean updateTravelById(int id, String country, Season season, Priority priority, String name, BigDecimal budget) {
        Travel travel = getTravelById(id);
        if (travel != null) {
            travels.remove(travel);
            Travel updatedTravel = new Travel(id, country, season, priority, name, budget);
            travels.add(updatedTravel);
            return true;
        }
        return false;
    }

    //METHOD TO GET TRAVELS WITH BUDGET ABOVE A CERTAIN AMOUNT
    public List<Travel> getTravelsWithBudgetAbove(BigDecimal amount) {
        List<Travel> result = new ArrayList<>();
        for (Travel travel : travels) {
            if (travel.getBudget().compareTo(amount) > 0) {
                result.add(travel);
            }
        }
        return result;
    }

    //METHOD TO GET TRAVELS WITH BUDGET BELOW A CERTAIN AMOUNT
    public List<Travel> getTravelsWithBudgetBelow(BigDecimal amount) {
        List<Travel> result = new ArrayList<>();
        for (Travel travel : travels) {
            if (travel.getBudget().compareTo(amount) < 0) {
                result.add(travel);
            }
        }
        return result;
    }

    //METHOD TO SORT TRAVELS BY BUDGET
    public List<Travel> getTravelsSortedByBudget() {
        List<Travel> sortedTravels = new ArrayList<>(travels);
        sortedTravels.sort((t1, t2) -> t1.getBudget().compareTo(t2.getBudget()));
        return sortedTravels;
    }

    //METHOD TO SORT TRAVELS BY COUNTRY
    public List<Travel> getTravelsSortedByCountry() {
        List<Travel> sortedTravels = new ArrayList<>(travels);
        sortedTravels.sort((t1, t2) -> t1.getCountry().compareTo(t2.getCountry()));
        return sortedTravels;
    }

    //METHOD TO SORT TRAVELS BY SEASON
    public List<Travel> getTravelsSortedBySeason() {
        List<Travel> sortedTravels = new ArrayList<>(travels);
        sortedTravels.sort((t1, t2) -> t1.getSeason().compareTo(t2.getSeason()));
        return sortedTravels;
    }

    //METHOD TO SORT TRAVELS BY PRIORITY
    public List<Travel> getTravelsSortedByPriority() {
        List<Travel> sortedTravels = new ArrayList<>(travels);
        sortedTravels.sort((t1, t2) -> t1.getPriority().compareTo(t2.getPriority()));
        return sortedTravels;
    }

    //METHOD TO GET AVERAGE BUDGET
    public BigDecimal getAverageBudget() {
        if (travels.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal total = getTotalBudget();
        return total.divide(BigDecimal.valueOf(travels.size()), BigDecimal.ROUND_HALF_UP);
    }

    //METHOD TO GET MAXIMUM BUDGET
    public BigDecimal getMaximumBudget() {
        BigDecimal max = BigDecimal.ZERO;
        for (Travel travel : travels) {
            if (travel.getBudget().compareTo(max) > 0) {
                max = travel.getBudget();
            }
        }
        return max;
    }

    //METHOD TO GET MINIMUM BUDGET
    public BigDecimal getMinimumBudget() {
        if (travels.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal min = travels.get(0).getBudget();
        for (Travel travel : travels) {
            if (travel.getBudget().compareTo(min) < 0) {
                min = travel.getBudget();
            }
        }
        return min;
    }

    //METHOD TO CHECK IF A TRAVEL EXISTS BY NAME
    public boolean travelExistsByName(String name) {
        for (Travel travel : travels) {
            if (travel.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    //METHOD TO GET TRAVELS BY COUNTRY
    public List<Travel> getTravelsByCountry(String country) {
        List<Travel> result = new ArrayList<>();
        for (Travel travel : travels) {
            if (travel.getCountry().equalsIgnoreCase(country)) {
                result.add(travel);
            }
        }
        return result;
    }

    //METHOD TO GET TRAVELS BY NAME
    public List<Travel> getTravelsByName(String name) {
        List<Travel> result = new ArrayList<>();
        for (Travel travel : travels) {
            if (travel.getName().equalsIgnoreCase(name)) {
                result.add(travel);
            }
        }
        return result;
    }

    //METHOD TO GET THE HIGHEST PRIORITY TRAVEL
    public Travel getHighestPriorityTravel() {
        if (travels.isEmpty()) {
            return null;
        }
        Travel highestPriorityTravel = travels.get(0);
        for (Travel travel : travels) {
            if (travel.getPriority().compareTo(highestPriorityTravel.getPriority()) < 0) {
                highestPriorityTravel = travel;
            }
        }
        return highestPriorityTravel;
    }

    //METHOD TO GET THE LOWEST PRIORITY TRAVEL
    public Travel getLowestPriorityTravel() {
        if (travels.isEmpty()) {
            return null;
        }
        Travel lowestPriorityTravel = travels.get(0);
        for (Travel travel : travels) {
            if (travel.getPriority().compareTo(lowestPriorityTravel.getPriority()) > 0) {
                lowestPriorityTravel = travel;
            }
        }
        return lowestPriorityTravel;
    }

    //METHOD TO GET A SUMMARY OF TRAVELS
    public String getTravelSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append(String.format("| %-3s | %-15s | %-10s | %-10s | %-15s | %-10s |\n", "ID", "Country", "Season", "Priority", "Name", "Budget"));
        summary.append("--------------------------------------------------------------------------------\n");
        for (Travel travel : travels) {
            summary.append(travel.toString()).append("\n");
        }
        return summary.toString();
    }
}
