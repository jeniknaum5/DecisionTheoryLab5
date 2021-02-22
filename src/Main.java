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
        System.out.println("|Метод относительного большинства|");
        relativeMajorityMethod(quantityOfVotes, ratingCols, numberOfCols);
        System.out.println("\n_______________________________________________");
        System.out.println("|Метод альтернативных голосов|");
        alternativeVotesMethod(quantityOfVotes, ratingCols, numberOfVars, numberOfCols);
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
                                              int numberOfCols) {

        HashMap<String, Integer> buffer = new HashMap<>();

        for (int i = 0; i < numberOfCols; i++) {//заполняем мапу

            if (buffer.containsKey(ratingCols[0][i]))
                buffer.put(ratingCols[0][i], buffer.get(ratingCols[0][i]) + quantityOfVotes.get(i));
            else
                buffer.put(ratingCols[0][i], quantityOfVotes.get(i));

        }

        for (String key : buffer.keySet())//вывод мапы кандидат :: кол-во голосов
            System.out.println(key + " :: " + buffer.get(key) + " votes");

        Integer maxValue = Collections.max(buffer.values());
        int count = 0;
        String key = null;
        for (Map.Entry entry : buffer.entrySet()) {//находим кандидата с наимбольшим кол-вом голосов
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

    public static void alternativeVotesMethod(ArrayList<Integer> quantityOfVotes, String[][] ratingCols, int numberOfVars,
                                              int numberOfCols) {


        String[][] tempArray = new String[numberOfVars][numberOfCols];

        int tempNumberOfVars = numberOfVars;
        int tempNumberOfCols = numberOfCols;

        for (int i = 0; i < numberOfVars; i++) {//заполняем массив данными из условия
            for (int j = 0; j < numberOfCols; j++)
                tempArray[i][j] = ratingCols[i][j];
        }

        String keys = null;
        for (int iter = 0; iter < numberOfVars - 1; iter++) {
            HashMap<String, Integer> buffer = new HashMap<>();
            System.out.println("\n|Тур №"+ (iter+1) +"|");
            print(quantityOfVotes, tempArray, tempNumberOfVars, tempNumberOfCols);

            for (int j = 0; j < tempNumberOfVars; j++) { //заполняем мапу кандидатами c нулевыми голосами
                for (int i = 0; i < tempNumberOfCols; i++) {
                    buffer.put(tempArray[j][i], 0);
                }
            }

            for (int i = 0; i < tempNumberOfCols; i++) {//заполняем мапу по первому ряду матрицы

                if (buffer.containsKey(ratingCols[0][i]))
                    buffer.put(tempArray[0][i], buffer.get(tempArray[0][i]) + quantityOfVotes.get(i));
                else
                    buffer.put(tempArray[0][i], quantityOfVotes.get(i));

            }

            // for (String key : buffer.keySet()) //выводим мапу кандидат :: кол-во голосов
            //    System.out.println(key + " :: " + buffer.get(key) + " votes");

            Integer minValue = Collections.min(buffer.values());
            for (Map.Entry entry : buffer.entrySet()) {//ищем в мапе кандидата с наименьшим кол-вом голосов
                if (minValue.equals(entry.getValue())) {
                    keys = (String) entry.getKey();
                    System.out.println("Меньше всех голосов набрал кандидат: |" + keys + "| (" + minValue + " голосов)" +
                            "\n\tКандидат |" + keys + "| исключен!");

                }
            }

            String delete = keys;
            String temp = null;
            for (int i = 0; i < tempNumberOfVars - 1; i++) { //смещаем кандидата с наименьшим кол-во голосов вниз
                for (int j = 0; j < tempNumberOfCols; j++) {
                    if (tempArray[i][j].equals(delete)) {
                        temp = tempArray[i][j];
                        tempArray[i][j] = tempArray[i + 1][j];
                        tempArray[i + 1][j] = temp;
                    }
                }
            }
            //print(quantityOfVotes, tempArray, tempNumberOfVars, tempNumberOfCols);
            for (int j = 0; j < tempNumberOfCols; j++)
                tempArray[tempNumberOfVars - 1][j] = null;
            tempNumberOfVars--;
            buffer.clear();

        }
        System.out.println("\n\tПобедитель: |" + tempArray[0][0] + "!!!");
    }
}
