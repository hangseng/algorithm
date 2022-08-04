/**
 * 滑动窗口
 *
 * 有一套系统需升级，为减小系统升级期间的影响，需根据系统过去一段时间内的每小时平均访问数据，来预测最佳升级时间窗。
 * 现给定长度为168(7*24)的整数数组，表示一个周期(假设从周一00:00到周日24:00)的每小时历史数据，最佳升级时间窗选择规则如下：
 *
 * 时间窗内累计用户访问量必须小于等于给定的容忍值。
 * 时间窗必须是连续的x个小时，最大的x即为最佳升级时间窗，且不超过7*24。
 * 时间窗允许跨周期，例如当前周期的第167小时到下一周期的第166小时，是一个长度为168的时间窗。
 * 请计算最佳升级时间窗，并返回其开始时间和结束时间的数组下标。 如果存在多个最佳升级时间窗时，返回开始时间下标最小的一个。
 *
 * 输入
 * 第一行为整数 n ，表示给定的升级影响的容忍值，取值范围：[0, 2^31)。
 * 第二行为7*24个整数，表示一个周期（7*24）的每个小时用户访问量，每个值的范围：[0, 2^31)。
 *
 * 输出
 * 两个整数，分别表示所计算出的最佳升级时间窗的开始时间下标(包含)和结束时间下标(包含)，不存在时返回-1 -1。
 *
 * @author: Ansen
 * @create: 2022-08-03 11:26
 **/
public class day22_803 {
    private static int[] getBestTimeWindow(int[] pvByHourWeekly, int pvErrorTolerance) {
        int[] weeklyDouble = new int[pvByHourWeekly.length * 2]; //跨周期数组。
        for (int i = 0; i < pvByHourWeekly.length; i++) {
            weeklyDouble[i] = pvByHourWeekly[i];
            weeklyDouble[i +  pvByHourWeekly.length] = pvByHourWeekly[i];
        }
        int resLeft = -1;
        int resRight = -1;
        int left = 0;
        int right = 0;
        int maxLen = 0;
        long tolerance = 0; // long，防止累加时越界
        while (right < weeklyDouble.length) {
            tolerance += weeklyDouble[right];
            right++;
            while (tolerance > pvErrorTolerance) {
                tolerance -= weeklyDouble[left];
                left++;
            }
            if (right - left > maxLen) {
                maxLen = right - left;
                resLeft = left;
                resRight = right - 1; //!
            }
        }
        return new int[]{resLeft, resRight % 168};
    }
}
