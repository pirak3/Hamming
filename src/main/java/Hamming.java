import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import com.google.gson.*;

public class Hamming
{
    BitSet data = new BitSet();
    public BitSet ConvertToBitset(String input)
    {
         byte[] bytes = input.getBytes(StandardCharsets.US_ASCII);
         data = BitSet.valueOf(bytes);
         return data;
    }

    public BitSet HammingEncode(String inputstring)
    {
        BitSet input = ConvertToBitset(inputstring);
        BitSet output = new BitSet();
        printBitSet(input);

        for(int i = 0; i<input.length(); i += 8)
        {
            System.out.println("start index: "+ i);
            BitSet temp = input.get(i,i+7);
            temp = Hamming7(temp);
            printBitSet(temp);
            output = ConcatBitSet(output,temp);
        }
        return output;
    }

    private BitSet ConcatBitSet(BitSet input, BitSet toConcat)
    {
        int StartIndex = input.length();
        int EndIndex = toConcat.length();
        System.out.println(StartIndex);
        BitSet output = new BitSet(StartIndex+EndIndex);
        for(int i = 0;i<StartIndex;i++)
        {
            output.set(i,input.get(i));
        }
        for(int i = 0;i<EndIndex;i++)
        {
            output.set(i+StartIndex,toConcat.get(i));
        }
        return output;
    }

    public BitSet Hamming7(BitSet data7)
    {
        BitSet parity1 = new BitSet();
        BitSet parity2 = new BitSet();
        BitSet parity3 = new BitSet();
        BitSet parity4 = new BitSet();

        for(int current = 0; current<data7.length();current++)
        {
            boolean bit  = data7.get(current);
            switch(current+1)
            {
                case 1:
                    parity1.set(1,bit);
                    parity2.set(1,bit);
                    break;
                case 2:
                    parity1.set(2,bit);
                    parity3.set(1,bit);
                    break;
                case 3:
                    parity2.set(2,bit);
                    parity3.set(2,bit);
                    break;
                case 4:
                    parity1.set(3,bit);
                    parity2.set(3,bit);
                    parity3.set(3,bit);
                    break;
                case 5:
                    parity1.set(4,bit);
                    parity4.set(1,bit);
                    break;
                case 6:
                    parity2.set(4,bit);
                    parity4.set(2,bit);
                    break;
                case 7:
                    parity1.set(5,bit);
                    parity2.set(5,bit);
                    parity4.set(3,bit);
                    break;
            }
        }
        boolean p1 = parity1.cardinality()%2 == 0;
        boolean p2 = parity2.cardinality()%2 == 0;
        boolean p3 = parity3.cardinality()%2 == 0;
        boolean p4 = parity4.cardinality()%2 == 0;

        BitSet output = new BitSet();

        output.set(0,p1);
        output.set(1,p2);
        output.set(2,data7.get(0));
        output.set(3,p3);
        output.set(4,data7.get(1));
        output.set(5,data7.get(2));
        output.set(6,data7.get(3));
        output.set(7,p4);
        output.set(8,data7.get(4));
        output.set(9,data7.get(5));
        output.set(10,data7.get(6));

        return output;




    }

    public void printBitSet(BitSet set)
    {
        Gson gson = new Gson();
        System.out.println("current bits: "+ gson.toJson(set));
    }
}
