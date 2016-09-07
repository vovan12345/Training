package Ex_1_2_16_1_2_17;

/**
 * Created by vl on 05.09.16.
 */

/*1.2.16 Rational numbers. Implement an immutable data type Rational for rational
numbers that supports addition, subtraction, multiplication, and division.
public class Rational

Rational(int numerator. int denominator)
Rational plus(Rational b) sum of this number and b
Rational minus(Rational b) difference of this number and b
Rational times(Rational b) product of this number and b
Rational divides(Rational b) quotient of this number and b
boolean equals(Rational that) is this number equal to that ?
String toString() string representation

You do not have to worry about testing for overflow (see Exercise 1.2.17), but use as
instance variables two long values that represent the numerator and denominator to
limit the possibility of overflow. Use Euclid’s algorithm (see page 4) to ensure that the
numerator and denominator never have any common factors. Include a test client that
exercises all of your methods.

1.2.17 Robust implementation of rational numbers. Use assertions to develop an
implementation of Rational (see Exercise 1.2.16) that is immune to overflow.
*/

public class Rational {
    public static void main(String[] args) {
        try {
            if (args.length == 2) {
                String a1 = args[0];
                String b1 = args[1];

                String[] aArray = a1.split("/");
                long aNumer = Long.parseLong(aArray[0]);
                long aDenom = Long.parseLong(aArray[1]);
                Rational a = new Rational(aNumer, aDenom);
                String[] bArray = b1.split("/");
                long bNumer = Long.parseLong(bArray[0]);
                long bDenom = Long.parseLong(bArray[1]);
                Rational b = new Rational(bNumer, bDenom);

                System.out.println(a.plus(b).toString());
                System.out.println(a.minus(b).toString());
                System.out.println(a.times(b).toString());
                System.out.println(a.divides(b).toString());
                System.out.println(a.equals(b));
            }
            else {
                System.out.println("There must be two arguments");
                System.exit(1);
            }
        }
        catch (ArithmeticException e1) {
            System.out.println("В знаменателе не может быть ноль");
            System.exit(1);
        }
        catch (NumberFormatException e2) {
            System.out.println("Введите числитель и знаменатель через / для каждого числа");
        }
    }

    private final long numer;
    private final long denom;

    Rational(long numer, long denom) {
        long gcd = gcd( numer, denom );

        if ( gcd > 1 ) {
            numer = numer / gcd;
            denom = denom / gcd;
        }

        if (denom < 0) {
            this.numer = numer * (-1);
            this.denom = denom * (-1);
        }
        else {
            this.numer = numer;
            this.denom = denom;
        }
    }

    public long numerator()
    { return this.numer; }

    public long denominator()
    { return this.denom; }

    // Сумма this и that
    public Rational plus(Rational b) {
        long sumNumer;
        long sumDenom;
        long tempNumer;
        long tempDenom;
        Rational sum;
        long aN = this.numer;
        long aD = this.denom;
        long bN = b.numer;
        long bD = b.denom;

        if (aD == bD) {
            sumNumer = aN + bN;
            sum = new Rational(sumNumer, aD);
        }
        else{
            tempNumer = aN * bD + bN * aD;
            long gcdDenom = gcd(aD, bD);

            if ( gcdDenom > 1) {
                tempDenom = aD * bD / gcdDenom;
                tempNumer = aN * tempDenom / aD + bN * tempDenom / bD;
            }
            else tempDenom = aD * bD;

            long gcdSum = gcd(tempNumer, tempDenom);

            if ( gcdSum > 1) {
                sumNumer = tempNumer / gcdSum;
                sumDenom = tempDenom / gcdSum;
            }
            else {
                sumNumer = tempNumer;
                sumDenom = tempDenom;
            }
            sum = new Rational(sumNumer, sumDenom);
        }
        return sum;
    }

