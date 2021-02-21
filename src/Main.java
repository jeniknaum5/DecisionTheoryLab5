import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Кол-во колонок: "); //в моем варианте 3
        int numberOfCols = sc.nextInt();

        System.out.print("Кол-во кандидатов: "); //в моем варианте 4 человека (a,b,c,d)
        int numberOfVars = sc.nextInt();

        ArrayList<Integer> quantityOfVotes = new ArrayList<>(); // в варианте 3,4,4

        for (int i = 0; i < numberOfCols; i++) {
            System.out.print("Введите кол-во голосов для " + (i + 1) + " колонки: ");
            int number = sc.nextInt();
            quantityOfVotes.add(number);
        }
        String[][] ratingCols = new String[numberOfVars][numberOfCols];
        // Вариант: 1:d,a,c,b
        //          2:a,b,c,d
        //          3:b,c,d,a

        Scanner newScanner = new Scanner(System.in);
        for (int i = 0; i < numberOfCols; i++) {
            for (int j = 0; j < numberOfVars; j++) {
                System.out.print("Введите " + (j + 1) + " кандидата для " + (i + 1) + " колонки:");
                String symbol = newScanner.nextLine();
                ratingCols[j][i] = symbol;
            }
        }

        print(quantityOfVotes, ratingCols, numberOfVars, numberOfCols);

        System.out.println("_______________________________________________");
        System.out.println("|Метод относительной большести|");
        relativeMajorityMethod(quantityOfVotes, ratingCols, numberOfCols, numberOfVars);
    }


    public static void print(ArrayList<Integer> arrayList, String[][] ratingCols, int numberOfVars, int numberOfCols) {
        for (int i : arrayList) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println("_ _ _");
        for (int i = 0; i < numberOfVars; i++) {
            for (int j = 0; j < numberOfCols; j++) {
                System.out.print(ratingCols[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void relativeMajorityMethod(ArrayList<Integer> quantityOfVotes, String[][] ratingCols,
                                              int numberOfCols, int numberOfVars) {

        HashMap<String, Integer> buffer = new HashMap<>();

        for (int i = 0; i < numberOfCols; i++) {

            if (buffer.containsKey(ratingCols[0][i]))
                buffer.put(ratingCols[0][i], buffer.get(ratingCols[0][i]) + quantityOfVotes.get(i));
            else
                buffer.put(ratingCols[0][i], quantityOfVotes.get(i));

        }

        for (String key : buffer.keySet())
            System.out.println(key + " :: " + buffer.get(key) + " votes");

        Integer maxValue = Collections.max(buffer.values());
        int count = 0;
        String key = null;
        for (Map.Entry entry : buffer.entrySet()) {
            if (maxValue.equals(entry.getValue())) {
                key = (String) entry.getKey();
                count++;
                //System.out.println(key + " :: " + maxValue);
            }
        }
        if (count > 1)
            System.out.println("Кол-во кандидатов с наибольшим кол-вом голосов >1\n" +
                    "поэтому победителя нет");
        else
            System.out.println("Победитель кандидат: |" + key + "| с таким количеством голосов: " + maxValue);
    }


}
