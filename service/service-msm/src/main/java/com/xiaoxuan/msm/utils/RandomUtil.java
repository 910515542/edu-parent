package com.xiaoxuan.msm.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * 获取随机数
 * 
 * @author qianyi
 *
 */
public class RandomUtil {

//	public static void main(String args[]){
//		ArrayList<Integer> list = new ArrayList<>();
//		list.add(11);
//		list.add(12);
//		list.add(13);
//		list.add(14);
//		list.add(15);
//		list.add(16);
//		list.add(17);
//		list.add(18);
//		System.out.println(getRandom(list, 5));
//	}

	private static final Random random = new Random();

	private static final DecimalFormat fourdf = new DecimalFormat("0000");

	private static final DecimalFormat sixdf = new DecimalFormat("000000");

	public static String getFourBitRandom() {
		return fourdf.format(random.nextInt(10000));
	}

	public static String getSixBitRandom() {
		return sixdf.format(random.nextInt(1000000));
	}

	/**
	 * 给定数组，抽取n个数据
	 * @param list
	 * @param n
	 * @return
	 */
	public static ArrayList getRandom(List list, int n) {

		Random random = new Random();

		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();

		// 生成随机数字并存入HashMap
		for (int i = 0; i < list.size(); i++) {

			int number = random.nextInt(100) + 1;
//			System.out.print(number + "\t");
			hashMap.put(number, i);
		}
		System.out.println();
		// 从HashMap导入数组
		Object[] robjs = hashMap.values().toArray();
//		for(Object ob:robjs){
//			System.out.print((int)ob + " ");
//		}
//		System.out.println();
		ArrayList r = new ArrayList();

		// 遍历数组并打印数据
		for (int i = 0; i < n; i++) {
			r.add(list.get((int) robjs[i]));
//			System.out.print(list.get((int) robjs[i]) + "\t");
		}
//		System.out.print("\n");
		return r;
	}
}
