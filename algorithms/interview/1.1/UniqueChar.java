
/***
 * 1.1　实现一个算法，确定一个字符串的所有字符是否全都不同。假使不允许使用额外的数据结构，又该如何处理？（第46页）
 *
 */

public class UniqueChar {

    public static void main(String[] args) {
        if (isUniqueChars2(args[0])) {
            System.out.println("没有重复的字母");
        } else {
            System.out.println("存在重复的字母");
        }
    }

    public static boolean isUniqueChars2(String str) {
        if (str.length() > 256) return false;

        boolean[] char_set = new boolean[256];
        for (int i = 0; i < str.length(); i++) {
            int val = str.charAt(i);
            if (char_set[val]) {
                return false;
            }
            char_set[val] = true;
        }
        return true;
    }
}
