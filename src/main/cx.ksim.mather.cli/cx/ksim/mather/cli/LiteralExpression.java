package cx.ksim.mather.cli;

public final class LiteralExpression implements Expression {

	private double value;
	public LiteralExpression(double value) {
		this.value = value;
	}

	@Override
	public double eval() {
		return this.value;
	}

	@Override
	public String print() {
		return Double.toString(value);
	}

	@Override
	public String toString() {
		return print();
	}
	
	

}
