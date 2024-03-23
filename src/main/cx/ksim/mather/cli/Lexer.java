package cx.ksim.mather.cli;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Locale.Builder;

public class Lexer implements Iterator<Token> {

  private List<Token> tokens;
  private Iterator<Token> iterator;
  private int cursor = 0;

  public Lexer(String expr) {
    this.tokens = new ArrayList<>();
    tokenize(expr);
    this.iterator = tokens.iterator();
  }

  private void tokenize(String expr) {
    for (int i = 0, len = expr.length(); i < len; i++) {
      char currentChar = expr.charAt(i);
      if (Character.isWhitespace(currentChar)) continue;

      if (Character.isDigit(currentChar) || currentChar == '.') {
        boolean isFloat = currentChar == '.';

        StringBuilder builder = new StringBuilder();
        builder.append(currentChar);
        i++;
        while (i < len) {
          currentChar = expr.charAt(i);
          if (!isNum(currentChar)) break;
          if (currentChar == '.') {
            if (isFloat) break;
            else isFloat = true;
          }
          if (currentChar != '_') builder.append(currentChar);
          i++;
        }
        tokens.add(new Token(FactorToken.NUMBER, builder.toString()));
        i--;
      } else if (currentChar >= 'a' && currentChar <= 'z') {
        StringBuilder builder = new StringBuilder();
        builder.append(currentChar);
        i++;
        while (i < len) {
          currentChar = expr.charAt(i);
          if (!isLower(currentChar) && !isUpper(currentChar)) break;
          builder.append(currentChar);
          i++;
        }
        i--;
        tokens.add(new Token(FactorToken.FUNC_CALL, builder.toString()));
      } else if (currentChar == '+') {
        tokens.add(new Token(ExpressionToken.PLUS, Character.toString(currentChar)));
      } else if (currentChar == '-') {
        tokens.add(new Token(ExpressionToken.MINUS, Character.toString(currentChar)));
      } else if (currentChar == '*') {
        tokens.add(new Token(TermToken.MULT, Character.toString(currentChar)));
      } else if (currentChar == '/') {
        tokens.add(new Token(TermToken.DIV, Character.toString(currentChar)));
      } else if (currentChar == '(') {
        tokens.add(new Token(FactorToken.OPEN_PAREN, Character.toString(currentChar)));
      } else if (currentChar == ')') {
        tokens.add(new Token(FactorToken.CLOSE_PAREN, Character.toString(currentChar)));
      } else if (currentChar == '!') {
        tokens.add(new Token(FactorToken.UNARY_OP, Character.toString(currentChar)));
      } else if (currentChar == '%') {
        tokens.add(new Token(FactorToken.UNARY_OP, Character.toString(currentChar)));
      } else if (currentChar == '^') {
        // tokens.add( new Token( FactorToken.EXPONENT, Character.toString( currentChar
        // ) ) );
      } else {
        tokens.add(new Token(ErrorToken.ERROR, Character.toString(currentChar)));
      }
    }
    }

  private boolean isNum(char currentChar) {
    return Character.isDigit(currentChar) || currentChar == '.' || currentChar == '_';
  }

  private boolean isUpper(char currentChar) {
    return currentChar >= 'a' && currentChar <= 'z';
  }

  private boolean isLower(char currentChar) {
    return currentChar >= 'A' && currentChar <= 'Z';
  }

  public void dump() {
    System.out.println(tokens);
  }

  public Optional<Token> peek() {
    if (!hasNext()) return Optional.empty();
    return Optional.of(tokens.get(cursor));
  }

  public Token next() {
    if (!hasNext()) throw new NoSuchElementException();
    cursor += 1;
    return this.iterator.next();
  }

  @Override
  public boolean hasNext() {
    return this.iterator.hasNext();
  }
}
