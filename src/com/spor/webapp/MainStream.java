package com.spor.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStream {
    public static void main(String[] args) {
        int[] values = {2, 8, 3, 4, 2, 8, 3, 6, 1, 9, 2, 4, 3, 1};
        System.out.println(Arrays.toString(values));
        System.out.println(minValue(values));

        List<Integer> list1 = Arrays.asList(2, 3, 4, 2, 8, 3, 6, 1, 9, 2, 3, 1);
        List<Integer> list2 = Arrays.asList(2, 3, 4, 2, 8, 3, 6, 1, 9, 2, 3);
        System.out.println(list1);
        System.out.println(oddOrEven(list1));
        System.out.println(list2);
        System.out.println(oddOrEven(list2));

    }

    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (acc, a) -> acc * 10 + a);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int rem = integers.stream().reduce(0, Integer::sum) % 2;
        return integers.stream().filter(a -> a % 2 != rem).collect(Collectors.toList());
    }
}
