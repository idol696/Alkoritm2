package test;

import exceptions.ArrayIntegerListOverflowException;
import models.ArrayIntegerList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static interfaces.IntegerList.DELETE_MODE.*;
import static interfaces.IntegerList.SORT.*;
import static org.junit.jupiter.api.Assertions.*;

class ArrayIntegerListTest {

    ArrayIntegerList array = new ArrayIntegerList(10);

    @Test
    @DisplayName("Constructor: Инициализация копией массива")
    void constructorCopy() {
        ArrayIntegerList arrayNew = new ArrayIntegerList(1000);
        for (int i = 0; i < 1000; i++) arrayNew.add(1);
        ArrayIntegerList arrayCopy = new ArrayIntegerList(arrayNew);
        assertArrayEquals(arrayCopy.toArray(), arrayNew.toArray());
    }


    @Test
    @DisplayName("Constructor: Инициализация предельного количества элементов в конструкторе (память)")
    void constructorInit() {
        ArrayIntegerList arrayNew = new ArrayIntegerList(1000);
        for (int i = 0; i < 1000; i++) arrayNew.add(1);
        assertEquals(1000, arrayNew.size());
    }

    @Test
    @DisplayName("Constructor: Инициализация ошибочного количества элементов в конструкторе")
    void constructorZeroInit() {
        assertThrows(ArrayIntegerListOverflowException.class, () -> new ArrayIntegerList(0), "Нулевая длина: провалена");
        assertThrows(ArrayIntegerListOverflowException.class, () -> new ArrayIntegerList(3001), "Максимальная длина сверх 1000: провалена");
    }

    @Test
    @DisplayName("Метод Add: Проверка добавления null")
    void addNull() {
        array.add(null);
        assertNull(array.get(0), "Получение null: провалено");
        assertEquals(array.size(), 1, "Размер массива 1: провалено");
    }


    @Test
    @DisplayName("Метод Add: Проверка добавления по значению")
    void addElement() {
        array.add(0);
        assertEquals(0, array.get(0));
    }

    @Test
    @DisplayName("Метод Add: Проверка добавления нескольких элементов")
    void addElements() {
        array.add(1);
        array.add(2);
        array.add(3);
        array.add(0);
        assertTrue(array.get(0) == 1 && array.get(1) == 2 && array.get(2) == 3 && array.get(3) == 0);
        assertEquals(array.size(), 4, "Размер массива 1: провалено");
    }

    @Test
    @DisplayName("Метод Add: Проверка добавления 1000 элементов (grow)")
    void add1000Elements() {
        for (int i = 0; i < 1000; i++) {
            array.add(1);
        }
        assertEquals(array.size(), 1000, "Размер массива 1000: провалено");
    }

    @Test
    @DisplayName("Метод Add: Проверка вставки элемента в начало")
    void testAddFirstInsert() {
        array.add(1);
        array.add(2);
        array.add(3);
        array.add(0, 0);
        assertTrue(array.get(0) == 0 && array.get(1) == 1 && array.get(2) == 2 && array.get(3) == 3, "Валидность строк: провалено");
        assertEquals(array.size(), 4, "Размер массива 1: провалено");
    }

    @Test
    @DisplayName("Метод Add: Проверка вставки элемента в конец")
    void testAddLastInsert() {
        array.add(1);
        array.add(2);
        array.add(3);
        array.add(3, 0);
        assertTrue(array.get(0) == 1 && array.get(1) == 2 && array.get(2) == 3 && array.get(3) == 0, "Валидность строк: провалено");
        assertEquals(array.size(), 4, "Размер массива 1: провалено");
    }

    @Test
    @DisplayName("Метод Add: Проверка вставки элемента в середину")
    void testAddMiddleInsert() {
        array.add(1);
        array.add(2);
        array.add(3);
        array.add(2, 0);
        assertTrue(array.get(0) == 1 && array.get(1) == 2 && array.get(2) == 0 && array.get(3) == 3, "Валидность строк: провалено");
        assertEquals(array.size(), 4, "Размер массива 1: провалено");
    }

    @Test
    @DisplayName("Метод Add: Проверка возвращаемого значения")
    void addReturn() {
        Integer actual = array.add(8);
        assertEquals(8, actual);
    }

    @Test
    @DisplayName("Метод Set: Проверка присвоения значения")
    void set() {
        array.add(0);
        array.set(0, 3);
        assertEquals(array.get(0), 3, "Валидность строк: провалено");
        assertEquals(array.size(), 1, "Размер массива 1: провалено");
    }

    @Test
    @DisplayName("Метод set: Проверка присвоения null")
    void setNull() {
        array.add(0);
        array.set(0, null);
        assertNull(array.get(0), "Валидность строк: провалено");
        assertEquals(array.size(), 1, "Размер массива 1: провалено");
    }


    @Test
    @DisplayName("Метод Remove: Проверка удаления по Id первого, среднего и последнего элемента")
    void removeById() {
        array.add(1);
        array.add(2);
        array.add(3);
        array.add(4);
        array.add(5);


        array.remove(0, ID);
        array.remove(1, ID);
        array.remove(2, ID);
        assertEquals(array.size(), 2, "Размер после удаления должен быть 2: провалено");
        assertTrue(array.get(0) == 2 && array.get(1) == 4, "Проверка корректности значений после удаления: провалено");
    }


