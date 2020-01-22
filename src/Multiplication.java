import javafx.util.Pair;

import java.math.BigInteger;
import java.util.Random;

public class Multiplication {

    private int bits;

    private Key key;

    private BigInteger R;
    private BigInteger S;

    private BigInteger X;
    private BigInteger Y;

    private BigInteger r;
    private BigInteger s;

    private Paillier paillier;

    public Multiplication(Paillier paillier, Key key) {
        bits = 1024;
        this.paillier = paillier;
        this.key = key;
    }

    public Pair<BigInteger, BigInteger> mult1(BigInteger X, BigInteger Y) {

        this.X = X;
        this.Y = Y;

        Random random = new Random();

        r = BigInteger.probablePrime(bits, random).mod(key.getPK());

        s = BigInteger.probablePrime(bits, random).mod(key.getPK());

        R = paillier.paillierEncrypt(r, key.getPK());

        S = paillier.paillierEncrypt(s, key.getPK());

        return new Pair<>(R.multiply(X).mod(key.getPK().pow(2)),S.multiply(Y).mod(key.getPK().pow(2)));
    }

    public BigInteger mult2(Pair<BigInteger, BigInteger> vals) {
        return vals.getKey().modPow(paillier.paillierDecrypt(vals.getValue(), key.getPK(), key.getSK()), key.getPK().pow(2));
    }

    public BigInteger mult3(BigInteger prod) {
        return prod.multiply(Y.modPow(r, key.getPK().pow(2)).negate())
                .multiply(X.modPow(s, key.getPK().pow(2)).negate())
                .multiply(R.modPow(s, key.getPK().pow(2)).negate())
                .mod(key.getPK());
    }

    public Key getKey() {
        return key;
    }
}
