class Solution {
    public int maxSum(List<Integer> nums, int k) {
     int mod= 1000000007;
     int bits[]=new int[32];
     for(int num:nums)
     {
        for(int i=0;i<32;i++)
        {
            if((num&(1 << i))!=0)
            {
                bits[i]++;
            }
        }
     }
     long sum=0;
     for(int j=0;j<k;j++)
     {
        long num=0;
        for(int i=0;i<32;i++)
        {
            if(bits[i]>0)
            {
              num|=(1L<<i);
              bits[i]--;              
            }
        }
        sum=(sum+num*num)%mod;
     }
     return (int)sum;

    }
}