    @Test
    @DisplayName("Метод Remove: Проверка удаления по Value первого, среднего и последнего элемента")
    void removeBString() {
        array.add(1);
        array.add(2);
        array.add(3);
        array.add(4);
        array.add(5);

        array.remove(1, VALUE);
        array.remove(3, VALUE);
        array.remove(5, VALUE);

        assertEquals(array.size(), 2, "Размер после удаления должен быть 2: провалено");
        assertTrue(array.get(0) == 2 && array.get(1) == 4, "Проверка корректности значений после удаления: провалено");
    }

    @Test
    @DisplayName("Метод Remove: Проверка на содержание значения в массиве")
    void contains() {
        array.add(1);
        array.add(2);
        array.add(3);
        array.add(4);
        array.add(5);

        assertTrue(array.contains(5), "Поиск последнего элемента: провалено");
        assertTrue(array.contains(1), "Поиск первого элемента: провалено");
    }

    @Test
    @DisplayName("Метод indexOf - первое вхождение")
    void indexOf() {
        array.add(1);
        array.add(1);
        array.add(3);
        array.add(5);
        array.add(5);

        assertEquals(3, array.indexOf(5), "Поиск последнего элемента: провалено");
        assertEquals(0, array.indexOf(1), "Поиск первого элемента: провалено");
    }

    @Test
    @DisplayName("Метод indexOf - первое вхождение (значение не найдено)")
    void indexOfNotFound() {
        array.add(1);
        array.add(1);
        array.add(3);
        array.add(5);
        array.add(5);

        assertEquals(-1, array.indexOf(8));
    }

    @Test
    @DisplayName("Метод indexOf - первое вхождение в пустой базе -1")
    void indexOfEmptySearch() {
        assertEquals(-1, array.indexOf(4));
    }

    @Test
    @DisplayName("Метод lastIndexOf - последнее вхождение")
    void lastIndexOf() {
        array.add(1);
        array.add(1);
        array.add(3);
        array.add(5);
        array.add(5);

        assertEquals(4, array.lastIndexOf(5), "Поиск последнего элемента: провалено");
        assertEquals(1, array.lastIndexOf(1), "Поиск первого элемента: провалено");
    }

    @Test
    @DisplayName("Метод lastIndexOf - последнее вхождение в пустой базе = -1")
    void lastIndexOfEmptySearch() {
        assertEquals(-1, array.lastIndexOf(6));
    }

    @Test
    @DisplayName("Метод get - получение результата")
    void get() {
        array.add(1);
        array.add(2);

        assertEquals(2, array.get(1));
    }

    @Test
    @DisplayName("Метод get - ошибка несуществующего элемента ArrayIntegerListOverflowException")
    void getNull() {
        assertThrows(ArrayIntegerListOverflowException.class, () -> array.get(0));
    }


    @Test
    @DisplayName("Метод equals - сравнение объекта")
    void testEquals() {
        array.add(1);
        ArrayIntegerList arrayExpected = new ArrayIntegerList(10);
        arrayExpected.add(1);
        assertTrue(array.equals(arrayExpected));
    }

    @Test
    @DisplayName("Метод size - сравнение размера")
    void size() {
        array.add(1);
        array.add(1);
        assertEquals(2, array.size());
    }

    @Test
    @DisplayName("Метод isEmpty() - негативный тест: не пустое")
    void NotIsEmpty() {
        array.add(1);
        array.add(1);
        assertFalse(array.isEmpty());
    }

    @Test
    @DisplayName("Метод isEmpty() - пустое")
    void isEmpty() {
        assertTrue(array.isEmpty());
    }

    @Test
    @DisplayName("Метод clear() - корректность очистки")
    void clear() {
        array.add(1);
        array.add(2);
        array.clear();
        assertTrue(array.isEmpty());
    }

    @Test
    @DisplayName("Метод toArray() - в массив")
    void toArray() {
        array.add(1);
        array.add(2);
        Integer[] arrayExpected = new Integer[2];
        arrayExpected[0] = 1;
        arrayExpected[1] = 2;
        assertArrayEquals(arrayExpected, array.toArray());
    }

    @Test
    @DisplayName("Метод sort() - сортировка пузырьками")
    void sortBubble() {
        array.add(2);
        array.add(1);
        array.add(3);
        array.add(6);
        array.add(4);
        array.add(5);
        array.sort(BUBBLE);

        assertArrayEquals(new Integer[]{1, 2, 3, 4, 5, 6}, array.toArray());
    }

    @Test
    @DisplayName("Метод sort() - сортировка выбором")
    void sortSelect() {
        array.add(2);
        array.add(1);
        array.add(3);
        array.add(6);
        array.add(4);
        array.add(5);
        array.sort(SELECT);

        assertArrayEquals(new Integer[]{1, 2, 3, 4, 5, 6}, array.toArray());
    }

    @Test
    @DisplayName("Метод sort() - сортировка вставкой")
    void sortInsert() {
        array.add(2);
        array.add(1);
        array.add(3);
        array.add(6);
        array.add(4);
        array.add(5);
        array.sort(INSERT);

        assertArrayEquals(new Integer[]{1, 2, 3, 4, 5, 6}, array.toArray());
    }


}