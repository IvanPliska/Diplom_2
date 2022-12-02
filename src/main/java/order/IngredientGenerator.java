package order;

import java.util.Random;

public class IngredientGenerator {

    public static String generateBun() {
        String[] id = new String[]{
                "61c0c5a71d1f82001bdaaa6d",
                "61c0c5a71d1f82001bdaaa6c"};
        return id[new Random().nextInt(id.length)];
    }

    public static String generateMain() {
        String[] id = new String[]{
                "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa70",
                "61c0c5a71d1f82001bdaaa71", "61c0c5a71d1f82001bdaaa6e",
                "61c0c5a71d1f82001bdaaa76", "61c0c5a71d1f82001bdaaa77",
                "61c0c5a71d1f82001bdaaa78", "61c0c5a71d1f82001bdaaa79",
                "61c0c5a71d1f82001bdaaa7a"};
        return id[new Random().nextInt(id.length)];
    }

    public static String generateSauce() {
        String[] id = new String[]{
                "61c0c5a71d1f82001bdaaa72", "61c0c5a71d1f82001bdaaa73",
                "61c0c5a71d1f82001bdaaa74", "61c0c5a71d1f82001bdaaa75"};
        return id[new Random().nextInt(id.length)];
    }

    public static String generateIngredient() {
        return generateBun() + generateMain() + generateSauce();
    }
}
