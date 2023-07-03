package practice;

import java.util.Random;

public class GetRandomNumber {

	public static void main(String[] args) {
		Random random = new Random();
		int num = random.nextInt(3000);
        System.out.println(num);
	}

}
