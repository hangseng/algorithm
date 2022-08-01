import java.util.ArrayList;
import java.util.List;

/**
 * 679. 24 点游戏 (回溯)
 * leetcode: https://leetcode.cn/problems/24-game/
 * @author: Ansen
 * @create: 2022-07-31 11:26
 **/
public class day22_731 {

    //回溯枚举 + 部分剪枝优化
    static final int TARGET = 24;
    //误差
    static final double EPSILON = 1e-6;
    //加、乘、减、除
    static final int ADD = 0, MULTIPLY = 1, SUBTRACT = 2, DIVIDE = 3;

    public boolean judgePoint24(int[] nums) {
        //4个数入list
        List<Double> list = new ArrayList<Double>();
        for (int num : nums) {
            list.add((double) num);
        }
        return solve(list);
    }

    public boolean solve(List<Double> list) {
        if (list.size() == 0) {
            return false;
        }
        //算完后只剩一个数，即结果，误差在可接受范围内判true
        if (list.size() == 1) {
            return Math.abs(list.get(0) - TARGET) < EPSILON;
        }
        int size = list.size();
        //list里挑出一个数
        for (int i = 0; i < size; i++) {
            //再挑一个，做运算
            for (int j = 0; j < size; j++) {
                //不能挑一样的
                if (i != j) {
                    //没被挑到的扔进list2
                    List<Double> list2 = new ArrayList<Double>();
                    for (int k = 0; k < size; k++) {
                        if (k != i && k != j) {
                            list2.add(list.get(k));
                        }
                    }
                    //四种运算挑一个，算完的结果加到list2里做第三个数
                    for (int k = 0; k < 4; k++) {
                        //k < 2是指，所挑运算为加或乘
                        //i > j是为了判定交换律的成立，如果i < j,说明是第一次做加或乘运算
                        //eg: i = 0, j = 1, k = 1 → 挑第一个和第二个数，两数相乘
                        //    i = 1, j = 0, k = 1 → 挑第二个和第一个数，两数相乘  →  无效的重复计算
                        if (k < 2 && i > j) {
                            continue;
                        }
                        //加
                        if (k == ADD) {
                            list2.add(list.get(i) + list.get(j));
                            //乘
                        } else if (k == MULTIPLY) {
                            list2.add(list.get(i) * list.get(j));
                            //减
                        } else if (k == SUBTRACT) {
                            list2.add(list.get(i) - list.get(j));
                            //除
                        } else if (k == DIVIDE) {
                            //如果除数等于0，直接判这次所挑选的运算为false，跳出此次循环
                            if (Math.abs(list.get(j)) < EPSILON) {
                                continue;
                            } else {
                                list2.add(list.get(i) / list.get(j));
                            }
                        }
                        //至此已经由4→3，进入下一层由3→2的过程，依次类推
                        if (solve(list2)) {
                            return true;
                        }
                        //没凑成24就把加到list2末尾的结果数删掉，考虑下种运算可能
                        list2.remove(list2.size() - 1);
                    }
                }
            }
        }
        return false;
    }
}
