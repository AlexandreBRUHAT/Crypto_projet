import javafx.util.Pair;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;

public class Main {

    public static void main(String[] args) {

        int bits = 1024;
        Paillier paillier = new Paillier(bits);

        Key key = paillier.KeygenPaillier();

        Random random = new Random();
        BigInteger x = BigInteger.probablePrime(bits, random);
        BigInteger y = BigInteger.probablePrime(bits, random);

        BigInteger X = paillier.paillierEncrypt(x, key.getPK());
        BigInteger Y = paillier.paillierEncrypt(y, key.getPK());

        //____________________________________________________

        Multiplication multiplication = new Multiplication(paillier, key);

        BigInteger result = multiplication.mult3(multiplication.mult2(multiplication.mult1(X, Y)));

        System.out.println("Résultat obtenu : " + paillier.paillierDecrypt(result,key.getPK(), key.getSK()));
        System.out.println("Résultat voulu : " + x.multiply(y));

        System.out.println("Bool voulu : " + x.multiply(y).equals(paillier.paillierDecrypt(result,key.getPK(), key.getSK())));
    }

    public static void tp2() {
        Instant time0, timeEncrypt, timeDecrypt;

        System.out.println("PAILLIER :");

        Paillier paillier = new Paillier(1024);

        Key keyPaillier = paillier.KeygenPaillier();

        BigInteger mp = BigInteger.TEN;
        BigInteger mp2 = new BigInteger("500");

        BigInteger mpe, mp2e, mult, result;

        time0 = Instant.now();

        mpe = paillier.paillierEncrypt(mp, keyPaillier.getPK());

        timeEncrypt = Instant.now();

        mp = paillier.paillierDecrypt(mpe, keyPaillier.getN(), keyPaillier.getSK());

        timeDecrypt = Instant.now();

        System.out.println("Message Encrypté timer = " + timeEncrypt.minusMillis(time0.toEpochMilli()).toEpochMilli() + "ms");
        System.out.println("Message décrypté : " + mp + ", time = " + timeDecrypt.minusMillis(time0.toEpochMilli()).toEpochMilli() + "ms");

        System.out.println("RSA : ");

        BigInteger mr = BigInteger.TEN;

        RSA rsa = new RSA(1024);

        Key keyRSA = rsa.keyGenRSA();

        time0 = Instant.now();

        mr = rsa.rsaEncrypt(mr, keyRSA.getPK(), keyRSA.getN());

        timeEncrypt = Instant.now();

        mr = rsa.rsaDecrypt(mr, keyRSA.getSK(), keyRSA.getN());

        timeDecrypt = Instant.now();

        System.out.println("Message Encrypté timer = " + timeEncrypt.minusMillis(time0.toEpochMilli()).toEpochMilli() + "ms");
        System.out.println("Message décrypté : " + mp2 + ", time = " + timeDecrypt.minusMillis(time0.toEpochMilli()).toEpochMilli() + "ms");

        System.out.println("Add homomorphique Paillier :");

        mp2e = paillier.paillierEncrypt(mp2, keyPaillier.getPK());

            mult = mp2e.multiply(mpe).mod(keyPaillier.getN().pow(2));

        result = paillier.paillierDecrypt(mult, keyPaillier.getN(), keyPaillier.getSK());

        System.out.println("Result = " + result);
    }



    public static void tp1() {
        Random random = new Random();

        BigInteger p = BigInteger.probablePrime(512, random);

        BigInteger q = BigInteger.probablePrime(512, random);

        System.out.println("p = " + p);
        System.out.println("q = " + q);

        BigInteger n = p.multiply(q);
        BigInteger phiN = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        // n = p  * q
        System.out.println("p * q = " + n);
        //PhiN
        System.out.println("(p - 1) * (q - 1) = " + phiN);
        System.out.println();

        BigInteger e;
//        X^d mod n = x

//        KeyGen -> p, q, phiN, e, d | () -> e, d
//        Encrypt -> E : x -> x^e mod n
//        Decrypt -> D: X -> X -> x^d mod n

        do {
            e = BigInteger.probablePrime(16, random);
        } while (phiN.mod(e).compareTo(BigInteger.ZERO) == 0);

        // e est un nombre premier de 16bits
        System.out.println("e = " + e);

        // Inverse modulaire de e par phiN
        BigInteger d = e.modInverse(phiN);

        System.out.println("d = " + d);

        System.out.println("e * d % phi = " + e.multiply(d).mod(phiN));

        BigInteger x;

        for(int i = 0; i < 10; ++i) {
            x = new BigInteger(n.bitLength(), random).mod(n);

            BigInteger X = x.modPow(e, n);

            System.out.println("x = " + x);

            System.out.println("x^e mod n = " + X);

            System.out.println("X^d mod n = " + X.modPow(d, n));

            System.out.println();
        }

//        X^d mod n = x
//
//        KeyGen -> p, q, phiN, e, d | () -> e, d
//        Encrypt -> E : x -> x^e mod n
//        Decrypt -> D: X -> X -> x^d mod n

    }
}