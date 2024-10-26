/**
 * Represents a slot in a vending meal machine that can hold food objects.
 * Each slot holds information about the number and name of food objects it contains.
 */
public class Slot {
    int foodNumberInSlot = 0;
    String foodName;
    Food food = new Food(-6); //Inıtıalizatıon with -6 not to keep it in range ±5 of zero.
}
