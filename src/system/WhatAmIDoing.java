package system;

import java.util.Random;

public class WhatAmIDoing
{

	private static final boolean	isDef	= true;

	public static void main(String[] args)
	{
		int[] errorExpectRatios_2p = {
				// expected error ratios
				95, 90, 85,// 1-3. ��ȡM0��M1��M2��Ϣ��δ���÷���
				60, 55, 40,// 4-6. ��ȡM0��M1��M2��Ϣ����ô������
				70, 65, 60,// 7-9. ��ȡM0��M1��M2��Ϣ����÷��񴫵ݲ���������ƥ��
				40, 30, 20,// 10-12. ��ȡM0��M1��M2��Ϣ����÷��񴫵ݲ�����Χ����
				85,// 13. ��ȡM3��Ϣ��δ���÷���
				65,// 14. ��ȡM3��Ϣ����ô������
				65, 65,// 15-16. ��ȡM3��Ϣ����÷��񴫵ݲ���������ƥ��
				40, 30,// 17-18. ��ȡM3��Ϣ����÷��񴫵ݲ�����Χ����
				80,// 19. ��ȡM4��Ϣ��δ���÷���
				50,// 20. ��ȡM4��Ϣ����ô������
				30,// 21. ��ȡM4��Ϣ����÷��񴫵ݲ���������ƥ��
				20,// 22. ��ȡM4��Ϣ����÷��񴫵ݲ�����Χ����

				100,// checkInEntryNotice ��ȡ�ÿ���Ϣ�Ƿ�ɹ����ִ��� ������������
				95,// getBoardingPass ��ȡ�ÿ���Ϣ�Ƿ�ɹ����ִ��� ������������
				60,// boardingPassCompare ���������жϳ��ִ���>1��Ϊ<1 ��ϵ����������
				60,// boardingPassCompare �ж��ÿ���Ϣ�Ƿ��뺽����Ϣһ�´��� ��ϵ����������
				20,// boardingPassCompare �ÿ���Ϣ�ȶ�ʱ��&&��������Ϊ|| ��������������
				15,// boardingPassCompare �ÿ���Ϣ�ȶ�ʱ���������ʽ������� �������Ŵ���
				95,// luminanceAdjust ��ȡ�ÿ���Ϣ�Ƿ�ɹ����ִ��� ������������
				86,// luminanceAdjust ���������жϳ��ִ���==3��Ϊ==4 �������ʽ����
				90,// luminanceAdjust �������ȷ����������ȵ��ڷ����෴ ��ϵ����������
				80,// luminanceAdjust ����Ȩ��ʱȨ��ֵ���� �������ʽ����
				80,// advertisementSelect ��ȡ�ÿ���Ϣ�Ƿ�ɹ����ִ��� ������������
				80,// advertisementSelect ��ȡ�����Ϣ�Ƿ�ɹ����ִ��� ������������
				80,// shoppingRecommendationByAge ��ȡ�ÿ���Ϣ�Ƿ�ɹ����ִ��� ������������
				80,// shoppingRecommendationByInterest ��ȡ�ÿ���Ϣ�Ƿ�ɹ����ִ��� ������������
				50,// boardingPassCompare ���������жϳ��ִ���>1��Ϊ<1 ��ϵ����������
				50 // boardingPassCompare �ж��ÿ���Ϣ�Ƿ��뺽����Ϣһ�´��� ��ϵ����������

		};

		int[] errorExpectRatios_1p = {
				// expected error ratios
				90, 85, 80,// 1-3. ��ȡM0��M1��M2��Ϣ��δ���÷���
				60, 55, 40,// 4-6. ��ȡM0��M1��M2��Ϣ����ô������
				65, 60, 55,// 7-9. ��ȡM0��M1��M2��Ϣ����÷��񴫵ݲ���������ƥ��
				50, 35, 30,// 10-12. ��ȡM0��M1��M2��Ϣ����÷��񴫵ݲ�����Χ����
				85,// 13. ��ȡM3��Ϣ��δ���÷���
				65,// 14. ��ȡM3��Ϣ����ô������
				60, 60,// 15-16. ��ȡM3��Ϣ����÷��񴫵ݲ���������ƥ��
				40, 30,// 17-18. ��ȡM3��Ϣ����÷��񴫵ݲ�����Χ����
				80,// 19. ��ȡM4��Ϣ��δ���÷���
				55,// 20. ��ȡM4��Ϣ����ô������
				30,// 21. ��ȡM4��Ϣ����÷��񴫵ݲ���������ƥ��
				20,// 22. ��ȡM4��Ϣ����÷��񴫵ݲ�����Χ����

				99,// checkInEntryNotice ��ȡ�ÿ���Ϣ�Ƿ�ɹ����ִ��� ������������
				90,// getBoardingPass ��ȡ�ÿ���Ϣ�Ƿ�ɹ����ִ��� ������������
				50,// boardingPassCompare ���������жϳ��ִ���>1��Ϊ<1 ��ϵ����������
				50,// boardingPassCompare �ж��ÿ���Ϣ�Ƿ��뺽����Ϣһ�´��� ��ϵ����������
				30,// boardingPassCompare �ÿ���Ϣ�ȶ�ʱ��&&��������Ϊ|| ��������������
				20,// boardingPassCompare �ÿ���Ϣ�ȶ�ʱ���������ʽ������� �������Ŵ���
				90,// luminanceAdjust ��ȡ�ÿ���Ϣ�Ƿ�ɹ����ִ��� ������������
				90,// luminanceAdjust ���������жϳ��ִ���==3��Ϊ==4 �������ʽ����
				90,// luminanceAdjust �������ȷ����������ȵ��ڷ����෴ ��ϵ����������
				80,// luminanceAdjust ����Ȩ��ʱȨ��ֵ���� �������ʽ����
				80,// advertisementSelect ��ȡ�ÿ���Ϣ�Ƿ�ɹ����ִ��� ������������
				80,// advertisementSelect ��ȡ�����Ϣ�Ƿ�ɹ����ִ��� ������������
				80,// shoppingRecommendationByAge ��ȡ�ÿ���Ϣ�Ƿ�ɹ����ִ��� ������������
				80,// shoppingRecommendationByInterest ��ȡ�ÿ���Ϣ�Ƿ�ɹ����ִ��� ������������
				50,// boardingPassCompare ���������жϳ��ִ���>1��Ϊ<1 ��ϵ����������
				50 // boardingPassCompare �ж��ÿ���Ϣ�Ƿ��뺽����Ϣһ�´��� ��ϵ����������

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