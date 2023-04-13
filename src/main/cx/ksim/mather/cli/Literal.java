package cx.ksim.mather;

public class Literal extends Expression {

	private double value;

	public Literal(double value) {
		this.value = value;
	}

	@Override
	public double eval() {
		return this.value;
	}

	@Override
	public String print() {
		return Double.toString( value );
	}

	@Override
	public String toString() {
		return print();
	}

}
