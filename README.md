# Calculator Servlet

A responsive calculator web application built with Java Servlets, HTML, CSS, and basic JavaScript. Arithmetic is performed on the server by a Jakarta Servlet.

## Features

- Addition, subtraction, multiplication, division, and modulus
- Decimal input, clear, and delete
- Keyboard support: digits, `.`, `+`, `-`, `*`, `/`, `%`, Enter, Backspace, and Escape
- Division-by-zero error handling
- Responsive dark interface

## Requirements

- JDK 26
- Apache Maven
- Apache Tomcat 11

## Build

From the project directory, run:

```powershell
mvn clean package
```

Maven creates `target/calculator-servlet.war`.

## Run on Tomcat

Copy the WAR to Tomcat's `webapps` folder, start Tomcat, then open the application:

```powershell
Copy-Item .\target\calculator-servlet.war "C:\path\to\apache-tomcat-11\webapps\calculator-servlet.war"
& "C:\path\to\apache-tomcat-11\bin\catalina.bat" run
```

Visit <http://localhost:8080/calculator-servlet/>.

## Project layout

```text
calculator-servlet/
├── pom.xml
└── src/main/
    ├── java/com/calculator/CalculatorServlet.java
    ├── resources/
    └── webapp/
        ├── index.html
        └── css/style.css
```

The browser sends calculation requests to the Servlet's `/calculate` endpoint using HTTP POST. The Servlet validates the inputs, calculates with `BigDecimal`, and returns a result or an error message.
