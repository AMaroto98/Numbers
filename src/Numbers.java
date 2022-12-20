import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class Numbers {

    // Creo Arrays con los números que tiene que devolver el programa. Las arrays están duplicadas por el tema de las mayusculas
    // y las minúsculas.
    static String[] oneToNineWords = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"};
    static String[] oneToNineWordsMinuscule = {"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
    static String[] tenToThirteenWords = {"Ten", "Eleven", "Twelve", "Thirteen"};
    static String[] tenToThirteenWordsMinuscule = {"ten", "eleven", "twelve", "thirteen"};
    static String[] twentyToNinetyDecimalWords = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seven", "Eighty", "Ninety"};
    static String[] twentyToNinetyDecimalWordsMinuscule = {"", "", "twenty", "thirty", "forty", "fifty", "sixty", "seven", "eighty", "ninety"};
    static String[] hundredToQuintillion = {"", "hundred", "thousand", "million", "billion", "trillion", "quadrillion", "quintillion"};

    public static String say(long number) {

        if (number >= 0 && number <= 19) {

            return zeroToNineTeenWords(number, true);
        }

        if (number >= 20 && number <= 99) {

            return twentyToNinetyWords(number, true);
        }

        if (number >= 100 && number <= 9_000_000_000_000_000_000L) {

            return blocks(number);
        }

        return "";
    }

    public static long words(String word) {
        return getNumber(word);
    }

    /* Creamos la primera función zeroToNineTeenWords. Nos dará las traducciones de los números que introduce el
        usuario, entre 0 y 19. */
    public static String zeroToNineTeenWords(long number, boolean isCapitalLetter) {

        String translate = "";

        if (number == 0) {

            translate = "Zero";
        }

        if (number >= 1 && number <= 9) {

            if (isCapitalLetter) {

                translate = oneToNineWords[(int) number];
            } else {

                translate = oneToNineWordsMinuscule[(int) number];
            }
        }

        if (number >= 10 && number <= 13) {

            int unit = takeUnit(number);

            if (isCapitalLetter) {

                translate = tenToThirteenWords[unit];
            } else {

                translate = tenToThirteenWordsMinuscule[unit];
            }
        }

        if (number >= 14 && number <= 19) {

            int unit = takeUnit(number);

            if (isCapitalLetter) {

                translate = oneToNineWords[(int) unit] + "teen";
            } else {

                translate = oneToNineWordsMinuscule[(int) unit] + "teen";
            }
        }
        return translate;
    }

    // Segunda función con la que se nos darán las traducciones de 20 a 99
    public static String twentyToNinetyWords(long number, boolean isCapitalLetter) {

        int decimal = takeDecimal(number);
        long unit = takeUnit(number);

        // Condición para evitar que salga por ejemplo Ninety-
        if (unit == 0) {

            if (isCapitalLetter) {

                return twentyToNinetyDecimalWords[decimal];
            } else {

                return twentyToNinetyDecimalWordsMinuscule[decimal];
            }
        }
        // Condición para las mayúsculas
        if (isCapitalLetter) {

            return twentyToNinetyDecimalWords[decimal] + "-" + oneToNineWordsMinuscule[(int) unit];
        } else {

            return twentyToNinetyDecimalWordsMinuscule[decimal] + "-" + oneToNineWordsMinuscule[(int) unit];
        }
    }

    // Funciones takeUnit / Decimal / Hundred. Están creadas con el objetivo de extraer el número de la posición que me
    // interesa.
    public static int takeUnit(long number) {

        int lastNumber = (int) number;

        // Convertimos Number a un String

        String stringLastNumber = Integer.toString((int) number);

        // Extraemos el último valor del String

        stringLastNumber = stringLastNumber.substring(stringLastNumber.length() - 1);

        // Convertimos el String a un Int de nuevo.

        lastNumber = Integer.parseInt(stringLastNumber);

        return lastNumber;

    }

    public static int takeHundred(long number) {

        String stringHundred = Integer.toString((int) number);
        int size = stringHundred.length();

        if (size != 3) {

            return 0;
        } else {

            return Integer.parseInt(stringHundred.substring(0, 1));
        }
    }

    public static int takeDecimal(long number) {

        String stringDecimal = Integer.toString((int) number);
        int size = stringDecimal.length();

        if (size == 1) {

            return 0;
            // Saca el decimal en el caso de que el bloque de números tenga 2 valores
        } else if (size == 2) {

            return Integer.parseInt(stringDecimal.substring(0, 1));
            // Saca el decimal en el caso de que el bloque de números tenga 3 valores
        } else {

            return Integer.parseInt(stringDecimal.substring(1, 2));
        }
    }

    public static String blocks(long number) {

        // Primero pasamos number a un String
        String countNumbers = Long.toString(number);

        // Definimos dos variablos, numero de digitos que tiene el String que depende del número que busca.
        // Y definimos el tamaño de los bloques, que son de 3.
        int numberOfDigits = countNumbers.length();
        int size = 3;

        // Crear una array list de indices indeterminados,
        ArrayList<Integer> blocks = new ArrayList<Integer>();

        // Con el FOR ordenamos los bloques de derecha a izquierda. 150 643 --> 643 150
        for (int end = numberOfDigits - 1; end >= 0; end -= size) {

            blocks.add(Integer.parseInt(countNumbers.substring(Math.max(0, end - (size - 1)), end + 1)));
        }

        // Creamos el string que setearemos más adelante, es decir, darle un valor.
        String writtedNumber = "";

        // Se crea bucle con el que podremos dar cualquier valor.
        for (int i = blocks.size() - 1; i >= 0; i--) {

            String suffix = hundredToQuintillion[i + 1];

            // Bloque de 3 entero
            int valueOfNumber = blocks.get(i);

            // Lo pasamos a String para comprobar de cuantos digitos es el bloque (de 3 o menos números) de valores
            // que tenemos en este momento.
            String stringOfNumber = Integer.toString((valueOfNumber));

            int numbersOfDigits = stringOfNumber.length();
            int decimal = takeDecimal(valueOfNumber);
            int unit = takeUnit(valueOfNumber);
            int hundred = takeHundred(valueOfNumber);

            // Creamos booleano para comprobar si es el primer bloque, por ejemplo: 11.000 --> Primer bloque: 11 /
            // Segundo bloque 000.
            boolean isFirstBlock = i == blocks.size() - 1;
            boolean isLastBlock = i == 0;

            if (numbersOfDigits == 1 && unit != 0 && isFirstBlock) {

                writtedNumber += oneToNineWords[unit] + " " + suffix;

            } else if (numbersOfDigits == 2 && isFirstBlock) {

                String decimalPlusUnit = decimal + "" + unit;

                if (Long.parseLong(decimalPlusUnit) >= 10 && Long.parseLong(decimalPlusUnit) <= 19) {

                    writtedNumber += zeroToNineTeenWords(Long.parseLong(decimalPlusUnit), true);

                } else {

                    writtedNumber += twentyToNinetyWords(Long.parseLong(decimalPlusUnit), true);
                }

                if (!isLastBlock && valueOfNumber != 0) {

                    writtedNumber += " " + suffix;
                }

            } else {

                if (hundred != 0) {

                    if (isFirstBlock) {

                        writtedNumber += oneToNineWords[hundred] + " " + "hundred";
                    } else {

                        writtedNumber += " " + oneToNineWordsMinuscule[hundred] + " " + "hundred";
                    }
                }

                if (decimal != 0) {

                    if (hundred != 0 || isLastBlock) {

                        writtedNumber += " " + "and";
                    }

                    String decimalPlusUnit = decimal + "" + unit;

                    if (Long.parseLong(decimalPlusUnit) >= 10 && Long.parseLong(decimalPlusUnit) <= 19) {

                        writtedNumber += " " + zeroToNineTeenWords(Long.parseLong(decimalPlusUnit), false);
                    } else {

                        writtedNumber += " " + twentyToNinetyWords(Long.parseLong(decimalPlusUnit), false);
                    }

                } else if (unit != 0) {

                    writtedNumber += " " + "and" + " " + oneToNineWordsMinuscule[unit];
                }
                // Pintamos el sufijo en caso de que no sea el último bloque
                if (i != 0 && valueOfNumber != 0) {

                    writtedNumber += " " + suffix;
                }
            }
        }
        return writtedNumber;
    }

    // Funciones para Word
    public static String unitInText(String value) {

        if (value.equals("zero")) {
            return "0";
        }

        if (value.equals("one")) {
            return "1";
        }

        if (value.equals("two")) {
            return "2";
        }

        if (value.equals("three")) {
            return "3";
        }

        if (value.equals("four")) {
            return "4";
        }

        if (value.equals("five")) {
            return "5";
        }

        if (value.equals("six")) {
            return "6";
        }

        if (value.equals("seven")) {
            return "7";
        }

        if (value.equals("eight")) {
            return "8";
        }

        if (value.equals("nine")) {
            return "9";
        }
        return "";
    }

    public static String decimalInText(String value) {

        // Nombres únicos o casos especiales

        if (value.equals("ten")) {
            return "10";
        }

        if (value.equals("eleven")) {
            return "11";
        }

        if (value.equals("twelve")) {
            return "12";
        }

        if (value.equals("thirteen")) {
            return "13";
        }

        if (value.equals("eighteen")) {
            return "18";
        }

        // Se queda con lo que no sea el teen para formar el número
        if (value.contains("teen")) {
            String[] unitFromDecimal = value.split("teen");
            return "1" + unitInText(unitFromDecimal[0]);
        }

        // Si el código llega hasta aquí el número está entre 20 y 99
        String[] arrayOfDecimal = value.split("-");
        boolean hasWink = arrayOfDecimal.length > 1;

        // Cuando la array tiene más de un valor significa que es por
        // ejemplo twenty-one, si el booleano fuese falso sería twenty por lo que la array tiene menos de un registro.
        if (arrayOfDecimal[0].equals("twenty")) {
            if (hasWink) {
                return "2" + unitInText(arrayOfDecimal[1]);
            }
            return "20";
        }

        if (arrayOfDecimal[0].equals("thirty")) {
            if (hasWink) {
                return "3" + unitInText(arrayOfDecimal[1]);
            }
            return "30";
        }

        if (arrayOfDecimal[0].equals("forty")) {
            if (hasWink) {
                return "4" + unitInText(arrayOfDecimal[1]);
            }
            return "40";
        }

        if (arrayOfDecimal[0].equals("fifty")) {
            if (hasWink) {
                return "5" + unitInText(arrayOfDecimal[1]);
            }

            return "50";
        }

        if (arrayOfDecimal[0].equals("sixty")) {
            if (hasWink) {
                return "6" + unitInText(arrayOfDecimal[1]);
            }

            return "60";
        }

        if (arrayOfDecimal[0].equals("seventy")) {
            if (hasWink) {
                return "7" + unitInText(arrayOfDecimal[1]);
            }

            return "70";
        }

        if (arrayOfDecimal[0].equals("eighty")) {
            if (hasWink) {
                return "8" + unitInText(arrayOfDecimal[1]);
            }

            return "80";
        }

        if (arrayOfDecimal[0].equals("ninety")) {
            if (hasWink) {
                return "9" + unitInText(arrayOfDecimal[1]);
            }
            return "90";
        }
        return "";
    }

    // Función principal que se encarga de llamar a la funcion de las unidades y los decimales
    public static long getNumber(String word) {

        String wordMinuscule = word.toLowerCase();
        String[] splitedWord = wordMinuscule.split(" ");
        String writtedNumber = "";

        for (int i = 0; i <= splitedWord.length - 1; i++) {

            String value = splitedWord[i];
            boolean isDecimal = value.contains("teen") || value.contains("-") || value.equals("ten") || value.equals("eleven") || value.equals("twelve") || value.contains("ty");

            // Si i es mayor a uno usar i - 1 y si es menor sólo i
            String previousValue = i > 0 ? splitedWord[i - 1] : "";
            String doublePrevious = i > 1 ? splitedWord[i - 2] : "";

            if (isDecimal) {

                if ((i == splitedWord.length - 1 && !doublePrevious.equals("hundred")) || (i != splitedWord.length - 1 && !previousValue.equals("and"))) {

                    writtedNumber += "0";
                }

                writtedNumber += decimalInText(value);

            } else {

                if ((previousValue.equals("and") && !doublePrevious.equals("hundred")) || previousValue.equals("quintillion")) {

                    writtedNumber += "00";

                } else if (doublePrevious.equals("hundred")) {

                    writtedNumber += "0";
                }

                writtedNumber += unitInText(value);
            }

            if (value.equals("hundred")) {

                if (i == splitedWord.length - 1) {

                    writtedNumber += "00";
                }
            }

            if (value.equals("thousand")) {

                if (i == splitedWord.length - 1) {

                    writtedNumber += "000";
                }
            }

            if (value.equals("million")) {

                if (i == splitedWord.length - 1) {

                    writtedNumber += "000000";

                } else {
                    String nextValue = splitedWord[i + 1];
                    if (nextValue.equals("and")) {
                        writtedNumber += "000";
                    }
                }
            }

            if (value.equals("billion")) {

                if (i == splitedWord.length - 1) {

                    writtedNumber += "000000000";

                } else {
                    String nextValue = splitedWord[i + 1];
                    if (nextValue.equals("and")) {
                        writtedNumber += "000000";
                    }
                }
            }

            if (value.equals("trillion")) {

                if (i == splitedWord.length - 1) {

                    writtedNumber += "000000000000";

                } else {
                    String nextValue = splitedWord[i + 1];
                    if (nextValue.equals("and")) {
                        writtedNumber += "000000000";
                    }
                }
            }

            if (value.equals("quadrillion")) {

                if (i == splitedWord.length - 1) {
                    writtedNumber += "000000000000000";

                } else {
                    String nextValue = splitedWord[i + 1];
                    if (nextValue.equals("and")) {
                        writtedNumber += "000000000000";
                    }
                }
            }

            if (value.equals("quintillion")) {

                if (i == splitedWord.length - 1) {

                    writtedNumber += "000000000000000000";

                } else {
                    String nextValue = splitedWord[i + 1];
                    if (nextValue.equals("and")) {
                        writtedNumber += "000000000000000";
                    }
                }
            }
        }
        return Long.parseLong(writtedNumber);
    }
}