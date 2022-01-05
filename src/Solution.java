import java.util.*;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;


public class Solution {
    ArrayList<String> lines;

    public Solution(String filename) { this.lines = parse(filename); }

    private ArrayList<String> parse(String filename) {
        ArrayList<String> lines = new ArrayList<>();

        try {
            File f            = new File(filename);
            FileReader fr     = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line;

            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    private boolean isOpen(Character c) {
        return ((c == '[') || (c == '<') || (c == '{') || (c == '('));
    }

    private char getClosed(Character c) {
        switch (c) {
            case ']': return '[';
            case '>': return '<';
            case ')': return '(';
            case '}': return '{';
            default:  return  '\0';
        }
    }

    private char getOpen(Character c) {
         switch (c) {
             case '[': return ']';
             case '<': return '>';
             case '(': return ')';
             case '{': return '}';
             default:  return  '\0';
         }
    }

    public void solveP1() {
        Map<Character, Integer> charCount = new HashMap<>();
        Stack<Character> lineStack;

        charCount.put(']', 0);
        charCount.put('}', 0);
        charCount.put(')', 0);
        charCount.put('>', 0);

        for (String line : this.lines) {
            lineStack = new Stack<>();

            for (char c : line.toCharArray()) {
                if (isOpen(c)) {
                    lineStack.add(c);
                } else {
                    Character open   = lineStack.pop();
                    Character closed = getClosed(c);

                    if (open != closed) {
                        charCount.put(c, charCount.get(c) + 1);
                        break;
                    }
                }
            }
        }

        System.out.println("Syntax score: " + ((charCount.get(']') * 57) + (charCount.get('}') * 1197) + (charCount.get(')') * 3) + (charCount.get('>')) * 25137));
    }

    public void solveP2() {
         Map<Character, Integer> charScore = new HashMap<>();
         ArrayList<Long> scores            = new ArrayList<>();
         Stack<Character> lineStack;
         boolean error;

         charScore.put(']', 2);
         charScore.put('}', 3);
         charScore.put(')', 1);
         charScore.put('>', 4);

         for (String line : this.lines) {
             lineStack = new Stack<>();
             error     = false;

             for (char c : line.toCharArray()) {
                 if (isOpen(c)) {
                     lineStack.add(c);
                 } else {
                     Character open   = lineStack.pop();
                     Character closed = getClosed(c);

                     if (open != closed) {
                         error = true;
                         break;
                     }
                 }
             }

             if (!error) {
                 long points = 0;

                 while (!lineStack.isEmpty()) {
                     points = (points * 5) + charScore.get(getOpen(lineStack.pop()));
                 }

                 scores.add(points);
             }
         }
         
         Collections.sort(scores);
         System.out.println("Median score: " + scores.get(scores.size()/2));
    }
}