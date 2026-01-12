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

    public CardWinner makePlayer(String name, List<Integer> hand) {
        hand = hand.stream()
                .sorted(Comparator.reverseOrder())
                .limit(2)
                .toList();
        Integer mayorNum = hand.getFirst() * 10 + hand.getLast();
        return new CardWinner(name, mayorNum);
    }

    public CardWinner calculateWinningHand(List<Integer> player1, List<Integer> player2) {
        CardWinner winner1 = makePlayer("P1", player1);
        CardWinner winner2 = makePlayer("P2", player2);

        Integer mayor1 = winner1.getWinTotal();
        Integer mayor2 = winner2.getWinTotal();

        if (Objects.equals(mayor2, mayor1)) {
            return new CardWinner("TIE", mayor2);
        }
        return mayor1 > mayor2 ? winner1 : winner2;
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

    public double getTotal(CallCostObject call) {
        int duration = call.getDuration();
        return switch (call.getType()) {
            case "International" -> {

                double basicCost = Math.min(3, duration) * 7.56;
                yield duration <= 3
                        ? basicCost
                        : basicCost + (duration - 3) * 3.03;
            }
            case "National" -> {
                double basicCost = Math.min(3, duration) * 1.20;
                yield duration <= 3
                        ? basicCost
                        : basicCost + (duration - 3) * 0.48;
            }
            case "Local" -> {
                yield duration * 0.2;
            }
            default -> 0.0;
        };
    }

    public CallSummary getSummary(CallCostObject call){
        return new CallSummary(call, getTotal(call));
    }

    private final static List<String> VALID_TYPES_OF_CALLS = List.of(
            "International",
            "National",
            "Local"
    );

    public boolean isValidCall(CallCostObject call){
        return VALID_TYPES_OF_CALLS.contains(call.getType());
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
