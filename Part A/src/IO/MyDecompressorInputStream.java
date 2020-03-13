package IO;

import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

public class MyDecompressorInputStream extends InputStream {

    InputStream in;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        byte  num1 =(byte) in.read();
        byte num2 =(byte) in.read();
        int res =-1;
        if(num1!=-1){
            res = unite(num1, num2);
        }
        return res;
    }


    @Override
    public int read(byte[] b) throws IOException {
        Vector<Integer> code = new Vector<>();

        int temp = read();
        while(temp!=-1){
            code.add(temp);
            temp = read();
        }
        return decompressLZW (code, b);
    }

    private int decompressLZW(Vector<Integer> code, byte[] b) {
        int indexInput =0;
        int indexOutput = 0;

        //initialize dictionary
        Vector<Vector<Byte>> dictionary = new Vector<>();
        for (int i=-128; i<128; i++){
            Vector<Byte> v = new Vector<>();
            v.add((byte)i);
            dictionary.add(v);
        }

        int old, next;
        old = code.elementAt(indexInput);
        Vector<Byte> s;

        Byte c =0;
        b[indexOutput] = dictionary.elementAt(old).elementAt(0);
        indexInput++;
        indexOutput++;

        while(indexInput<code.size()){
            next = code.elementAt(indexInput);
            if(next>=dictionary.size()){
                s= new Vector<>();

                for(int i=0; i<dictionary.elementAt(old).size(); i++){
                    s.add(dictionary.elementAt(old).elementAt(i));
                }
                s.add(c);
            }
            else{
                s = new Vector<>();
                for(int i=0; i<dictionary.elementAt(next).size(); i++){
                    s.add(dictionary.elementAt(next).elementAt(i));
                }

            }
            for (int i=0; i<s.size();i++){
                b[indexOutput]= s.elementAt(i);
                indexOutput++;
            }
            c=s.elementAt(0);

            Vector<Byte> temp = new Vector<>();
            for(int i=0; i<dictionary.elementAt(old).size(); i++){
                temp.add(dictionary.elementAt(old).elementAt(i));
            }
            temp.add(c);
            dictionary.add(temp);
            old = next;
            indexInput++;
        }
        return b.length;
    }

    private int unite(int num1, int num2) {
        int[] binary1 = convertToBinary(num1,8);
        int[] binary2 = convertToBinary(num2,8);

        int[] binaryNum = new int[16];

        for (int i = 0; i < 8; i++) {
            binaryNum[i] = binary1[i];
            binaryNum[i + 8] = binary2[i];
        }
        return convertToInteger(binaryNum, 0, 15);
    }

    private int[] convertToBinary(int num, int size) {
        boolean negative = false;
        boolean done = false;
        int[] res = new int[size];
        if (num<0) {
            negative = true;
            num = num*(-1);
        }
        for (int i=size-1; i>=0; i--){
            if(Math.pow(2,i)>num){
                res[i] =0;
            }
            else {
                res[i]=1;
                num = num-(int)Math.pow(2,i);
            }
        }
        if(negative){
            for(int i=0; i<res.length; i++){
                if(res[i] ==0 )
                    res[i]=1;
                else
                    res[i]=0;
            }
            int index =  0;
            while (!done){
                if(res[index]==0){
                    done= true;
                    res[index] = 1;
                }
                else{
                    res[index] = 0;
                }
                index++;
            }
        }
        return res;
    }

    private int convertToInteger(int[] binaryNum, int start, int end) {
        int res=0;
        int index =0;
        for(int i=start;i<end; i++){
            res = res + (int)Math.pow(2,index) * binaryNum[i];
            index++;
        }
        res= res -(int)Math.pow(2,index) * binaryNum[end];
        return res;
    }

}