    // Вычитание из this that
    public Rational minus(Rational b) {
        long subNumer;
        long subDenom;
        long tempNumer;
        long tempDenom;
        Rational sub;
        long aN = this.numer;
        long aD = this.denom;
        long bN = b.numer;
        long bD = b.denom;

        if (aD == bD) {
            subNumer = aN - bN;
            sub = new Rational(subNumer, aD);
        }
        else{
            tempNumer = aN * bD - bN * aD;
            long gcdDenom = gcd(aD, bD);

            if ( gcdDenom > 1) {
                tempDenom = aD * bD / gcdDenom;
                tempNumer = aN * tempDenom / aD + bN * tempDenom / bD;
            }
            else tempDenom = aD * bD;

            long gcdSub = gcd(tempNumer, tempDenom);

            if ( gcdSub > 1) {
                subNumer = tempNumer / gcdSub;
                subDenom = tempDenom / gcdSub;
            }
            else {
                subNumer = tempNumer;
                subDenom = tempDenom;
            }
            sub = new Rational(subNumer, subDenom);
        }
        return sub;
    }

    // Умножение this на that
    public Rational times(Rational b) {
        long timesNumer;
        long timesDenom;
        long tempNumer;
        long tempDenom;
        Rational times;

        tempNumer = this.numer * b.numer;
        tempDenom = this.denom * b.denom;

        long gcdTimes = gcd(tempNumer, tempDenom);

        if ( gcdTimes > 1 ) {
            timesNumer = tempNumer / gcdTimes;
            timesDenom = tempDenom / gcdTimes;
        }
        else {
            timesNumer = tempNumer;
            timesDenom = tempDenom;
        }
        times = new Rational(timesNumer, timesDenom);

        return times;
    }

    // Деление this на that
    public Rational divides(Rational b) {
        long divNumer;
        long divDenom;
        long tempNumer;
        long tempDenom;
        Rational div;

        tempNumer = this.numer * b.denom;
        tempDenom = this.denom * b.numer;

        long gcdDiv = gcd(tempNumer, tempDenom);

        if ( gcdDiv > 1 ) {
            divNumer = tempNumer / gcdDiv;
            divDenom = tempDenom / gcdDiv;
        }
        else {
            divNumer = tempNumer;
            divDenom = tempDenom;
        }
        div = new Rational(divNumer, divDenom);

        return div;
    }

    // Проверка на равенство this и that
    public boolean equals(Object x) {
        long gcdThis;
        long thisNumer;
        long thisDenom;
        long gcdThat;
        long thatNumer;
        long thatDenom;

        if (this == x) return true;
        if (x == null) return false;
        if (this.getClass() != x.getClass()) return false;

        Rational that = (Rational) x;
        long aN = this.numer;
        long aD = this.denom;
        long bN = that.numer;
        long bD = that.denom;

        gcdThis = gcd(aN, aD);

        if ( gcdThis > 1 ) {
            thisNumer = aN / gcdThis;
            thisDenom = aD / gcdThis;
        }
        else {
            thisNumer = aN;
            thisDenom = aD;
        }

        gcdThat = gcd(bN, bD);

        if ( gcdThat > 1 ) {
            thatNumer = bN / gcdThat;
            thatDenom = bD / gcdThat;
        }
        else {
            thatNumer = bN;
            thatDenom = bD;
        }

        return thisNumer == thatNumer && thisDenom == thatDenom;
    }

    // Строковое представление числа.
    // Если числитель - ноль, число - ноль.
    // Если знаменатель - один, число - числитель.
    public String toString () {
        if (numerator() == 0) return "0";
        if (denominator() == 1) return numerator() + "";
        return numerator() + "/" + denominator();
    }

    // Определение наибольшего общего делителя (НОД)
    public static long gcd(long p1, long q1) {
        long r;
        long p = Math.abs(p1);
        long q = Math.abs(q1);
        while (q != 0) {
            r = p % q;
            p = q;
            q = r;
        }
        return p;
    }
}