package utils;

public final class CardUtils {
    public CardUtils() {
    }
    public static String generate3Random() {
        String newRandom = "";
        for(int i = 0; i < 3; i++) {
            int newNumber = (int) (Math.random() * 10);
            newRandom += String.valueOf(newNumber);
        }
        return newRandom;
    }
    public static String generateRandomN(int n) {
        String newRandom = "";
        for (int i = 0; i < n; i++) {
            int rand = (int) (Math.random() * 10);
            newRandom += String.valueOf(rand);
        }
        return newRandom;
    }

}
