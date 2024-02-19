package cx.ksim.mather;

public abstract class Expression {
	private String operator;

	public String getOperator() {
		return operator;
	}

//	public abstract void printAst();

	public abstract double eval();

	public abstract String print();

}
