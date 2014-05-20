package utils;

public class Isbn {
	public static boolean checkout13(String str) {
		str = str.trim();
		str = str.replace("-", "");
		if (str.length() != 13)
			return false;
		int sum = 0;
		for (int i = 0; i < 12; i++) {
			if (i % 2 == 0)
				sum += (str.charAt(i) - 48);
			else
				sum += (3 * (str.charAt(i) - 48));
		}
		sum %= 10;
		sum = 10 - sum;
		if (sum == 10)
			sum = 0;
		if (str.charAt(str.length() - 1) - 48 == sum)
			return true;
		else
			return false;
	}

	public static boolean checkout10(String str) {
		str = str.trim();
		str = str.replace("-", "");
		if (str.length() != 10)
			return false;
		int sum = 0;
		for (int i = 0; i < 9; i++)
			sum += ((10 - i) * (str.charAt(i) - 48));
		System.out.println(sum);
		sum %= 11;
		sum = 11 - sum;
		System.out.println(sum);
		String N;
		if (sum == 11)
			N = "0";
		else if (sum == 10)
			N = "X";
		else
			N = Integer.toString(sum);
		System.out.println(N);
		if (N.equalsIgnoreCase(Character.toString(str.charAt(str.length() - 1))))
			return true;
		else
			return false;
	}
}
