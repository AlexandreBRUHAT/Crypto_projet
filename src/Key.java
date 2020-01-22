import java.math.BigInteger;

public class Key {

    private BigInteger pk;
    private BigInteger sk;
    private BigInteger n;

    public Key(BigInteger sk, BigInteger pk, BigInteger n) {
        this.pk = pk;
        this.sk = sk;
        this.n = n;
    }

    public BigInteger getSK() {
        return sk;
    }

    public BigInteger getPK() {
        return pk;
    }

    public BigInteger getN() {
        return n;
    }

    public void displayKey() {
        System.out.println("PK=" + pk + " \n SK=" + sk + " \n " + n);
    }
}
