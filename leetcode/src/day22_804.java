import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * 滑动窗口
 *
 * 给定一个由正整数数组，请按以下规则处理，使得数组有序（递增或者递减，不一定要严格递增或严格递减）：
 *
 * 数组已经是有序的，则不做处理，返回数组中最小的元素值。
 * 如果数组不是有序的，
 * 若删除其中一个元素，使得剩余元素是有序的，返回需要删除的元素值；如果存在多个解，则选择删除元素值最小的。
 * 若无解，返回-1。
 *
 * 输入
 * 第一行一个整数，表示给定数组的长度 N，范围 [2, 200]。
 * 第二行N个整数，表示给定的数组，每个元素值的范围 [0, 200]。
 *
 * 输出
 * 一个整数，表示所求的返回值。
 *
 * @author: Ansen
 * @create: 2022-08-03 11:26
 **/
public class day22_804 {
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in, StandardCharsets.UTF_8.name());
        int length = cin.nextInt();
        int[] nums = new int[length];
        for (int i = 0; i < length; i++) {
            nums[i] = cin.nextInt();
        }
        cin.close();
        System.out.println(sortByDeleteOne(nums));
    }

    private static int sortByDeleteOne(int[] nums) {
        List<Integer> list = new ArrayList<>();
        // 本身就是升序或降序
        if (isIncrease(nums) || isReduced(nums)) {
            return nums[0] >= nums[nums.length - 1] ? nums[nums.length - 1] : nums[0];
        }
        // 不是升序也不是降序
        for (int i = 0; i < nums.length; i++) {// 每个都移除一下看能变成升序或者降序不
            List<Integer> reList = new ArrayList<>();
            for (int j = 0; j < nums.length; j++) {// 每次都从所有数中移除一个
                reList.add(nums[j]);
            }
            reList.remove(i);
            Object[] reArr = reList.toArray();
            int[] obArr = new int[reArr.length];
            for (int k = 0; k < reArr.length; k++) {
                obArr[k] = (int) reArr[k];
            }
            // 移除一个,剩下的判断是升序还是降序
            if (isIncrease(obArr) || isReduced(obArr)) {
                list.add(nums[i]);
            }
        }
        Collections.sort(list);
        return list.isEmpty() ? -1 : list.get(0);
    }



    public static boolean isIncrease(int[] nums) {
        boolean bool = true;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i + 1] < nums[i]) {
                return false;
            }
        }
        return bool;
    }

    public static boolean isReduced(int[] nums){
        boolean bool = true;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i + 1] > nums[i]) {
                return false;
            }
        }
        return bool;
    }

}
