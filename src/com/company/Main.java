package com.company;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    private static BufferedReader br;
    private static int batteries;
    private static boolean evenSerial;
    private static boolean vowel;
    private static boolean parallel;
    private static boolean frk;
    private static boolean car;
    private static ArrayList<Integer> memClicked;
    private static ArrayList<Integer> memValues;
    private static TreeMap<String, Integer> morseMap;
    private static ArrayList<String> morsePotentials;
    private static int[] wireSeq;
    private static String[][] wireMap;
    private static ArrayList<String[][]> mazes;
    private static Queue<Coordinate> mazeQueue;
    private static ArrayList<String> passwordPotentials;
    private static ArrayList<String> letterCombos;

    public static void main(String[] args) {
        br = new BufferedReader(new InputStreamReader(System.in));
        batteries = 0;
        evenSerial = false;
        vowel = false;
        parallel = false;
        frk = false;
        car = false;
        memClicked = new ArrayList<>();
        memValues = new ArrayList<>();
        morseMap = new TreeMap<>();
        morsePotentials = new ArrayList<>();
        mazes = new ArrayList<>();
        passwordPotentials = new ArrayList<>();
        letterCombos = new ArrayList<>();
        setupWireSeq();
        setupMorse();
        getBombSettings();
        setupMazes();
        setupPasswords();
        startDefusing();
    }


    private static void getBombSettings() {
        try {
            System.out.println("Input Bomb Settings:");
            String settings = br.readLine();
            batteries = Integer.parseInt(settings.split(" ")[0]);
            evenSerial = settings.contains("e");
            vowel = settings.contains("v");
            parallel = settings.contains("p");
            frk = settings.contains("f");
            car = settings.contains("c");
            System.out.println("Batteries: " + batteries);
            System.out.println("Serial even: " + evenSerial);
            System.out.println("Serial vowel: " + vowel);
            System.out.println("Parallel port: " + parallel);
            System.out.println("FRK: " + frk);
            System.out.println("CAR: " + car);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void startDefusing() {
        try {
            while (true) {
                System.out.println("Next Command:");
                String line = br.readLine();
                String[] lineSplit = line.split(" ");
                String puzzle = lineSplit[0];
                ArrayList<String> cmd = new ArrayList<>(Arrays.asList(lineSplit));
                cmd.remove(0);
                switch (puzzle) {
                    case "exit":
                        return;
                    case "reset":
                        resetBomb();
                        break;
                    case "simple":
                        simpleWires(cmd);
                        break;
                    case "button":
                        button(cmd);
                        break;
                    case "keys":
                        keypads(cmd);
                        break;
                    case "simon":
                        simon();
                        break;
                    case "who":
                        who(cmd);
                        break;
                    case "mem":
                        memory(cmd);
                        break;
                    case "resetmem":
                        resetMem();
                        break;
                    case "morse":
                        morse(cmd);
                        break;
                    case "morsereset":
                        resetMorse();
                        break;
                    case "comp":
                        complicated(cmd);
                        break;
                    case "seq":
                        wireSeq(cmd);
                        break;
                    case "resetseq":
                        resetWireSeq();
                        break;
                    case "maze":
                        maze(cmd);
                        break;
                    case "pw":
                        pw(cmd);
                        break;
                    case "resetpw":
                        resetPw();
                        break;
                    case "dial":
                        dial(cmd);
                        break;
                    default:
                        System.out.println("Invalid Input");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void resetBomb() {
        batteries = 0;
        evenSerial = false;
        vowel = false;
        parallel = false;
        frk = false;
        car = false;
        resetMem();
        resetMorse();
        resetWireSeq();
        resetPw();
        getBombSettings();
    }

    private static void simpleWires(ArrayList<String> cmd) throws Exception {
        switch (cmd.size()) {
            case 3:
                if (!cmd.contains("r")) {
                    System.out.println("Cut Second.");
                } else if (cmd.get(cmd.size()-1).equals("w")){
                    System.out.println("Cut Last.");
                } else if (Collections.frequency(cmd, "bl") > 1) {
                    System.out.println("Cut Last Blue.");
                } else {
                    System.out.println("Cut Last.");
                }
                break;
            case 4:
                if (!evenSerial && Collections.frequency(cmd, "r") > 1) {
                    System.out.println("Cut Last Red.");
                } else if (cmd.get(cmd.size()-1).equals("y") && !cmd.contains("r")) {
                    System.out.println("Cut First.");
                } else if (Collections.frequency(cmd, "bl") == 1) {
                    System.out.println("Cut First.");
                } else if (Collections.frequency(cmd, "y") > 1) {
                    System.out.println("Cut Last.");
                } else {
                    System.out.println("Cut Second.");
                }
                break;
            case 5:
                if (!evenSerial && cmd.get(cmd.size()-1).equals("b")) {
                    System.out.println("Cut Fourth.");
                } else if (Collections.frequency(cmd, "r") == 1 &&
                        Collections.frequency(cmd, "y") > 1) {
                    System.out.println("Cut First.");
                } else if (!cmd.contains("b")) {
                    System.out.println("Cut Second.");
                } else {
                    System.out.println("Cut First");
                }
                break;
            case 6:
                if (!evenSerial && !cmd.contains("y")) {
                    System.out.println("Cut Third.");
                } else if (Collections.frequency(cmd, "y") == 1 &&
                        Collections.frequency(cmd, "w") > 1) {
                    System.out.println("Cut Fourth.");
                } else if (!cmd.contains("r")) {
                    System.out.println("Cut Last.");
                } else {
                    System.out.println("Cut Fourth.");
                }
                break;
        }
    }

    private static void button(ArrayList<String> cmd) {
        if (cmd.get(0).equals("bl") && cmd.get(1).equals("abort")) {
            System.out.println("HOLD. Blue: 4. Yellow: 5. Else 1.");
        } else if (batteries > 1 && cmd.get(1).equals("detonate")) {
            System.out.println("Quick Press.");
        } else if (cmd.get(0).equals("w") && car) {
            System.out.println("HOLD. Blue: 4. Yellow: 5. Else 1.");
        } else if (batteries > 2 && frk) {
            System.out.println("Quick Press.");
        } else if (cmd.get(0).equals("y")) {
            System.out.println("HOLD. Blue: 4. Yellow: 5. Else 1.");
        } else if (cmd.get(0).equals("r") && cmd.get(1).equals("Hold")) {
            System.out.println("Quick Press.");
        } else {
            System.out.println("HOLD. Blue: 4. Yellow: 5. Else 1.");
        }
    }

    private static void keypads(ArrayList<String> cmd) {
        String[][] keys = { {"oleg", "at", "lambda", "lightning", "alien", "h", "backwardsc"},
                {"backwardse", "oleg", "backwardsc", "a", "whitestar", "h", "?"},
                {"copyright", "w", "a", "kk", "r", "lambda", "whitestar"},
                {"6", "p", "bt", "alien", "kk", "?", "face"},
                {"psi", "face", "bt", "cdot", "p", "3", "star"},
                {"6", "backwardse", "lines", "ae", "psi", "n", "o"}};
        for (String[] s : keys) {
            List<String> list = Arrays.asList(s);
            if (list.contains(cmd.get(0)) && list.contains(cmd.get(1)) &&
                    list.contains(cmd.get(2)) && list.contains(cmd.get(3))) {
                TreeMap<Integer, String> map = new TreeMap<>();
                map.put(list.lastIndexOf(cmd.get(0)), cmd.get(0));
                map.put(list.lastIndexOf(cmd.get(1)), cmd.get(1));
                map.put(list.lastIndexOf(cmd.get(2)), cmd.get(2));
                map.put(list.lastIndexOf(cmd.get(3)), cmd.get(3));
                for (String symbol : map.values()) {
                    System.out.println(symbol);
                }
                return;
            }
        }
        System.out.println("Invalid symbols.");
    }

    private static void simon() {
        if (vowel) {
            System.out.println("Red: Blue");
            System.out.println("Blue: Red");
            System.out.println("Green: Yellow");
            System.out.println("Yellow: Green");
        } else {
            System.out.println("Red: Blue");
            System.out.println("Blue: Yellow");
            System.out.println("Green: Green");
            System.out.println("Yellow: Red");
        }
    }

    private static void who(ArrayList<String> cmd) {
        if (cmd.size() < 2) {
            System.out.println("Invalid Input.");
            return;
        }
        if (cmd.get(0).equals("1")) {
            switch (cmd.get(1)) {
                case "yes":
                    System.out.println("Middle Left");
                    break;
                case "first":
                    System.out.println("Top Right");
                    break;
                case "display":
                    System.out.println("Bottom Right");
                    break;
                case "okay":
                    System.out.println("Top Right");
                    break;
                case "says":
                    System.out.println("Bottom Right");
                    break;
                case "nothing":
                    System.out.println("Middle Left");
                    break;
                case "empty":
                    System.out.println("Bottom Left");
                    break;
                case "blank":
                    System.out.println("Middle Right");
                    break;
                case "no":
                    System.out.println("Bottom Right");
                    break;
                case "led":
                    System.out.println("Middle Left");
                    break;
                case "lead":
                    System.out.println("Bottom Right");
                    break;
                case "read":
                    System.out.println("Middle Right");
                    break;
                case "red":
                    System.out.println("Middle Right");
                    break;
                case "reed":
                    System.out.println("Bottom Left");
                    break;
                case "leed":
                    System.out.println("Bottom Left");
                    break;
                case "holdon":
                    System.out.println("Bottom Right");
                    break;
                case "you":
                    System.out.println("Middle Right");
                    break;
                case "youare":
                    System.out.println("Bottom Right");
                    break;
                case "your":
                    System.out.println("Middle Right");
                    break;
                case "you're":
                    System.out.println("Middle Right");
                    break;
                case "ur":
                    System.out.println("Top Left");
                    break;
                case "there":
                    System.out.println("Bottom Right");
                    break;
                case "they're":
                    System.out.println("Bottom Left");
                    break;
                case "their":
                    System.out.println("Middle Right");
                    break;
                case "theyare":
                    System.out.println("Middle Left");
                    break;
                case "see":
                    System.out.println("Bottom Right");
                    break;
                case "c":
                    System.out.println("Top Right");
                    break;
                case "cee":
                    System.out.println("Bottom Right");
                    break;
                default:
                    System.out.println("Invalid Word 1: " + cmd.get(1));
                    break;
            }
        } else {
            switch (cmd.get(1)) {
                case "ready":
                    System.out.println("YES, OKAY, WHAT, MIDDLE, LEFT, PRESS, RIGHT, BLANK, READY");
                    break;
                case "first":
                    System.out.println("LEFT, OKAY, YES, MIDDLE, NO, RIGHT, NOTHING, UHHH, WAIT, READY, BLANK, WHAT, PRESS, FIRST");
                    break;
                case "no":
                    System.out.println("BLANK, UHHH, WAIT, FIRST, WHAT, READY, RIGHT, YES, NOTHING, LEFT, PRESS, OKAY, NO");
                    break;
                case "blank":
                    System.out.println("WAIT, RIGHT, OKAY, MIDDLE, BLANK");
                    break;
                case "nothing":
                    System.out.println("UHHH, RIGHT, OKAY, MIDDLE, YES, BLANK, NO, PRESS, LEFT, WHAT, WAIT, FIRST, NOTHING");
                    break;
                case "yes":
                    System.out.println("OKAY, RIGHT, UHHH, MIDDLE, FIRST, WHAT, PRESS, READY, NOTHING, YES");
                    break;
                case "what":
                    System.out.println("UHHH, WHAT");
                    break;
                case "uhhh":
                    System.out.println("READY, NOTHING, LEFT, WHAT, OKAY, YES, RIGHT, NO, PRESS, BLANK, UHHH");
                    break;
                case "left":
                    System.out.println("RIGHT, LEFT");
                    break;
                case "right":
                    System.out.println("YES, NOTHING, READY, PRESS, NO, WAIT, WHAT, RIGHT");
                    break;
                case "middle":
                    System.out.println("BLANK, READY, OKAY, WHAT, NOTHING, PRESS, NO, WAIT, LEFT, MIDDLE");
                    break;
                case "okay":
                    System.out.println("MIDDLE, NO, FIRST, YES, UHHH, NOTHING, WAIT, OKAY");
                    break;
                case "wait":
                    System.out.println("UHHH, NO, BLANK, OKAY, YES, LEFT, FIRST, PRESS, WHAT, WAIT");
                    break;
                case "press":
                    System.out.println("RIGHT, MIDDLE, YES, READY, PRESS");
                    break;
                case "you":
                    System.out.println("SURE, YOU ARE, YOUR, YOU'RE, NEXT, UH HUH, UR, HOLD, WHAT?, YOU");
                    break;
                case "youare":
                    System.out.println("YOUR, NEXT, LIKE, UH HUH, WHAT?, DONE, UH UH, HOLD, YOU, U, YOU'RE, SURE, UR, YOU ARE");
                    break;
                case "your":
                    System.out.println("UH UH, YOU ARE, UH HUH, YOUR");
                    break;
                case "you're":
                    System.out.println("YOU, YOU'RE");
                    break;
                case "ur":
                    System.out.println("DONE, U, UR");
                    break;
                case "u":
                    System.out.println("UH HUH, SURE, NEXT, WHAT?, YOU'RE, UR, UH UH, DONE, U");
                    break;
                case "uhhuh":
                    System.out.println("UH HUH");
                    break;
                case "uhuh":
                    System.out.println("UR, U, YOU ARE, YOU'RE, NEXT, UH UH");
                    break;
                case "what?":
                    System.out.println("YOU, HOLD, YOU'RE, YOUR, U, DONE, UH UH, LIKE, YOU ARE, UH HUH, UR, NEXT, WHAT?");
                    break;
                case "done":
                    System.out.println("SURE, UH HUH, NEXT, WHAT?, YOUR, UR, YOU'RE, HOLD, LIKE, YOU, U, YOU ARE, UH UH, DONE");
                    break;
                case "next":
                    System.out.println("WHAT?, UH HUH, UH UH, YOUR, HOLD, SURE, NEXT");
                    break;
                case "hold":
                    System.out.println("YOU ARE, U, DONE, UH UH, YOU, UR, SURE, WHAT?, YOU'RE, NEXT, HOLD");
                    break;
                case "sure":
                    System.out.println("YOU ARE, DONE, LIKE, YOU'RE, YOU, HOLD, UH HUH, UR, SURE");
                    break;
                case "like":
                    System.out.println("YOU'RE, NEXT, U, UR, HOLD, DONE, UH UH, WHAT?, UH HUH, YOU, LIKE");
                    break;
                default:
                    System.out.println("Invalid Word 2: " + cmd.get(1));
                    break;
            }
        }
    }

    private static void memory(ArrayList<String> cmd) {
        int phase = memClicked.size() + 1;
        int num = Integer.parseInt(cmd.get(0));
        switch (phase) {
            case 1:
                if (num == 1) {
                    int pos = 2;
                    int result = Integer.parseInt(cmd.get(pos));
                    memClicked.add(pos);
                    memValues.add(result);
                    System.out.println(result);
                } else if (num == 2) {
                    int pos = 2;
                    int result = Integer.parseInt(cmd.get(pos));
                    memClicked.add(pos);
                    memValues.add(result);
                    System.out.println(result);
                } else if (num == 3) {
                    int pos = 3;
                    int result = Integer.parseInt(cmd.get(pos));
                    memClicked.add(pos);
                    memValues.add(result);
                    System.out.println(result);
                } else if (num == 4) {
                    int pos = 4;
                    int result = Integer.parseInt(cmd.get(pos));
                    memClicked.add(pos);
                    memValues.add(result);
                    System.out.println(result);
                } else {
                    System.out.println("Invalid Input");
                }
                break;
            case 2:
                if (num == 1) {
                    int pos = cmd.lastIndexOf("4");
                    int result = 4;
                    memClicked.add(pos);
                    memValues.add(result);
                    System.out.println(result);
                } else if (num == 2) {
                    int pos = memClicked.get(0);
                    int result = Integer.parseInt(cmd.get(pos));
                    memClicked.add(pos);
                    memValues.add(result);
                    System.out.println(result);
                } else if (num == 3) {
                    int pos = 1;
                    int result = Integer.parseInt(cmd.get(pos));
                    memClicked.add(pos);
                    memValues.add(result);
                    System.out.println(result);
                } else if (num == 4) {
                    int pos = memClicked.get(0);
                    int result = Integer.parseInt(cmd.get(pos));
                    memClicked.add(pos);
                    memValues.add(result);
                    System.out.println(result);
                } else {
                    System.out.println("Invalid Input");
                }
                break;
            case 3:
                if (num == 1) {
                    int pos = cmd.lastIndexOf(memValues.get(0).toString());
                    int result = Integer.parseInt(cmd.get(pos));
                    memClicked.add(pos);
                    memValues.add(result);
                    System.out.println(result);
                } else if (num == 2) {
                    int pos = cmd.lastIndexOf(memValues.get(0).toString());
                    int result = Integer.parseInt(cmd.get(pos));
                    memClicked.add(pos);
                    memValues.add(result);
                    System.out.println(result);
                } else if (num == 3) {
                    int pos = 3;
                    int result = Integer.parseInt(cmd.get(pos));
                    memClicked.add(pos);
                    memValues.add(result);
                    System.out.println(result);
                } else if (num == 4) {
                    int pos = cmd.lastIndexOf("4");
                    int result = Integer.parseInt(cmd.get(pos));
                    memClicked.add(pos);
                    memValues.add(result);
                    System.out.println(result);
                } else {
                    System.out.println("Invalid Input");
                }
                break;
            case 4:
                if (num == 1) {
                    int pos = memClicked.get(0);
                    int result = Integer.parseInt(cmd.get(pos));
                    memClicked.add(pos);
                    memValues.add(result);
                    System.out.println(result);
                } else if (num == 2) {
                    int pos = 1;
                    int result = Integer.parseInt(cmd.get(pos));
                    memClicked.add(pos);
                    memValues.add(result);
                    System.out.println(result);
                } else if (num == 3) {
                    int pos = memClicked.get(1);
                    int result = Integer.parseInt(cmd.get(pos));
                    memClicked.add(pos);
                    memValues.add(result);
                    System.out.println(result);
                } else if (num == 4) {
                    int pos = memClicked.get(1);
                    int result = Integer.parseInt(cmd.get(pos));
                    memClicked.add(pos);
                    memValues.add(result);
                    System.out.println(result);
                } else {
                    System.out.println("Invalid Input");
                }
                break;
            case 5:
                if (num == 1) {
                    int pos = cmd.lastIndexOf(memValues.get(0).toString());
                    int result = Integer.parseInt(cmd.get(pos));
                    memClicked.add(pos);
                    memValues.add(result);
                    System.out.println(result);
                } else if (num == 2) {
                    int pos = cmd.lastIndexOf(memValues.get(1).toString());
                    int result = Integer.parseInt(cmd.get(pos));
                    memClicked.add(pos);
                    memValues.add(result);
                    System.out.println(result);
                } else if (num == 3) {
                    int pos = cmd.lastIndexOf(memValues.get(3).toString());
                    int result = Integer.parseInt(cmd.get(pos));
                    memClicked.add(pos);
                    memValues.add(result);
                    System.out.println(result);
                } else if (num == 4) {
                    int pos = cmd.lastIndexOf(memValues.get(2).toString());
                    int result = Integer.parseInt(cmd.get(pos));
                    memClicked.add(pos);
                    memValues.add(result);
                    System.out.println(result);
                } else {
                    System.out.println("Invalid Input");
                }
                break;
            default:
                System.out.println("Invalid Input. Resetting");
                resetMem();
                break;
        }
    }

    private static void resetMem() {
        memClicked = new ArrayList<>();
        memValues = new ArrayList<>();
        System.out.println("Memory Reset");
    }

    private static void setupMorse() {
        morseMap.put("shell", 5);
        morseMap.put("halls", 15);
        morseMap.put("slick", 22);
        morseMap.put("trick", 32);
        morseMap.put("boxes", 35);
        morseMap.put("leaks", 42);
        morseMap.put("strobe", 45);
        morseMap.put("bistro", 52);
        morseMap.put("flick", 55);
        morseMap.put("bombs", 65);
        morseMap.put("break", 72);
        morseMap.put("brick", 75);
        morseMap.put("steak", 82);
        morseMap.put("sting", 92);
        morseMap.put("vector", 95);
        morseMap.put("beats", 0);

        for (String key : morseMap.keySet()) {
            morsePotentials.add(key);
        }
    }

    private static void resetMorse() {
        morsePotentials = new ArrayList<>();
        for (String key : morseMap.keySet()) {
            morsePotentials.add(key);
        }
        System.out.println("Morse Reset");
    }

    public static void morse(ArrayList<String> cmd) {
        String letter = "";
        for (String phrase : cmd) {
            switch (phrase) {
                case "01":
                    letter += "a";
                    break;
                case "1000":
                    letter += "b";
                    break;
                case "1010":
                    letter += "c";
                    break;
                case "0":
                    letter += "e";
                    break;
                case "0010":
                    letter += "f";
                    break;
                case "0000":
                    letter += "h";
                    break;
                case "00":
                    letter += "i";
                    break;
                case "101":
                    letter += "k";
                    break;
                case "0100":
                    letter += "l";
                    break;
                case "11":
                    letter += "m";
                    break;
                case "10":
                    letter += "n";
                    break;
                case "111":
                    letter += "o";
                    break;
                case "0110":
                    letter += "p";
                    break;
                case "010":
                    letter += "r";
                    break;
                case "000":
                    letter += "s";
                    break;
                case "1":
                    letter += "t";
                    break;
                case "0001":
                    letter += "v";
                    break;
                case "1001":
                    letter += "x";
                    break;
                default:
                    letter += "Invalid";
                    break;
            }
        }
        if (letter.contains("Invalid")) {
            System.out.println("Invalid Input");
            return;
        }
        ArrayList<String> newPotentials = new ArrayList<>();
        for (String word : morsePotentials) {
            if (word.contains(letter)) {
                newPotentials.add(word);
            }
        }
        if (newPotentials.size() == 1) {
            System.out.println("Code is: " + morseMap.get(newPotentials.get(0)));
            return;
        } else if (newPotentials.size() == 0) {
            System.out.println("No words remaining. Resetting.");
            resetMorse();
            return;
        } else {
            morsePotentials = newPotentials;
        }
        System.out.println("Need more letters. Possible words: " + morsePotentials);
    }

    public static void complicated(ArrayList<String> cmd) {
        String result = "";
        if (cmd.get(0).equals("all")) {
            result += "none          : YES\n";
            result += "all           : no\n";
            if (evenSerial) {
                result += "red           : YES\n";
                result += "red blue      : YES\n";
            } else {
                result += "red           : no\n";
                result += "red blue      : no\n";
            }
            result += "red star      : YES\n";
            if (batteries >= 2) {
                result += "red led       : YES\n";
            } else {
                result += "red led       : no\n";
            }
            if (parallel) {
                result += "red blue star : YES\n";
            } else {
                result += "red blue star : no\n";
            }
            if (evenSerial) {
                result += "red blue led  : YES\n";
            } else {
                result += "red blue led  : no\n";
            }
            if (batteries >= 2) {
                result += "red star led  : YES\n";
            } else {
                result += "red star led  : no\n";
            }
            if (evenSerial) {
                result += "blue          : YES\n";
            } else {
                result += "blue          : no\n";
            }
            result += "blue star     : no\n";
            if (parallel) {
                result += "blue led      : YES\n";
                result += "blue star led : YES\n";
            } else {
                result += "blue led      : no\n";
                result += "blue star led : no\n";
            }
            result += "star          : YES\n";
            if (batteries >= 2) {
                result += "star led      : YES\n";
            } else {
                result += "star led      : no\n";
            }
            result += "led           : no\n";
            System.out.println(result);
        }
        for (String wire : cmd) {
            boolean red = wire.contains("r");
            boolean blue = wire.contains("b");
            boolean star = wire.contains("s");
            boolean light = wire.contains("l");

            if (red) {
                if (blue) {
                    if (star) {
                        if (light) {
                            result += "no ";
                        } else {
                            if (parallel) {
                                result += "YES ";
                            } else {
                                result += "no ";
                            }
                        }
                    } else {
                        if (light) {
                            if (evenSerial) {
                                result += "YES ";
                            } else {
                                result += "no ";
                            }
                        } else {
                            if (evenSerial) {
                                result += "YES ";
                            } else {
                                result += "no ";
                            }
                        }
                    }
                } else {
                    if (star) {
                        if (light) {
                            if (batteries >= 2) {
                                result += "YES ";
                            } else {
                                result += "no ";
                            }
                        } else {
                            result += "YES ";
                        }
                    } else {
                        if (light) {
                            if (batteries >= 2) {
                                result += "YES ";
                            } else {
                                result += "no ";
                            }
                        } else {
                            if (evenSerial) {
                                result += "YES ";
                            } else {
                                result += "no ";
                            }
                        }
                    }
                }
            } else {
                if (blue) {
                    if (star) {
                        if (light) {
                            if (parallel) {
                                result += "YES ";
                            } else {
                                result += "no ";
                            }
                        } else {
                            result += "no ";
                        }
                    } else {
                        if (light) {
                            if (parallel) {
                                result += "YES ";
                            } else {
                                result += "no ";
                            }
                        } else {
                            if (evenSerial) {
                                result += "YES ";
                            } else {
                                result += "no ";
                            }
                        }
                    }
                } else {
                    if (star) {
                        if (light) {
                            if (batteries >= 2) {
                                result += "YES ";
                            } else {
                                result += "no ";
                            }
                        } else {
                            result += "YES ";
                        }
                    } else {
                        if (light) {
                            result += "no ";
                        } else {
                            result += "YES ";
                        }
                    }
                }
            }
        }
        System.out.println(result);
    }

    private static void setupWireSeq() {
        wireSeq = new int[] {0, 0, 0};
        String[] redArray = {"c", "b", "a", "ac", "b", "ac", "abc", "ab", "b"};
        String[] blueArray = {"b", "ac", "b", "a", "b", "bc", "c", "ac", "a"};
        String[] blackArray = {"abc", "ac", "b", "ac", "b", "bc", "ab", "c", "c"};
        wireMap = new String[][] {redArray, blueArray, blackArray};
    }

    private static void resetWireSeq() {
        wireSeq = new int[] {0, 0, 0};
        System.out.println("Reset Wire Sequences.");
    }

    private static void wireSeq(ArrayList<String> cmd) {
        String result = "";
        for (String wire : cmd) {
            if (wireSeq[0] > 8 || wireSeq[1] > 8 || wireSeq[2] > 8) {
                System.out.println("Invalid Input. Resetting.");
                resetWireSeq();
                return;
            }
            if (wire.contains("r")) {
                wire = wire.substring(1);
                if (wireMap[0][wireSeq[0]].contains(wire)) {
                    result += "YES ";
                } else {
                    result += "no ";
                }
                wireSeq[0]++;
            } else if (wire.contains("bl")) {
                wire = wire.substring(2);
                if (wireMap[1][wireSeq[1]].contains(wire)) {
                    result += "YES ";
                } else {
                    result += "no ";
                }
                wireSeq[1]++;
            } else {
                wire = wire.substring(1);
                if (wireMap[2][wireSeq[2]].contains(wire)) {
                    result += "YES ";
                } else {
                    result += "no ";
                }
                wireSeq[2]++;
            }
        }
        System.out.println(result);
    }

    private static void setupMazes() {
        int[][] circles = { {1, 0}, {3, 5} };
        int[][] walls = { {0, 5}, {1, 2}, {1, 8}, {1, 10},  {2, 1}, {2, 5}, {3, 4}, {3, 6}, {3, 8}, {4, 1},
                {4, 5}, {5, 2}, {5, 8}, {6, 1}, {6, 7}, {7, 2}, {7, 4}, {7, 6}, {7, 8}, {8, 5},
                {8, 9}, {9, 2}, {9, 8}, {10, 3}, {10, 7} };
        mazes.add(createMaze(circles, walls));
        circles = new int[][] { {3, 1}, {1, 4} };
        walls = new int[][] { {0, 5}, {1, 0}, {1, 4}, {1, 10}, {2, 3}, {2, 7}, {3, 4}, {3, 6}, {3, 8}, {4, 1},
                {4, 5}, {5, 4}, {5, 8}, {6, 3}, {6, 7}, {6, 9}, {7, 2}, {7, 6}, {8, 1}, {8, 3}, {8, 5}, {8, 9},
                {9, 8}, {10, 1}, {10, 5} };
        mazes.add(createMaze(circles, walls));
        circles = new int[][] { {3, 3}, {3, 5} };
        walls = new int[][] {{0, 5}, {0, 7}, {1, 2}, {2, 1}, {2, 3}, {2, 5}, {2, 9}, {3, 0}, {3, 6}, {3, 8},
                {4, 3}, {4, 5}, {4, 9}, {6, 1}, {6, 3}, {6, 5}, {6, 7}, {6, 9}, {8, 1}, {8, 5}, {8, 7}, {8, 9},
                {9, 2}, {9, 4}, {10, 7} };
        mazes.add(createMaze(circles, walls));
        circles = new int[][] { {0, 0}, {3, 0} };
        walls = new int[][] { {0, 3}, {1, 4}, {1, 6}, {1, 8}, {2, 1}, {2, 3}, {3, 6}, {3, 8}, {4, 1}, {4, 5},
                {4, 9}, {5, 2}, {5, 4}, {5, 8}, {6, 1}, {7, 2}, {7, 4}, {7, 6}, {7, 8}, {8, 9}, {9, 2}, {9, 4},
                {9, 6}, {10, 5}, {10, 9} };
        mazes.add(createMaze(circles, walls));
        circles = new int[][] { {2, 4}, {5, 3} };
        walls = new int[][] { {1, 0}, {1, 2}, {1, 4}, {1, 6}, {2, 9}, {3, 2}, {3, 4}, {3, 8}, {3, 10}, {4, 3},
                {4, 7}, {5, 4}, {5, 6}, {6, 1}, {6, 7}, {6, 9}, {7, 2}, {7, 4}, {7, 8}, {8, 1}, {8, 9}, {9, 4},
                {9, 6}, {9, 8}, {10, 1} };
        mazes.add(createMaze(circles, walls));
        circles = new int[][] { {0, 4}, {4, 2} };
        walls = new int[][] { {0, 1}, {0, 5}, {1, 6}, {2, 1}, {2, 3}, {2, 5}, {2, 9}, {3, 8}, {4, 3}, {4, 5},
                {4, 7}, {5, 2}, {5, 4}, {5, 10}, {6, 3}, {6, 7}, {6, 9}, {7, 0}, {8, 3}, {8, 5}, {8, 7}, {9, 2},
                {9, 4}, {9, 8}, {10, 7} };
        mazes.add(createMaze(circles, walls));
        circles = new int[][] { {0, 1}, {5, 1} };
        walls = new int[][] { {0, 7}, {1, 2}, {1, 4}, {2, 1}, {2, 5}, {2, 9}, {3, 4}, {3, 6}, {3, 8}, {4, 3},
                {4, 7}, {5, 0}, {5, 2}, {5, 6}, {5, 10}, {6, 3}, {6, 9}, {7, 6}, {7, 8}, {8, 1}, {8, 3}, {8, 9},
                {9, 2}, {9, 4}, {9, 6} };
        mazes.add(createMaze(circles, walls));
        circles = new int[][] { {0, 3}, {3, 2} };
        walls = new int[][] { {0, 1}, {0, 7}, {1, 4}, {2, 5}, {2, 9}, {3, 2}, {3, 4}, {3, 6}, {3, 8}, {4, 1},
                {4, 9}, {5, 4}, {5, 6}, {6, 1}, {6, 5}, {7, 2}, {7, 6}, {7, 8}, {7, 10}, {8, 1}, {8, 3}, {9, 4},
                {9, 6}, {9, 8}, {9, 10} };
        mazes.add(createMaze(circles, walls));
        circles = new int[][] { {1, 2}, {0, 4} };
        walls = new int[][] { {0, 1}, {1, 4}, {1, 6}, {2, 1}, {2, 3}, {2, 7}, {2, 9}, {3, 6}, {4, 5}, {4, 9},
                {5, 2}, {5, 4}, {5, 8}, {6, 1}, {6, 3}, {6, 7}, {7, 6}, {7, 8}, {8, 1}, {8, 3}, {8, 5}, {8, 9},
                {9, 10}, {10, 3}, {10, 7} };
        mazes.add(createMaze(circles, walls));
    }

    private static String[][] createMaze(int[][] circle, int[][]walls) {
        String[][] maze = new String[11][11];
        for (String[] arr: maze) {
            Arrays.fill(arr, "");
        }
        maze[circle[0][0]*2][circle[0][1]*2] = "c";
        maze[circle[1][0]*2][circle[1][1]*2] = "c";
        for (int i = 0; i < walls.length; i++) {
            if ((walls[i][0]%2) == 0) {
                maze[walls[i][0]][walls[i][1]] = "|";
            } else {
                maze[walls[i][0]][walls[i][1]] = "-";
            }
        }
        return maze;
    }

    private static void printMaze(int index) {
        String[][] maze = mazes.get(index);
        System.out.println("");
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j].equals("")) {
                    if ((i%2) == 0 && (j%2) == 0) {
                        System.out.print(".");
                    } else {
                        System.out.print(" ");
                    }
                } else {
                    System.out.print(maze[i][j]);
                }
            }
            System.out.print("\n");
        }
        System.out.println("");
    }

    private static void maze(ArrayList<String> cmd) {
        String[] circle = cmd.get(0).split(",");
        Coordinate circleCoord = new Coordinate(Integer.parseInt(circle[0])*2, Integer.parseInt(circle[1])*2, "");
        String[] start = cmd.get(1).split(",");
        Coordinate startCoord = new Coordinate(Integer.parseInt(start[0])*2, Integer.parseInt(start[1])*2, "");
        String[] end = cmd.get(2).split(",");
        Coordinate endCoord = new Coordinate(Integer.parseInt(end[0])*2, Integer.parseInt(end[1])*2, "");
        int mazeNum = findMaze(circleCoord);
        if (mazeNum < 0) {
            System.out.println("No Maze Found.");
            return;
        }
        String path = findPath(mazeNum, startCoord, endCoord);
        String[] splitPath = path.split(" ");
        for (int i = 0; i < splitPath.length; i+=3) {
            String print = splitPath[i];
            if (i+1 < splitPath.length) {
                print += " " + splitPath[i+1];
                if (i+2 < splitPath.length) {
                    print += " " + splitPath[i+2];
                }
            }
            System.out.println(print);
        }

    }

    private static int findMaze(Coordinate circle) {
        int row = circle.getRow();
        int col = circle.getCol();
        for (int i = 0; i < mazes.size(); i++) {
            String[][] maze = mazes.get(i);
            if (maze[row][col].equals("c")) {
                return i;
            }
        }
        return -1;
    }

    private static String findPath(int mazeNum, Coordinate start, Coordinate end) {
        mazeQueue = new LinkedList<>();
        mazeQueue.add(start);
        String path = "";
        while (!mazeQueue.isEmpty()) {
            Coordinate point = mazeQueue.remove();
            path = explore(mazeNum, point, end);
            if (!path.equals("")) {
                return path;
            }
        }
        return "No Path Found";
    }

    private static String explore(int mazeNum, Coordinate point, Coordinate end) {
        String[][] maze = mazes.get(mazeNum);
        int row = point.getRow();
        int col = point.getCol();
        String lastStep = point.getLastStep();
        boolean up = (row > 0 && !lastStep.equals("down") && !maze[row-1][col].equals("-"));
        boolean down = (row < maze.length-1 && !lastStep.equals("up") && !maze[row+1][col].equals("-"));
        boolean left = (col > 0 && !lastStep.equals("right") && !maze[row][col-1].equals("|"));
        boolean right = (col < maze.length-1 && !lastStep.equals("left") && !maze[row][col+1].equals("|"));
        if (up) {
            Coordinate next = new Coordinate(row-2, col, point.getPath());
            next.addToPath("up");
            if (next.equals(end)) {
                return next.getPath();
            }
            mazeQueue.add(next);
        }
        if (down) {
            Coordinate next = new Coordinate(row+2, col, point.getPath());
            next.addToPath("down");
            if (next.equals(end)) {
                return next.getPath();
            }
            mazeQueue.add(next);
        }
        if (left) {
            Coordinate next = new Coordinate(row, col-2, point.getPath());
            next.addToPath("left");
            if (next.equals(end)) {
                return next.getPath();
            }
            mazeQueue.add(next);
        }
        if (right) {
            Coordinate next = new Coordinate(row, col+2, point.getPath());
            next.addToPath("right");
            if (next.equals(end)) {
                return next.getPath();
            }
            mazeQueue.add(next);
        }
        return "";
    }

    private static void setupPasswords() {
        ArrayList<String> pwList = new ArrayList<>();
        pwList.add("about");
        pwList.add("after");
        pwList.add("again");
        pwList.add("below");
        pwList.add("could");
        pwList.add("every");
        pwList.add("first");
        pwList.add("found");
        pwList.add("great");
        pwList.add("house");
        pwList.add("large");
        pwList.add("learn");
        pwList.add("never");
        pwList.add("other");
        pwList.add("place");
        pwList.add("plant");
        pwList.add("point");
        pwList.add("right");
        pwList.add("small");
        pwList.add("sound");
        pwList.add("spell");
        pwList.add("still");
        pwList.add("study");
        pwList.add("their");
        pwList.add("there");
        pwList.add("these");
        pwList.add("thing");
        pwList.add("think");
        pwList.add("three");
        pwList.add("water");
        pwList.add("where");
        pwList.add("world");
        pwList.add("would");
        pwList.add("write");

        passwordPotentials = (ArrayList<String>) pwList.clone();
    }

    private static void resetPw() {
        setupPasswords();
        letterCombos.clear();
        System.out.println("Passwords Reset.");
    }

    public static void pw(ArrayList<String> cmd) {
        for (String combo : cmd) {
            ArrayList<String> filteredPasswords = new ArrayList<>();
            for (String word : passwordPotentials) {
                if (combo.contains(String.valueOf(word.charAt(letterCombos.size())))) {
                    filteredPasswords.add(word);
                }
            }
            letterCombos.add(combo);
            passwordPotentials = filteredPasswords;
            if (passwordPotentials.size() == 1) {
                System.out.println(passwordPotentials.get(0));
                return;
            }
        }
        System.out.println("Need More Letters.");
    }

    public static void dial(ArrayList<String> cmd) {
        switch (cmd.get(0)) {
            case "011101":
                System.out.println("Up.");
                return;
            case "010011":
                System.out.println("Up.");
                return;
            case "001101":
                System.out.println("Down.");
                return;
            case "010001":
                System.out.println("Down.");
                return;
            case "010111":
                System.out.println("Left.");
                return;
            case "010110":
                System.out.println("Left.");
                return;
            case "111010":
                System.out.println("Right.");
                return;
            case "100010":
                System.out.println("Right.");
                return;
        }
    }
}
