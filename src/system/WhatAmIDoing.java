package system;

import java.util.Random;

public class WhatAmIDoing
{

	public static void main(String[] args)
	{
		int errorCount = 43;
		int pathCount = 94;
		int offset = 50;
		int fluc = 10 + 1;

		for (int i = 0; i < errorCount; i++)
		{
			Random random = new Random();
			int expectedRatio = random.nextInt(fluc) + offset;
			System.out.print("Error" + (i > 8 ? "" : "0") + (i + 1) + ":\t");
			for (int j = 0; j < pathCount; j++)
			{
				System.out.print((random.nextInt(101) >= expectedRatio ? 0 : 1) + "\t");
			}
			System.out.println();
		}
	}
}