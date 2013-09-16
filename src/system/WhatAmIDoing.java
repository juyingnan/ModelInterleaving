package system;

import java.util.Random;

public class WhatAmIDoing
{

	private static final boolean	isDef	= true;

	public static void main(String[] args)
	{
		int[] errorExpectRatios_2p = {
				// expected error ratios
				95, 90, 85,// 1-3. 获取M0、M1、M2信息后未调用服务
				60, 55, 40,// 4-6. 获取M0、M1、M2信息后调用错误服务
				70, 65, 60,// 7-9. 获取M0、M1、M2信息后调用服务传递参数个数不匹配
				40, 30, 20,// 10-12. 获取M0、M1、M2信息后调用服务传递参数范围有误
				85,// 13. 获取M3信息后未调用服务
				65,// 14. 获取M3信息后调用错误服务
				65, 65,// 15-16. 获取M3信息后调用服务传递参数个数不匹配
				40, 30,// 17-18. 获取M3信息后调用服务传递参数范围有误
				80,// 19. 获取M4信息后未调用服务
				50,// 20. 获取M4信息后调用错误服务
				30,// 21. 获取M4信息后调用服务传递参数个数不匹配
				20,// 22. 获取M4信息后调用服务传递参数范围有误

				100,// checkInEntryNotice 获取旅客信息是否成功出现错误 布尔变量错误
				95,// getBoardingPass 获取旅客信息是否成功出现错误 布尔变量错误
				60,// boardingPassCompare 参数数量判断出现错误，>1误为<1 关系操作符错误
				60,// boardingPassCompare 判断旅客信息是否与航班信息一致错误 关系操作符错误
				20,// boardingPassCompare 旅客信息比对时，&&操作符误为|| 布尔操作符错误
				15,// boardingPassCompare 旅客信息比对时，布尔表达式误加括号 布尔括号错误
				95,// luminanceAdjust 获取旅客信息是否成功出现错误 布尔变量错误
				86,// luminanceAdjust 参数数量判断出现错误，==3误为==4 算数表达式错误
				90,// luminanceAdjust 计算亮度发生错误，亮度调节方向相反 关系操作符错误
				80,// luminanceAdjust 计算权重时权重值错误 算数表达式错误
				80,// advertisementSelect 获取旅客信息是否成功出现错误 布尔变量错误
				80,// advertisementSelect 获取广告信息是否成功出现错误 布尔变量错误
				80,// shoppingRecommendationByAge 获取旅客信息是否成功出现错误 布尔变量错误
				80,// shoppingRecommendationByInterest 获取旅客信息是否成功出现错误 布尔变量错误
				50,// boardingPassCompare 参数数量判断出现错误，>1误为<1 关系操作符错误
				50 // boardingPassCompare 判断旅客信息是否与航班信息一致错误 关系操作符错误

		};

		int[] errorExpectRatios_1p = {
				// expected error ratios
				90, 85, 80,// 1-3. 获取M0、M1、M2信息后未调用服务
				60, 55, 40,// 4-6. 获取M0、M1、M2信息后调用错误服务
				65, 60, 55,// 7-9. 获取M0、M1、M2信息后调用服务传递参数个数不匹配
				50, 35, 30,// 10-12. 获取M0、M1、M2信息后调用服务传递参数范围有误
				85,// 13. 获取M3信息后未调用服务
				65,// 14. 获取M3信息后调用错误服务
				60, 60,// 15-16. 获取M3信息后调用服务传递参数个数不匹配
				40, 30,// 17-18. 获取M3信息后调用服务传递参数范围有误
				80,// 19. 获取M4信息后未调用服务
				55,// 20. 获取M4信息后调用错误服务
				30,// 21. 获取M4信息后调用服务传递参数个数不匹配
				20,// 22. 获取M4信息后调用服务传递参数范围有误

				99,// checkInEntryNotice 获取旅客信息是否成功出现错误 布尔变量错误
				90,// getBoardingPass 获取旅客信息是否成功出现错误 布尔变量错误
				50,// boardingPassCompare 参数数量判断出现错误，>1误为<1 关系操作符错误
				50,// boardingPassCompare 判断旅客信息是否与航班信息一致错误 关系操作符错误
				30,// boardingPassCompare 旅客信息比对时，&&操作符误为|| 布尔操作符错误
				20,// boardingPassCompare 旅客信息比对时，布尔表达式误加括号 布尔括号错误
				90,// luminanceAdjust 获取旅客信息是否成功出现错误 布尔变量错误
				90,// luminanceAdjust 参数数量判断出现错误，==3误为==4 算数表达式错误
				90,// luminanceAdjust 计算亮度发生错误，亮度调节方向相反 关系操作符错误
				80,// luminanceAdjust 计算权重时权重值错误 算数表达式错误
				80,// advertisementSelect 获取旅客信息是否成功出现错误 布尔变量错误
				80,// advertisementSelect 获取广告信息是否成功出现错误 布尔变量错误
				80,// shoppingRecommendationByAge 获取旅客信息是否成功出现错误 布尔变量错误
				80,// shoppingRecommendationByInterest 获取旅客信息是否成功出现错误 布尔变量错误
				50,// boardingPassCompare 参数数量判断出现错误，>1误为<1 关系操作符错误
				50 // boardingPassCompare 判断旅客信息是否与航班信息一致错误 关系操作符错误

		};

		int errorCount = errorExpectRatios_2p.length;
		int pathCount = 270;
		int offset = 20;
		// int fluc = 10 + 1;

		for (int i = 0; i < errorCount; i++)
		{
			Random random = new Random();
			int expectedRatio = errorExpectRatios_1p[i];
			if (isDef)
			{
				expectedRatio *= (90 - random.nextInt(offset));
				expectedRatio /= 100;
			}
			System.out.print("Error" + (i > 8 ? "" : "0") + (i + 1) + ":");
			for (int j = 0; j < pathCount; j++)
			{
				System.out.print("\t" + (random.nextInt(100) >= expectedRatio ? 0 : 1));
			}
			System.out.println();
		}
	}
}