import java.util.BitSet;

public class Main
{
    public static void main(String[] args) {
    Hamming hamming = new Hamming();
//    BitSet input = hamming.ConvertToBitset("a");
//    hamming.printBitSet(input,7);
//    BitSet output = hamming.Hamming7(input);
//    hamming.printBitSet(output,11);
//    output = hamming.addNoise(output);
//    hamming.printBitSet(output,11);
//    String test = hamming.hammingDecode(output);
//    System.out.println(test);
        System.out.println("Output: "+hamming.hammingEncodewithNoiseString("test"));

    // write your code here
    }
}
