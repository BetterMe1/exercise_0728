package exercise.exercise_0726;

/**
 * 合唱团
 */
/*
动态规划问题：

（1）状态：我们需要维护两个数组，因为能力值有负数，负负会得正。
dpm[i][j]表示选中了i个人，以第j个人结尾的能力最大乘积
dpn[i][j]表示选中了i个人，以第j个人结尾的能力最小乘积
（2）状态递推：
当选中了i-1个人，以j-1结尾，再选中第j个人时：
最大乘积为dpm[i][j] = Math.max(dpm[i-1][j-1]*num[j],dpn[i-1][j-1]*num[j]);
最小乘积为dpn[i][j] = Math.min(dpm[i-1][j-1]*num[j],dpn[i-1][j-1]*num[j]);
但由于前i-1个人的最大乘积和最小乘积不一定以第j-1个人结尾，所以我们从允许与j相隔最远的人Math.max(1,j-D)开始遍历直到j-1,
不断更新dpm[i][j]，dpn[i][j],最后得出的dpm[i][j]，dpn[i][j]便是选中i个人，以j结尾的最大、最小乘积。
（3）初始化：dpm[1][j] = dpn[1][j] = num[j];   dpm[i][1] = dpn[i][1] = num[1];
（4）返回值：dpm[K][j],dpn[K][j]的最大值
 */
/*
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] num = new int[n+1];
        for(int i=1; i<=n; i++){
            num[i] = in.nextInt();
        }
        int K = in.nextInt();
        int D = in.nextInt();
        long[][] dpm = new long[K+1][n+1];//dpm[i][j]表示选中了i个人，以第j个人结尾的能力最大乘积
        long[][] dpn = new long[K+1][n+1];//dpn[i][j]表示选中了i个人，以第j个人结尾的能力最小乘积
        for(int j=1; j<n+1; j++){//初始
            dpm[1][j] = num[j];
            dpn[1][j] = num[j];
        }
        for(int i=1; i<K+1; i++){//初始
            dpm[i][1] = num[1];
            dpn[i][1] = num[1];
        }
        for(int i=2;i<K+1;i++){
            for(int j=2;j<n+1;j++){
                for(int k=Math.max(1,j-D);k<j;k++){
                    dpm[i][j] = Math.max(dpm[i][j],Math.max(dpm[i-1][k]*num[j],dpn[i-1][k]*num[j]));
                    dpn[i][j] = Math.min(dpn[i][j],Math.min(dpm[i-1][k]*num[j],dpn[i-1][k]*num[j]));
                }
            }
        }
        long max = Math.max(dpm[K][1],dpn[K][1]);
        for(int j=2;j<n+1;j++){
            max = Math.max(Math.max(dpm[K][j],dpn[K][j]),max);
        }
        System.out.println(max);
    }
}
*/


import java.util.*;

public class Main {
    static class People{
        public int weight;
        public int height;
        public People(int weight, int height) {
            this.weight = weight;
            this.height = height;
        }
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()){
            int N = in.nextInt();
            People[] people = new People[N];//编号从0开始
            for(int i=0; i<N;i++){
                int index = in.nextInt();
                people[index-1] = new People(in.nextInt(),in.nextInt());
            }
            //体重相同时，只有身高也相同才可以站在自己肩上，比自己矮是不能站在自己肩上的。
            Arrays.sort(people, new Comparator<People>() {
                @Override
                public int compare(People o1, People o2) {
                    int result = Integer.compare(o1.height,o2.height);
                    if(result !=0){
                        return result;
                    }
                    return Integer.compare(o1.weight,o2.weight);
                }
            });
            int[] dp = new int[N];//dp[i]表示前i个人中以第i个人结尾（包括第i个人)，可以叠起来的最高罗汉塔
            int max = Integer.MIN_VALUE;
            for(int i=0; i<N;i++){
                dp[i] = 1;//最小值为1
                for(int j=0;j<i; j++){
                    if(people[i].weight > people[j].weight ||
                            (people[i].weight == people[j].weight && people[i].height== people[j].height)){
                        dp[i] = Math.max(dp[i],dp[j]+1);
                    }
                }
                max = Math.max(max,dp[i]);
            }
            System.out.println(max);
        }
    }
}