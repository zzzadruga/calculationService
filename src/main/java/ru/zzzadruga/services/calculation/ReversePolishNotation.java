package ru.zzzadruga.services.calculation;

import java.math.BigDecimal;
import java.util.*;

public class ReversePolishNotation {

    public static final Map<String, Integer> MAIN_MATH_OPERATIONS;

    static {
        MAIN_MATH_OPERATIONS = new HashMap<String, Integer>();
        MAIN_MATH_OPERATIONS.put("%", 1);
        MAIN_MATH_OPERATIONS.put("*", 2);
        MAIN_MATH_OPERATIONS.put("/", 2);
        MAIN_MATH_OPERATIONS.put("+", 3);
        MAIN_MATH_OPERATIONS.put("-", 3);
    }


    public static String sortingStation(String expression, Map<String, Integer> operations, String leftBracket,
                                        String rightBracket) throws Exception {
        if (expression == null || expression.length() == 0)
            throw new Exception("Expression isn't specified.");
        if (operations == null || operations.isEmpty())
            throw new Exception("Operations aren't specified.");

        List<String> out = new ArrayList<String>();

        Stack<String> stack = new Stack<String>();


        expression = expression.replace(" ", "");


        Set<String> operationSymbols = new HashSet<String>(operations.keySet());
        operationSymbols.add(leftBracket);
        operationSymbols.add(rightBracket);


        int index = 0;

        boolean findNext = true;
        while (findNext) {
            int nextOperationIndex = expression.length();
            String nextOperation = "";

            for (String operation : operationSymbols) {
                int i = expression.indexOf(operation, index);
                if (i >= 0 && i < nextOperationIndex) {
                    nextOperation = operation;
                    nextOperationIndex = i;
                }
            }

            if (nextOperationIndex == expression.length()) {
                findNext = false;
            } else {

                if (index != nextOperationIndex) {
                    out.add(expression.substring(index, nextOperationIndex));
                }

                if (nextOperation.equals(leftBracket)) {
                    stack.push(nextOperation);
                } else if (nextOperation.equals(rightBracket)) {
                    while (!stack.peek().equals(leftBracket)) {
                        out.add(stack.pop());
                        if (stack.empty()) {
                            throw new Exception("Unmatched brackets");
                        }
                    }
                    stack.pop();
                } else {
                    while (!stack.empty() && !stack.peek().equals(leftBracket) &&
                            (operations.get(nextOperation) >= operations.get(stack.peek()))) {
                        out.add(stack.pop());
                    }
                    stack.push(nextOperation);
                }
                index = nextOperationIndex + nextOperation.length();
            }
        }

        if (index != expression.length()) {
            out.add(expression.substring(index));
        }

        while (!stack.empty()) {
            out.add(stack.pop());
        }
        StringBuffer result = new StringBuffer();
        if (!out.isEmpty())
            result.append(out.remove(0));
        while (!out.isEmpty())
            result.append(" ").append(out.remove(0));

        return result.toString();
    }


    public static String sortingStation(String expression, Map<String, Integer> operations) throws Exception {
        return sortingStation(expression, operations, "(", ")");
    }


    public static BigDecimal calculateExpression(String expression) throws Exception {
        String rpn = sortingStation(expression, MAIN_MATH_OPERATIONS);
        StringTokenizer tokenizer = new StringTokenizer(rpn, " ");
        Stack<BigDecimal> stack = new Stack<BigDecimal>();
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();

            if (!MAIN_MATH_OPERATIONS.keySet().contains(token)) {
                try {
                    stack.push(new BigDecimal(token));
                } catch (NumberFormatException e){
                    throw new Exception("Operands \"" + token + "\" is not valid");
                }
            } else {
                BigDecimal operand2 = stack.pop();
                BigDecimal operand1 = stack.empty() ? BigDecimal.ZERO : stack.pop();
                if (token.equals("*")) {
                    stack.push(operand1.multiply(operand2));
                } else if (token.equals("/")) {
                    stack.push(operand1.divide(operand2));
                } else if (token.equals("+")) {
                    stack.push(operand1.add(operand2));
                } else if (token.equals("-")) {
                    stack.push(operand1.subtract(operand2));
                } else if (token.equals("%")) {
                    token = tokenizer.nextToken();
                    if (token.equals("*")) {
                        stack.push(operand1.multiply(operand2.divide(BigDecimal.valueOf(100))));
                    } else if (token.equals("/")) {
                        stack.push(operand1.divide(operand1.multiply(operand2.divide(BigDecimal.valueOf(100)))));
                    } else if (token.equals("+")) {
                        stack.push(operand1.multiply(operand2.divide(BigDecimal.valueOf(100)).add(BigDecimal.valueOf(1))));
                    } else if (token.equals("-")) {
                        stack.push(operand1.subtract(operand1.multiply(operand2.divide(BigDecimal.valueOf(100)))));
                    }

                }
            }
        }
        if (stack.size() != 1)
            throw new Exception("Expression syntax error.");
        return stack.pop();
    }


    private ReversePolishNotation() {
    }
}