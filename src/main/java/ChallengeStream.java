/* (C)2024 */

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import mocks.CallCostObject;
import mocks.CallSummary;
import mocks.CardWinner;
import mocks.TotalSummary;

public class ChallengeStream {

    /**
     * One stack containing five numbered cards from 0-9 are given to both players. Calculate which hand has winning number.
     * The winning number is calculated by which hard produces the highest two-digit number.
     * <p>
     * calculateWinningHand([2, 5, 2, 6, 9], [3, 7, 3, 1, 2]) ➞ true
     * P1 can make the number 96
     * P2 can make the number 73
     * P1 win the round since 96 > 73
     * <p>
     * The function must return which player hand is the winner and the two-digit number produced. The solution must contain streams.
     *
     * @param player1 hand, player2 hand
     */

    public int calculateBestTwoDigit(List<Integer> hand) {
        return hand.stream().sorted(Comparator.reverseOrder()).limit(2).reduce(0, (a, b) -> a * 10 + b);
    }

    public CardWinner calculateWinningHand(List<Integer> player1, List<Integer> player2) {
        Integer mayor1 = calculateBestTwoDigit(player1);
        Integer mayor2 = calculateBestTwoDigit(player2);

        if (Objects.equals(mayor2, mayor1)) {
            return new CardWinner("TIE", mayor2);
        }
        return mayor1 > mayor2 ? new CardWinner("P1", mayor1) : new CardWinner("P2", mayor2);
    }

    /**
     * Design a solution to calculate what to pay for a set of phone calls. The function must receive an
     * array of objects that will contain the identifier, type and duration attributes. For the type attribute,
     * the only valid values are: National, International and Local
     * <p>
     * The criteria for calculating the cost of each call is as follows:
     * <p>
     * International: first 3 minutes $ 7.56 -> $ 3.03 for each additional minute
     * National: first 3 minutes $ 1.20 -> $ 0.48 per additional minute
     * Local: $ 0.2 per minute.
     * <p>
     * The function must return the total calls, the details of each call (the detail received + the cost of the call)
     * and the total to pay taking into account all calls. The solution must be done only using streams.
     *
     * @param {Call[]} calls - Call's information to be processed
     * @returns {CallsResponse}  - Processed information
     */

    public enum CallType {
        INTERNATIONAL,
        NATIONAL,
        LOCAL;

        public int freeTierMinutes() {
            return switch (this) {
                case INTERNATIONAL, NATIONAL -> 3;
                case LOCAL -> 0;
            };
        }

        public double freeTierCost() {
            return switch (this) {
                case INTERNATIONAL -> 7.56;
                case NATIONAL -> 1.20;
                case LOCAL -> 0.0;
            };
        }

        public double outTierCost() {
            return switch (this) {
                case INTERNATIONAL -> 3.03;
                case NATIONAL -> 0.48;
                case LOCAL -> 0.20;
            };
        }

        public double calculateCost(int duration) {
            return switch (this) {
                case LOCAL -> duration * outTierCost();
                default -> {
                    double basicCost = Math.min(duration, freeTierMinutes()) * freeTierCost();
                    if (duration <= freeTierMinutes()) {
                        yield basicCost;
                    } else {
                        yield basicCost + (duration - freeTierMinutes()) * outTierCost();
                    }
                }
            };
        }
    }

    public double getTotal(CallCostObject call) {
        int duration = call.getDuration();
        try{
            CallType type = CallType.valueOf(call.getType().toUpperCase());
            return type.calculateCost(duration);
        } catch(Exception e){
            return 0.0;
        }
    }

    public CallSummary getSummary(CallCostObject call) {
        return new CallSummary(call, getTotal(call));
    }

    public boolean isValidCall(CallCostObject call){
        try{
            CallType type = CallType.valueOf(call.getType().toUpperCase());
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public TotalSummary calculateCost(List<CallCostObject> costObjectList) {
        List<CallSummary> finalSummary = costObjectList.stream()
                .filter(this::isValidCall)
                .map(this::getSummary)
                .toList();
        double totalCost = finalSummary.stream()
                .mapToDouble(CallSummary::getTotalCost)
                .sum();
        return new TotalSummary(finalSummary, finalSummary.size(), totalCost);
    }
}
