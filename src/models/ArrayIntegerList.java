package models;

import exceptions.ArrayIntegerListNotFoundException;
import exceptions.ArrayIntegerListOverflowException;
import interfaces.IntegerList;

import java.util.Arrays;
import java.util.Objects;

public class ArrayIntegerList implements IntegerList {

    private Integer[] array;
    private int size;
    private int maxSize;

    public ArrayIntegerList(int size) {
        if (size <= 0 || size > MAX_INTEGER_LIST_SIZE) {
            throw new ArrayIntegerListOverflowException();
        }
        this.array = new Integer[size];
        this.size = 0;
        this.maxSize = size;
    }

    public ArrayIntegerList(IntegerList arrayCopied) {
        this.size = arrayCopied.size();
        this.maxSize = arrayCopied.getMaxSize();
        this.array= arrayCopied.toArray();
    }

    private void grow() {
        if (size() == MAX_INTEGER_LIST_SIZE) {
            throw new ArrayIntegerListOverflowException();
        }
        int newSize = (int) (array.length * 1.5);
        if (newSize > MAX_INTEGER_LIST_SIZE) newSize = MAX_INTEGER_LIST_SIZE;
        this.maxSize = newSize;
        this.array = Arrays.copyOf(array, newSize);
    }


    @Override
    public int getMaxSize() {
        return maxSize;
    }

    @Override
    public void sort(SORT sortMode) {
        switch (sortMode) {
            case BUBBLE -> sortBubble();
            case SELECT -> sortSelection();
            case INSERT -> sortInsertion();
            case QUICK -> sortQuick(0,size() -1 );
        }
    }

    public void sortQuick(int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(begin, end);

            sortQuick(begin, partitionIndex - 1);
            sortQuick(partitionIndex + 1, end);
        }
    }

    private int partition(int begin, int end) {
        int pivot = array[end];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (array[j] <= pivot) {
                i++;

                swapElements(i, j);
            }
        }

        swapElements(i + 1, end);
        return i + 1;
    }

    private void swapElements(int left, int right) {
        int temp = array[left];
        array[left] = array[right];
        array[right] = temp;
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
            grow();
        }
        return array[size++] = item;
    }

    @Override
    public Integer add(int index, Integer item) {
        if (index > size || size >= maxSize) {
            grow();
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
