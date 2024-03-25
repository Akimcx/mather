package cx.ksim.mather.cli;

public final class LiteralNode implements Expression {

    private double value;

    public LiteralNode(double value) {
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

    @Override
    public String sexp() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sexp'");
    }

}
