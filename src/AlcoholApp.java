import interfaces.IntegerList;
import models.ArrayIntegerList;

import java.util.Arrays;
import java.util.Random;

public class AlcoholApp {
    public static void main(String[] args) {

        ArrayIntegerList array = new ArrayIntegerList(1000);

        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            array.add(random.nextInt(300));
        }

        ArrayIntegerList array1 = new ArrayIntegerList(array);
        ArrayIntegerList array2 = new ArrayIntegerList(array);
        ArrayIntegerList array3 = new ArrayIntegerList(array);

        System.out.println("Пузырьковая сортировка");
        long start = System.currentTimeMillis();
        array.sort(IntegerList.SORT.BUBBLE);
        System.out.println(System.currentTimeMillis() - start);
        System.out.println(Arrays.toString(array.toArray()));

        System.out.println("Сортировка выбором");
        start = System.currentTimeMillis();
        array1.sort(IntegerList.SORT.SELECT);
        System.out.println(System.currentTimeMillis() - start);
        System.out.println(Arrays.toString(array1.toArray()));

        System.out.println("Сортировка вставкой");
        start = System.currentTimeMillis();
        array2.sort(IntegerList.SORT.INSERT);
        System.out.println(System.currentTimeMillis() - start);
        System.out.println(Arrays.toString(array2.toArray()));

        System.out.println("Быстрая сортировка рекурсией");
        start = System.currentTimeMillis();
        array3.sort(IntegerList.SORT.QUICK);
        System.out.println(System.currentTimeMillis() - start);
        System.out.println(Arrays.toString(array3.toArray()));
    }
}
