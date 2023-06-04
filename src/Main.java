import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        String data = sc.nextLine();
        sc.close();
        String finalResult;

        boolean dataContains = data.contains("+") || data.contains("-") || data.contains("*") || data.contains("/");
        if (!dataContains) {
            throw new IOException("throws Exception //т.к. строка не является математической операцией");
        }

        int a = data.length() - data.replace("+", "").length();
        int b = data.length() - data.replace("-", "").length();
        int c = data.length() - data.replace("*", "").length();
        int d = data.length() - data.replace("/", "").length();
        int operatorQuantity = a + b + c + d;  // количество операторов в строке с данными

        if (operatorQuantity != 1) {
            throw new IOException("throws Exception //т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }
        finalResult = calc(data);

        System.out.print(finalResult);
    }

    public static String calc(String input) throws IOException {
        String num1 = null;
        char operator = 0;
        String num2 = null;
        String result;

        for (int i = 0; i < input.length(); i++) {
            if ("+-*/".contains(input.substring(i, i + 1))) {
                num1 = input.substring(0, i).trim();  // необработанный операнд1
                operator = input.charAt(i);  // оператор
                num2 = input.substring(i + 1).trim();  // необработанный операнд2
                break;
            }
        }

        assert num1 != null;
        if (num1.equals("") || num2.equals("")) {
            throw new IOException("throws Exception //т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }

        if ((!"12345678910".contains(num1) && !"IIIVIIIX".contains(num1)) || (!"12345678910".contains(num2) && !"IIIVIIIX".contains(num2))) {
            throw new IOException("throws Exception //т.к. формат математической операции не удовлетворяет заданию - два операнда (от 1 до 10) и один оператор (+, -, /, *)");
        }

        if ("12345678910".contains(num1) && "12345678910".contains(num2)) {
            result = calcArabic(num1, num2, operator);
        } else if ("IIIVIIIX".contains(num1) && "IIIVIIIX".contains(num2)) {
            result = calcRoman(num1, num2, operator);
        } else {
            throw new IOException("throws Exception //т.к. используются одновременно разные системы счисления");
        }

        return result;
    }

    private static String calcArabic(String num1, String num2, char operator) {
        String resultA = null;
        switch (operator) {
            case '+' -> resultA = Integer.toString(Integer.parseInt(num1) + Integer.parseInt(num2));
            case '-' -> resultA = Integer.toString(Integer.parseInt(num1) - Integer.parseInt(num2));
            case '*' -> resultA = Integer.toString(Integer.parseInt(num1) * Integer.parseInt(num2));
            case '/' -> resultA = Integer.toString(Integer.parseInt(num1) / Integer.parseInt(num2));
        }
        return resultA;
    }

    private static String calcRoman(String num1, String num2, char operator) throws IOException {
        String resultR;
        HashMap<String, Integer> numRomanTransfer = new HashMap<>();
        numRomanTransfer.put("I", 1);
        numRomanTransfer.put("II", 2);
        numRomanTransfer.put("III", 3);
        numRomanTransfer.put("IV", 4);
        numRomanTransfer.put("V", 5);
        numRomanTransfer.put("VI", 6);
        numRomanTransfer.put("VII", 7);
        numRomanTransfer.put("VIII", 8);
        numRomanTransfer.put("IX", 9);
        numRomanTransfer.put("X", 10);

        int result = 0;
        switch (operator) {
            case '+' -> result = numRomanTransfer.get(num1) + numRomanTransfer.get(num2);
            case '-' -> result = numRomanTransfer.get(num1) - numRomanTransfer.get(num2);
            case '*' -> result = numRomanTransfer.get(num1) * numRomanTransfer.get(num2);
            case '/' -> result = numRomanTransfer.get(num1) / numRomanTransfer.get(num2);
        }

        if (result > 1) {
            HashMap<Integer, String> numArabicTransfer = new HashMap<>();
            numArabicTransfer.put(1, "I");
            numArabicTransfer.put(2, "II");
            numArabicTransfer.put(3, "III");
            numArabicTransfer.put(4, "IV");
            numArabicTransfer.put(5, "V");
            numArabicTransfer.put(6, "VI");
            numArabicTransfer.put(7, "VII");
            numArabicTransfer.put(8, "VIII");
            numArabicTransfer.put(9, "IX");
            numArabicTransfer.put(10, "X");
            numArabicTransfer.put(20, "XX");
            numArabicTransfer.put(30, "XXX");
            numArabicTransfer.put(40, "XL");
            numArabicTransfer.put(50, "L");
            numArabicTransfer.put(60, "LX");
            numArabicTransfer.put(70, "LXX");
            numArabicTransfer.put(80, "LXXX");
            numArabicTransfer.put(90, "XC");
            numArabicTransfer.put(100, "C");

            int tens = result / 10;
            int units = result % 10;

            if (numArabicTransfer.containsKey(result)) {
                resultR = numArabicTransfer.get(result);
            } else {
                resultR = numArabicTransfer.get(tens * 10) + numArabicTransfer.get(units);
            }
        } else {
            throw new IOException("throws Exception //т.к. в римской системе нет отрицательных чисел");
        }
        return resultR;
    }
}