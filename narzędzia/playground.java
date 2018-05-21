package narzędzia;


import java.util.Arrays;
import static duralex.DuraLex.outL;

public class playground {
    public static void main(String...args){
        outL(Arrays.toString(deleteNth( new int[] { 1, 2, 2, 3, 3, 3, 4, 4, 4 ,4,5, 5, 5, 5, 5, 6,7, 7, 8, 8, 8, 9, 9, 9, 9 }, 2 )));
        outL(Arrays.toString(deleteNth( new int[] { 1, 1, 3, 3, 7, 2, 2, 2, 2 ,1,1, 1, 3, 3, 7, 2, 2, 2, 1, 3, 3, 7, 2, 2, 2 }, 2 )));
        outL(Arrays.toString(deleteNth( new int[] { 20, 37, 20, 21,  1  , 1,  3  , 3, 7,  2  , 2}, 1)));
    }
    
    
    public static int[] deleteNth(int[] elements, int maxOcurrences) {
outL("max="+maxOcurrences+":\n"+Arrays.toString(elements));
//DECLARATIONS
        int found,left,l
        ,count[]
        ,right
        ,empty
        ;//,els[]=Arrays.copyOf(elements, elements.length);
//INITIALIZATION
        count=new int [l=elements.length];
        count[0]=(found=1);
        right=1;
        left=0;
        empty=-1;
//MOTOR
        for(;right<l;left=0,right++){//every index
            while(elements[right]<1)right++;//eliminate empty cells
            while ( left<right && elements[left]!=elements[right] )  left++;//compare to left
            if (left==right) { count[left]=1;found=right;} //found new value 
            else if (left<right){
                if(count[left]<maxOcurrences){count[left]++; found=left;}//value keeps maxOccurences
                else elements[right]=-1;
            }
        }
        int c=0,zero=0;
        for(int e:elements)if(e>0)count[c++]=e;else zero++;
//SUMMARY
        outL(Arrays.toString(elements));
        return Arrays.copyOf(count, count.length-zero);//elements;
    }
}