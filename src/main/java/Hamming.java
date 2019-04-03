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

    private BitSet ConcatBitSet(BitSet input, BitSet toConcat)
    {
        int StartIndex = input.length();
        int EndIndex = toConcat.length();
        BitSet output = new BitSet(StartIndex+EndIndex);
        for(int i = 0;i>StartIndex;i++)
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

        for(int current = 0; current<7;current++)
        {
            boolean bit  = data7.get(current);
            switch(current+1)
            {
                case 7:
                    parity1.set(1,bit);
                    parity2.set(1,bit);
                    break;
                case 6:
                    parity1.set(2,bit);
                    parity3.set(1,bit);
                    break;
                case 5:
                    parity2.set(2,bit);
                    parity3.set(2,bit);
                    break;
                case 4:
                    parity1.set(3,bit);
                    parity2.set(3,bit);
                    parity3.set(3,bit);
                    break;
                case 3:
                    parity1.set(4,bit);
                    parity4.set(1,bit);
                    break;
                case 2:
                    parity2.set(4,bit);
                    parity4.set(2,bit);
                    break;
                case 1:
                    parity1.set(5,bit);
                    parity2.set(5,bit);
                    parity4.set(3,bit);
                    break;
            }
        }
        boolean p1 = parity1.cardinality()%2 != 0;
        boolean p2 = parity2.cardinality()%2 != 0;
        boolean p3 = parity3.cardinality()%2 != 0;
        boolean p4 = parity4.cardinality()%2 != 0;

        BitSet output = new BitSet();

        output.set(10,p1);
        output.set(9,p2);
        output.set(8,data7.get(6));
        output.set(7,p3);
        output.set(6,data7.get(5));
        output.set(5,data7.get(4));
        output.set(4,data7.get(3));
        output.set(3,p4);
        output.set(2,data7.get(2));
        output.set(1,data7.get(1));
        output.set(0,data7.get(0));
        return output;



    }

    public void printBitSet(BitSet set,int length)
    {
        StringBuilder builder = new StringBuilder();
        for(int i = length-1;i>=0;i--)
        {
            if(set.get(i))
            {
                builder.append(1);
            }
            else
            {
                builder.append(0);
            }
        }
        System.out.println(builder.toString());
    }

    public String hammingDecode(BitSet input)
    {
        String output = "";
        printBitSet(input,11);
        BitSet paritybits = new BitSet(4);
        paritybits.set(3,errorCheck(input,1));
        paritybits.set(2,errorCheck(input,2));
        paritybits.set(1,errorCheck(input,3));
        paritybits.set(0,errorCheck(input,4));
        if(!paritybits.isEmpty())
        {
            if(11-(int)convertToLong(paritybits)<0)
            {
                return "Error";
            }
            else
            {
                input.flip(11-(int)convertToLong(paritybits));
            }
        }
        else
        {
            System.out.println("Goed");
            return new String(getData(input).toByteArray());
        }
        return new String(getData(input).toByteArray());
    }

    public long convertToLong(BitSet bits) {
        int output = 0;
        if(bits.get(3)){
            output += 1;
        }
        if(bits.get(2)){
            output += 2;
        }
        if(bits.get(1)){
            output += 4;
        }
        if(bits.get(0)){
            output += 8;
        }
        return output;
    }
    public Boolean errorCheck(BitSet input,int paritybit)
    {
        switch(paritybit)
        {
            case 1:
                BitSet checkSet = new BitSet(5);
                int index = 0;
                for(int i = 0;i<11;i++)
                {
                    if(i==0|i==2|i==4|i==6|i==8)
                    {
                        checkSet.set(index,input.get(i));
                        index++;
                    }
                }
                return !((checkSet.cardinality()%2!=0) == input.get(10));
            case 2:
                checkSet = new BitSet(5);
                index = 0;
                for(int i = 0;i<11;i++)
                {
                    if(i == 0| i==1|i==4| i==5|i==8)
                    {
                        checkSet.set(index,input.get(i));
                        index++;
                    }
                }
                return !((checkSet.cardinality()%2!=0) == input.get(9));
            case 3:
                checkSet = new BitSet(5);
                index = 0;
                for(int i = 0;i<11;i++)
                {
                    if( i ==4 | i==5|i==6)
                    {
                        checkSet.set(index,input.get(i));
                        index++;
                    }
                }
                return !((checkSet.cardinality()%2 != 0) == input.get(7));
            case 4:
                checkSet = new BitSet(5);
                index = 0;
                for(int i = 0;i<11;i++)
                {
                    if( i ==0 | i==1|i==2)
                    {
                        checkSet.set(index,input.get(i));
                        index++;
                    }
                }
                return !((checkSet.cardinality()%2!=0) == input.get(3));
            default:
                return false;
        }
    }
    public Boolean twoErrorCheck(BitSet input)
    {
        BitSet newSet = getData(input);
        BitSet toCheck = Hamming7(newSet);
        for(int i = 0; i<11;i++)
        {
            if(toCheck.get(i)!=input.get(i))
            {
                return true;
            }
        }
        return false;
    }

    public BitSet getData (BitSet input)
    {
        BitSet newSet = new BitSet(7);
        int currentbit = 0;
        for(int i = 0;i<11;i++)
        {
            if(i!=3 & i!=7 & i!=9&i!=10)
            {
                newSet.set(currentbit,input.get(i));
                currentbit++;
            }
        }
        return newSet;
    }

    public BitSet addNoise(BitSet input)
    {
        for(int i=0;i<11;i++)
        {
            if(Math.random()>=0.95)
            {
                input.flip(i);
            }

        }
        return input;
//        input.flip(0);
//        return input;
    }

    public String hammingEncodewithNoiseString(String string)
    {
        StringBuilder output = new StringBuilder();
        for(char c:string.toCharArray())
        {
            System.out.println("Current character to encode: "+c);
            BitSet original = ConvertToBitset(Character.toString(c));
            BitSet encoded = Hamming7(original);
            printBitSet(encoded,11);
            encoded = addNoise(encoded);
            String decoded = hammingDecode(encoded);
            if(decoded != "Error")
            {
                output.append(decoded);
            }
            else
            {
                return "Error in decoding";
            }

        }
        return output.toString();
    }

}
