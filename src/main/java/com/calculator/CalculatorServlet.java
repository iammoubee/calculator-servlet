package com.calculator;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "CalculatorServlet", urlPatterns = "/calculate")
public class CalculatorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final MathContext DIVISION_CONTEXT = MathContext.DECIMAL128;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain");

        try {
            BigDecimal firstNumber = parseNumber(request.getParameter("firstNumber"));
            BigDecimal secondNumber = parseNumber(request.getParameter("secondNumber"));
            String operator = request.getParameter("operator");

            BigDecimal result = calculate(firstNumber, secondNumber, operator);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(formatResult(result));
        } catch (NumberFormatException exception) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST,
                    "Please enter valid decimal numbers.");
        } catch (IllegalArgumentException exception) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, exception.getMessage());
        } catch (ArithmeticException exception) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST,
                    "Cannot divide or calculate a remainder by zero.");
        }
    }

    private BigDecimal parseNumber(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Enter both numbers before calculating.");
        }
        return new BigDecimal(value.trim());
    }

    private BigDecimal calculate(BigDecimal first, BigDecimal second, String operator) {
        if (operator == null) {
            throw new IllegalArgumentException("Choose an operation.");
        }

        return switch (operator) {
            case "+" -> first.add(second);
            case "-" -> first.subtract(second);
            case "*" -> first.multiply(second);
            case "/" -> {
                if (second.compareTo(BigDecimal.ZERO) == 0) {
                    throw new ArithmeticException("Division by zero.");
                }
                yield first.divide(second, DIVISION_CONTEXT);
            }
            case "%" -> {
                if (second.compareTo(BigDecimal.ZERO) == 0) {
                    throw new ArithmeticException("Remainder by zero.");
                }
                yield first.remainder(second);
            }
            default -> throw new IllegalArgumentException("Choose a supported operation.");
        };
    }

    private String formatResult(BigDecimal result) {
        if (result.compareTo(BigDecimal.ZERO) == 0) {
            return "0";
        }
        return result.stripTrailingZeros().toPlainString();
    }

    private void sendError(HttpServletResponse response, int status, String message)
            throws IOException {
        response.setStatus(status);
        response.getWriter().write(message);
    }
}
