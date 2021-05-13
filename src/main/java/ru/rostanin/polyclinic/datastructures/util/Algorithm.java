package ru.rostanin.polyclinic.datastructures.util;

public class Algorithm {

    public static boolean naiveSearch(String pattern, String text) {
        int M = pattern.length();
        int N = text.length();

        /* A loop to slide pattern one by one */
        for (int i = 0; i <= N - M; i++) {

            int j;

            /* For current index i, check for pattern match */
            for (j = 0; j < M; j++)
                if (Character.toLowerCase(text.charAt(i + j)) != Character.toLowerCase(pattern.charAt(j)))
                    break;

            if (j == M) // if pat[0...M-1] = txt[i, i+1, ...i+M-1]
                return true;
        }
        return false;
    }
}
