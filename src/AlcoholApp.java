import interfaces.IntegerList;
import models.ArrayIntegerList;

import java.util.Random;

public class AlcoholApp {
    public static void main(String[] args) {

        ArrayIntegerList array = new ArrayIntegerList(1000);

        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            array.add(random.nextInt(300));
        }


        System.out.println("Пузырьковая сортировка");
        long start = System.currentTimeMillis();
        array.sort(IntegerList.SORT.BUBBLE);
        System.out.println(System.currentTimeMillis() - start);

        System.out.println("Сортировка выбором");
        start = System.currentTimeMillis();
        array.sort(IntegerList.SORT.SELECT);
        System.out.println(System.currentTimeMillis() - start);

        System.out.println("Сортировка вставкой");
        start = System.currentTimeMillis();
        array.sort(IntegerList.SORT.INSERT);
        System.out.println(System.currentTimeMillis() - start);

    }
}
