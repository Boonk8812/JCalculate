import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Main {
    private ScriptEngine engine;

    public Main() {
        ScriptEngineManager mgr = new ScriptEngineManager();
        engine = mgr.getEngineByName("graal.js");
    }

    public double calculate(String expression) throws ScriptException {
        Object result = engine.eval(expression);
        if (result instanceof Number) {
            return ((Number) result).doubleValue();
        } else {
            throw new ScriptException("Expression did not return a number");
        }
    }

    public static void main(String[] args) {
        Main calculator = new Main();
        try {
            System.out.println("Result: " + calculator.calculate("3 + 5 * 2"));
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    public double calculateUsingMath(String expression) throws ScriptException {
        // Replace JavaScript math functions with Java Math functions
        expression = expression.replaceAll("Math\\.", "Math.");
        Object result = engine.eval(expression);
        if (result instanceof Number) {
            return ((Number) result).doubleValue();
        } else {
            throw new ScriptException("Expression did not return a number");
        }
    }

    public double add(double... numbers) {
        double result = 0;
        for (double number : numbers) {
            result += number;
        }
        return result;
    }

    public double subtract(double... numbers) {
        if (numbers.length == 0) return 0;
        double result = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            result -= numbers[i];
        }
        return result;
    }

    public double multiply(double... numbers) {
        double result = 1;
        for (double number : numbers) {
            result *= number;
        }
        return result;
    }

    public double divide(double... numbers) {
        if (numbers.length == 0) return 0;
        double result = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] == 0) throw new ArithmeticException("Division by zero");
            result /= numbers[i];
        }
        return result;
    }

    public double power(double base, double exponent) {
        return Math.pow(base, exponent);
    }

    public double sqrt(double number) {
        return Math.sqrt(number);
    }

    public double log(double number) {
        return Math.log(number);
    }

    public double log10(double number) {
        return Math.log10(number);
    }

    public double sin(double angle) {
        return Math.sin(angle);
    }

    public double cos(double angle) {
        return Math.cos(angle);
    }

    public double tan(double angle) {
        return Math.tan(angle);
    }

    public double asin(double value) {
        return Math.asin(value);
    }

    public double acos(double value) {
        return Math.acos(value);
    }

    public double atan(double value) {
        return Math.atan(value);
    }

    public double abs(double number) {
        return Math.abs(number);
    }

    public double ceil(double number) {
        return Math.ceil(number);
    }

    public double floor(double number) {
        return Math.floor(number);
    }

    public double round(double number) {
        return Math.round(number);
    }

    public double exp(double exponent) {
        return Math.exp(exponent);
    }

    public double max(double... numbers) {
        double result = Double.NEGATIVE_INFINITY;
        for (double number : numbers) {
            result = Math.max(result, number);
        }
        return result;
    }

    public double min(double... numbers) {
        double result = Double.POSITIVE_INFINITY;
        for (double number : numbers) {
            result = Math.min(result, number);
        }
        return result;
    }
}
