import java.util.BitSet;

public class Main
{
    public static void main(String[] args) {
    Hamming hamming = new Hamming();
    BitSet output = hamming.HammingEncode("ab");
    hamming.printBitSet(output);
    // write your code here
    }
}
