package org.example;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MyLinkedListTest {

    @Test
    @Tag("basic")
    @DisplayName("Перевірка getFirst після різних вставок")
    void getFirst_AfterFirstAndLastAdditions_ReturnsFirst() {
        MyLinkedList<String> list = new MyLinkedList<>();
        list.addFirst("Ira");
        list.addFirst("Maks");
        list.addFirst("Dima");
        list.addLast("Sasha");
        assertEquals("Dima", list.getFirst());
    }

    @Test
    @Tag("basic")
    @DisplayName("Перевірка getLast після різних вставок")
    void getLast_AfterFirstAndLastAdditions_ReturnsLast() {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        list.addLast(10);
        list.addFirst(20);
        list.addFirst(500);
        list.addLast(1);
        assertEquals(1, list.getLast());
    }

    @ParameterizedTest
    @Tag("advanced")
    @DisplayName("toString для списків різних типів")
    @MethodSource("toStringTestData")
    <T> void toString_ListOfArguments_ReturnsCorrectString(List<T> elements, String expectedResult) {
        MyLinkedList<T> list = new MyLinkedList<>();
        for (T elem : elements) {
            list.addLast(elem);
        }
        Assumptions.assumeTrue(list.size>0, "Skip this test for empty list");
        assertEquals(expectedResult, list.toString());
    }

    @ParameterizedTest
    @Tag("advanced")
    @DisplayName("Перевірка порядку елементів у списках")
    @MethodSource("toStringTestData")
    <T> void orderAndIndexesCheck(List<T> elements, String ignoredString) {
        MyLinkedList<T> list = new MyLinkedList<>();
        for (T elem : elements) {
            list.addLast(elem);
        }
        Assumptions.assumeTrue(list.size>0, "Skip this test for empty list");
        for (int i = 0; i < elements.size(); i++) {
            assertEquals(elements.get(i), list.get(i));
        }
    }

    @ParameterizedTest
    @Tag("basic")
    @DisplayName("Перевірка розміру списку")
    @MethodSource("toStringTestData")
    <T> void lengthCheck(List<T> elements, String ignoredString) {
        MyLinkedList<T> list = new MyLinkedList<>();
        for (T elem : elements) {
            list.addLast(elem);
        }

        assertEquals(elements.size(), list.size);
    }


    static Stream<Arguments> toStringTestData() {
        return Stream.of(
                org.junit.jupiter.params.provider.Arguments.of(List.of(), null),
                Arguments.of(List.of(500, 20, 10, 1), "[ 500 20 10 1 ]"),
                Arguments.of(List.of('t', 'e', 's', 't'), "[ t e s t ]"),
                Arguments.of(List.of("This", "a", "is", "test"), "[ This a is test ]")
        );
    }

    @Test
    @Tag("advanced")
    @DisplayName("Додавання елементів з індексами")
    void addWithPositions_ReturnsCorrectResultToString(){
        MyLinkedList<String> list = new MyLinkedList<>();
        list.add("first", 0);
        list.add("second", 1);
        list.add("fourth", 2);
        list.add("third", 2);
        assertEquals("[ first second third fourth ]", list.toString());
    }

    @Test
    @Tag("advanced")
    @DisplayName("Розмір нового порожнього списку")
    void size_OfNewEmptyList_Returns0(){
        MyLinkedList<String> list = new MyLinkedList<>();
        assertEquals(0, list.size);
    }

    @Test
    @Tag("advanced")
    @DisplayName("Розмір наповненого різними методами списку")
    void size_OfList_ReturnsCorrectSize(){
        MyLinkedList<Integer> list = new MyLinkedList<>();
        list.addFirst(1);
        list.addFirst(2);
        list.addLast(3);
        list.add(3,3);
        assertEquals(4, list.size);
    }

    @Test
    @Tag("advanced")
    @DisplayName("Розмір очищеного списку")
    void size_OfList_AfterRemovingAllElements_ReturnsCorrectSize(){
        MyLinkedList<Integer> list = new MyLinkedList<>();
        list.addFirst(1);
        list.add(3,1);
        list.removeFirst();
        list.removeLast();

        assertEquals(0, list.size);
    }

    @ParameterizedTest
    @Tag("basic")
    @DisplayName("Перевірка get за індексом")
    @CsvSource({"1, 10", "3, 15", "4, 2"})
    void get_ByIndex(int position, Integer expectedValue) {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        list.addLast(2);
        list.addFirst(20);
        list.addLast(1);
        list.addFirst(10);
        list.addFirst(500);
        list.add(15,3);
        assertEquals(expectedValue, list.get(position));
    }

    @Test
    @Tag("advanced")
    @DisplayName("Перший = останньому в списку з одного елемента")
    void getFirst_Equals_getLast_IfOneElement() {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        list.addLast(2);

        Assumptions.assumeTrue(list.size==1);
        assertSame(list.getFirst(), list.getLast());
    }

    @Test
    @Tag("advanced")
    @DisplayName("Перший за індексом = getFirst зі списку")
    void get_WithZeroIndex_Equals_getFirst() {
        MyLinkedList<String> list = new MyLinkedList<>();
        list.add("some", 0);
        list.add("words", 1);
        list.add("to", 2);
        list.add("put", 2);
        list.addFirst("here");
        assertSame(list.get(0), list.getFirst());
    }

    @Test
    @Tag("advanced")
    @DisplayName("Елемент з індексом size-1 = getLast зі списку")
    void get_WithSizeMinusOneIndex_Equals_getLast() {
        MyLinkedList<String> list = new MyLinkedList<>();
        list.add("some", 0);
        list.addLast("words");
        list.add("to", 2);
        list.add("put", 2);
        list.addFirst("here");
        assertSame(list.get(list.size-1), list.getLast());
    }

    @Test
    @Tag("exception_check")
    @DisplayName("Exception при намаганні додати елемент з неможливим індексом")
    void add_WithInvalidOutOfBoundsIndex_ThrowsException() {
        MyLinkedList<String> list = new MyLinkedList<>();
        list.addLast("words");
        assertThrows(NullPointerException.class, () -> list.add("to", 2));
    }

    @Test
    @Tag("exception_check")
    @DisplayName("Exception при намаганні взяти елемент з неможливим індексом")
    void get_WithInvalidOutOfBoundsIndex_ThrowsException() {
        MyLinkedList<String> list = new MyLinkedList<>();
        list.addLast("Hello");
        list.addLast("world!");
        assertThrows(NullPointerException.class, () -> list.get(2));
    }

    @Test
    @Tag("exception_check")
    @DisplayName("Exception при намаганні взяти елемент з порожнього списку")
    void getFirst_FromEmptyList_ThrowsException() {
        MyLinkedList<String> list = new MyLinkedList<>();
        assertThrows(NullPointerException.class, list::getFirst);
    }

    @Test
    @Tag("exception_check")
    @DisplayName("Ігнорування при намаганні видалити перший елемент з порожнього списку")
    void removeFirst_FromEmptyList_ThrowsException() {
        MyLinkedList<String> list = new MyLinkedList<>();
        assertDoesNotThrow(list::removeFirst);
    }

    @Test
    @Tag("exception_check")
    @DisplayName("Ігнорування при намаганні видалити останній елемент з порожнього списку")
    void removeLast_FromEmptyList_ThrowsException() {
        MyLinkedList<String> list = new MyLinkedList<>();
        assertDoesNotThrow(list::removeLast);
    }


}