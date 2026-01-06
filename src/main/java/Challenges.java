/* (C)2024 */
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* (C)2024 */
public class Challenges {

    /* *****
    Challenge 1

    "Readable Time"

    The function "readableTime" accepts a positive number as argument,
    you should be able to modify the function to return the time from seconds
    into a human readable format.

    Example:

    Invoking "readableTime(3690)" should return "01:01:30" (HH:MM:SS)
    ***** */
    public Integer[] divisionWithRemaind(Integer dividend, Integer divisor ){
        Integer[] parts = new Integer[2];

        parts[0] = divisor == 0 ? dividend : dividend / divisor;
        parts[1] = divisor == 0 ? divisor : dividend % divisor;

        return parts;
    }
    public String readableTime(Integer seconds) {
        Integer seconds_f, minutes_f, hours_f;

        Integer[] aux = new Integer[2];

        aux = divisionWithRemaind(seconds, 60);
        seconds_f = aux[1];
        minutes_f = aux[0];
        aux = divisionWithRemaind(minutes_f, 60);
        minutes_f = aux[1];
        hours_f = aux[0];

        String read_s = seconds_f < 10 ? "0" + seconds_f : "" + seconds_f;
        String read_m = minutes_f < 10 ? "0" + minutes_f : "" + minutes_f;
        String read_h = hours_f < 10 ? "0" + hours_f : "" + hours_f;

        return read_h + ":" + read_m + ":" + read_s;

    }
    ;

    /* *****
    Challenge 2

    "Circular Array"

    Given the following array "COUNTRY_NAMES", modify the function "circularArray"
    to return an array that meets the following criteria:

    - The index number passed to the function should be the first element in the resulting array
    - The resulting array must have the same length as the initial array
    - The value of the argument "index" will always be a positive number

    Example:

    Invoking "circularArray(2)" should return "["Island", "Japan", "Israel", "Germany", "Norway"]"
    ***** */

    public String[] circularArray(int index) {
        String[] COUNTRY_NAMES = {"Germany", "Norway", "Island", "Japan", "Israel"};

        if (index > COUNTRY_NAMES.length) {
            index = index % COUNTRY_NAMES.length;
        }

        String[] AUX_COUNTRY_NAME = new String[COUNTRY_NAMES.length];
        for (int i = index; i < COUNTRY_NAMES.length ; i++) {
            AUX_COUNTRY_NAME[i - index] = COUNTRY_NAMES[i];
        }
        for (int i = 0; i <  index ; i++) {
            AUX_COUNTRY_NAME[COUNTRY_NAMES.length - (index - i)] = COUNTRY_NAMES[i];
        }

        return AUX_COUNTRY_NAME;
    }
    ;

    /* *****
    Challenge 3

    "Own Powers"

    The function "ownPower" accepts two arguments. "number" and "lastDigits".

    The "number" indicates how long is the series of numbers you are going to work with, your
    job is to multiply each of those numbers by their own powers and after that sum all the results.

    "lastDigits" is the length of the number that your function should return, as a string!.
    See example below.

    Example:

    Invoking "ownPower(10, 3)" should return "317"
    because 1^1 + 2^2 + 3^3 + 4^4 + 5^5 + 6^6 + 7^7 + 8^8 + 9^9 + 10^10 = 10405071317
    The last 3 digits for the sum of powers from 1 to 10 is "317"
    ***** */


    public String ownPower(int number, int lastDigits) {
        BigInteger result = new BigInteger("0");
        for (int i = 1; i <= number ; i++){
            BigInteger aux = BigInteger.valueOf(i);
            aux = aux.pow(i);
            result = result.add(aux);
        }
        String f_result = result.toString();
        int length = f_result.length();
        return f_result.substring(length - lastDigits);
    }
    ;

    /* *****
    Challenge 4

    "Sum of factorial digits"

    A factorial (x!) means x! * (x - 1)... * 3 * 2 * 1.
    For example: 10! = 10 × 9 × ... × 3 × 2 × 1 = 3628800

    Modify the function "digitSum" to return a number that
    equals to the sum of the digits in the result of 10!

    Example:

    Invoking "digitSum(10)" should return "27".
    Since 10! === 3628800 and you sum 3 + 6 + 2 + 8 + 8 + 0 + 0
    ***** */
    public BigInteger factorial(int n) {
        BigInteger result = BigInteger.valueOf(n);
        if (n == 0 || n == 1){
            return result;
        }
        return result.multiply(factorial(n-1));
    }
    public Integer digitSum(int n) {
        BigInteger factorial = factorial(n);
        int sum = 0;
        while (factorial.compareTo(BigInteger.ZERO) > 0) {
            BigInteger digit = factorial.mod(BigInteger.TEN);
            sum += digit.intValue();
            factorial = factorial.divide(BigInteger.TEN);
        }

        return sum;
    }


    /**
     * Decryption.
     * Create a decryption function that takes as parameter an array of ASCII values. The addition between values is the ascii value decrypted.
     * decrypt([ 72, 33, -73, 84, -12, -3, 13, -13, -68 ]) ➞ "Hi there!"
     * H = 72, the sum of H 72 and 33 gives 105 which ascii value is i;
     * The function must return the string encoded using the encryption function below.
     *
     * @param ascivalues  hand, player2 hand
     */
    public String decrypt(List<Integer> ascivalues) {
        // YOUR CODE HERE...
        return "";
    }

    /**
     * Encryption Function.
     * Create am encryption function that takes a string and converts into an array of ASCII character values.
     * encrypt("Hello") ➞ [72, 29, 7, 0, 3]
     * // H = 72, the difference between the H and e is 29
     * The function must return an array of integer ascii values.
     *
     * @param text  hand, player2 hand
     */
    public List<Integer> encrypt(String text) {
        // YOUR CODE HERE...
        return Collections.emptyList();
    }
}
