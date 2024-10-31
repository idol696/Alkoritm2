package models;

import exceptions.ArrayIntegerListNotFoundException;
import exceptions.ArrayIntegerListOverflowException;
import interfaces.IntegerList;

import java.util.Objects;

public class ArrayIntegerList implements IntegerList {

    private final Integer[] array;
    private int size;
    private final int maxSize;

    public ArrayIntegerList(int size) {
        if (size <= 0 || size > MAX_STRING_LIST_SIZE) {
            throw new ArrayIntegerListOverflowException();
        }
        this.array = new Integer[size];
        this.size = 0;
        this.maxSize = size;
    }

    @Override
    public void sort(SORT sortMode) {
        switch(sortMode) {
            case BUBBLE -> sortBubble();
            case SELECT -> sortSelection();
            case INSERT -> sortInsertion();
        }
    }

    private void sortBubble() {
        for (int i = 0; i < size() - 1; i++) {
            for (int j = 0; j < size() - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    private void sortSelection() {
        for (int i = 0; i < size() - 1; i++) {
            int minElementIndex = i;
            for (int j = i + 1; j < size(); j++) {
                if (array[j] < array[minElementIndex]) {
                    minElementIndex = j;
                }
            }
            if (minElementIndex != i) {
                int temp = array[i];
                array[i] = array[minElementIndex];
                array[minElementIndex] = temp;
            }
        }
    }

    private void sortInsertion() {
        for (int i = 1; i < size(); i++) {
            int temp = array[i];
            int j = i;
            while (j > 0 && array[j - 1] >= temp) {
                array[j] = array[j - 1];
                j--;
            }
            array[j] = temp;
        }
    }

    @Override
    public Integer add(Integer item) {
        if (size >= maxSize) {
            throw new ArrayIntegerListOverflowException();
        }
        return array[size++] = item;
    }

    @Override
    public Integer add(int index, Integer item) {
        if (index > size || size >= maxSize) {
            throw new ArrayIntegerListOverflowException();
        }

        for (int i = size; i > index; i--) {
            array[i] = array[i - 1];
        }
        size++;
        return array[index] = item;
    }

    @Override
    public Integer set(int index, Integer item) {
        if (index >= size) {
            throw new ArrayIntegerListOverflowException();
        }
        return array[index] = item;
    }

    @Override
    public Integer remove(Integer item, IntegerList.DELETE_MODE deleteMode) {
        Integer result = -1;
        switch (deleteMode) {
            case ID -> result = removeById(item);
            case VALUE -> result = removeByValue(item);
        }
        return result;
    }

    private Integer removeByValue(Integer item) {
        boolean flag = false;
        Integer removeResult = null;
        int count = size;
        while (count > 0) {
            if (Objects.equals(item, array[--count])) {
                removeResult = removeById(count--);
                flag = true;
            }
        }
        if (!flag) {
            throw new ArrayIntegerListNotFoundException();
        }
        return removeResult;
    }

    private Integer removeById(int index) {
        if (size == 0) {
            throw new ArrayIntegerListNotFoundException();
        }
        if (index >= size) {
            throw new ArrayIntegerListOverflowException();
        }
        Integer removeResult = array[index];
        for (int i = index; i < size; i++) {
            array[i] = array[i + 1];
        }
        size--;
        return removeResult;
    }

    @Override
    public boolean contains(Integer item) {
        for (Integer elem : array) {
            if (Objects.equals(elem, item)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int indexOf(Integer item) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(array[i], item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Integer item) {
        int result = -1;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(array[i], item)) {
                result = i;
            }
        }
        return result;
    }

    @Override
    public Integer get(int index) {
        if (index >= size) {
            throw new ArrayIntegerListOverflowException();
        }
        return array[index];
    }

    @Override
    public boolean equals(IntegerList otherList) {
        if (otherList.size() == size) {
            for (int i = 0; i < size; i++) {
                if (!Objects.equals(array[i], otherList.get(i))) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        size = 0;
    }

    @Override
    public Integer[] toArray() {
        Integer[] resultArray = new Integer[size];
        for (int i = 0; i < size; i++) {
            resultArray[i] = array[i];
        }
        return resultArray;
    }
}
