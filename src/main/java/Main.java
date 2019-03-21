import java.util.BitSet;

public class Main
{
    public static void main(String[] args) {
    Hamming hamming = new Hamming();
    BitSet input = hamming.ConvertToBitset("a");
    BitSet output = hamming.HammingEncode("joep");
    hamming.printBitSet(output);
    // write your code here
    }
}
