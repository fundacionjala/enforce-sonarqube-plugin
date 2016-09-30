package org.fundacionjala.sonarqube;

public class Tuple<T, U> {

    private T firstElement;
    private U secondElement;

    public Tuple(T firstElement, U secondElement) {
        this.firstElement = firstElement;
        this.secondElement = secondElement;
    }

    public T getFirstElement() {
        return firstElement;
    }

    public U getSecondElement() {
        return  secondElement;
    }


    private static <T, U> Tuple<T, U> newTuple(T first, U second) {
        return new Tuple<>(first, second);
    }

    public <T, U> Tuple<T, U> newTuple1(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple2(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple3(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple4(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple5(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple6(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple7(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple8(T first, U second) { return newTuple(first, second); }

    public <T, U> Tuple<T, U> newTuple9(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple10(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple11(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple12(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple14(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple16(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple17(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple18(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple19(T first, U second) {
        return newTuple(first, second);
    }

    public <T, U> Tuple<T, U> newTuple20(T first, U second) {
        return newTuple(first, second);
    }
}
