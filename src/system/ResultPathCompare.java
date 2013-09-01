package system;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import model.service.invokeUtil.ServiceInvokeUtil;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.airport.middleware.Bigraph;

public class ResultPathCompare
{
	ServiceInvokeUtil	serviceUtil	= new ServiceInvokeUtil();
	String				url			= "http://localhost:8080/AirportInterleavingServices/services/";
	String				namespace	= "http://interleaving";
	static Bigraph		big			= new Bigraph();

	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		ResultPathCompare RPC = new ResultPathCompare();
		String fileName = "D:\\Airport_Result.xlsx";
		String sheetName = "Airport";
		ArrayList<String> originalPaths = RPC.getPathStrings(fileName, sheetName, 0);

		String[] sensor = { "M0", "M1", "M2", "M3", "M4" };
		sensor[0] = "Shop";
		sensor[1] = "Light1";
		sensor[2] = "Light2";
		sensor[3] = "BillBoard";
		sensor[4] = "Light3";

		String[] tempStrings1 = originalPaths.get(0).split("->");
		String[] newPath = new String[tempStrings1.length * 6];
		int[] tempInt1 = new int[100];
		int[] tempInt2 = new int[100];
		int[] tempInt3 = new int[100];
		int[] tempInt4 = new int[100];
		int[] tempInt5 = new int[100];

		int point1 = 0;
		int point2 = 0;
		int point3 = 0;
		int point4 = 0;
		int point5 = 0;

		BufferedReader strin = new BufferedReader(new InputStreamReader(System.in));
		String result = big.getInfo("get in airport");
		String info[] = result.split("&");
		String id = info[0];
		String tele = info[2];
		String path = "";
		ServiceInvokeUtil serviceUtil = new ServiceInvokeUtil();
		String url = "http://localhost:8080/AirportInterleavingServices/services/";
		String namespace = "http://interleaving";
		int index = 0;
		String lastM0 = "M00";
		String lastM1 = "M10";
		String lastM2 = "M20";
		String lastM3 = "M30";
		String lastM4 = "M40";

		for (int i = 0; i < tempStrings1.length; i++)
		{
			String[] tempStrings2 = tempStrings1[i].split(",");
			for (String string : tempStrings2)
			{
				if (string.startsWith("M0"))
				{
					tempInt1[point1] = Integer.parseInt(string.substring(2));
					point1++;
				}
				if (string.startsWith("M1"))
				{
					tempInt2[point2] = Integer.parseInt(string.substring(2));
					point2++;
				}
				if (string.startsWith("M2"))
				{
					tempInt3[point3] = Integer.parseInt(string.substring(2));
					point3++;
				}
				if (string.startsWith("M3"))
				{
					tempInt4[point4] = Integer.parseInt(string.substring(2));
					point4++;
				}
				if (string.startsWith("M4"))
				{
					tempInt5[point5] = Integer.parseInt(string.substring(2));
					point5++;
				}
			}
		}

		for (int i = 0; i < tempStrings1.length; i++)
		{
			String[] tempStrings2 = tempStrings1[i].split(",");

			// print xuxu[0]
			newPath[index++] = "M0" + tempInt1[i] + ",M1" + tempInt2[i] + ",M2" + tempInt3[i] + ",M3" + tempInt4[i] + ",M4" + tempInt5[i];
			if (tempStrings2[1].contains("M00") && !lastM0.equals("M00"))
			{
				// 被测系统MO变1的输出

				lastM0 = "M00";
			}

			else if (tempStrings2[1].contains("M01") && !lastM0.equals("M01"))
			{
				String passagewayin = "passenger in passagerWay to Security ?";
				String isIn = strin.readLine();
				while (!isIn.equals("yes"))
				{
					String currentLuminance = big.getInfo("get passenger age");
					String passageWay = currentLuminance + "&" + tele;
					String resultLuminanceAdjust = serviceUtil.invokeService(url + "PassageWay", namespace, "LuminanceAdjust", passageWay);
					System.out.println("Current Luminance Adjust is:" + resultLuminanceAdjust);
					String resultAd = serviceUtil.invokeService(url + "PassageWay", namespace, "shoppingRecommendationByAge", tele);
					System.out.println("CurrentAd is:" + resultAd);
					newPath[index++] = "M01,M1" + tempInt2[i] + ",M2" + tempInt3[i] + ",M3" + tempInt4[i] + ",M4" + tempInt5[i];
					lastM0 = "M01";
				}
				// while (!passagewayin.equals("in passageWay"))
				// {
				//
				// }
				// 1->0
				// System.out.println("c" + "M01" + "M1" + tempInt2[j] + "M2" + tempInt3[j] + "M3" + tempInt4[j] + "M4" + tempInt5[j] );

			}
			else
			{
				// print xuxu[1]
				newPath[index++] = "M01,M1" + tempInt2[i] + ",M2" + tempInt3[i] + ",M3" + tempInt4[i] + ",M4" + tempInt5[i];
				// System.out.println("c" + "M0" + tempInt1[j] + "M1" + tempInt2[j] + "M2" + tempInt3[j] + "M3" + tempInt4[j] + "M4" + tempInt5[j]);
			}

			if (tempStrings2[2].contains("M10") && !lastM1.equals("M10"))
			{

			}
			else if (tempStrings2[2].contains("M11") && !lastM1.equals("M11"))
			{
				// 1->0
				// System.out.println("c" + "M01" + "");
				String currentLuminance = big.getInfo("get current light");
				String perferLuminanceString = big.getInfo("get perfer light");
				String passageWay = currentLuminance + "&" + tele;
				String resultLuminanceAdjust = serviceUtil.invokeService(url + "PassageWay", namespace, "LuminanceAdjust", passageWay);
				System.out.println("Current Luminance Adjust is:" + resultLuminanceAdjust);
				newPath[index++] = "M01,M1" + tempInt2[i] + ",M2" + tempInt3[i] + ",M3" + tempInt4[i] + ",M4" + tempInt5[i];
				lastM0 = "M01";
			}
			else
			{
				newPath[index++] = "M01,M1" + tempInt2[i] + ",M2" + tempInt3[i] + ",M3" + tempInt4[i] + ",M4" + tempInt5[i];

			}
			if (tempStrings2[2].contains("M20") && !lastM1.equals("M20"))
			{
				newPath[index++] = "M0" + tempInt1[i] + ",M1" + tempInt2[i] + ",M20,M3" + tempInt4[i] + ",M4" + tempInt5[i];
			}
			else if (tempStrings2[2].contains("M21") && !lastM1.equals("M21"))
			{
				// 1->0
				// System.out.println("c" + "M01" + "");
				String currentLuminance = big.getInfo("get current light");
				String perferLuminanceString = big.getInfo("get perfer light");
				String passageWay = currentLuminance + "&" + tele;
				String resultLuminanceAdjust = serviceUtil.invokeService(url + "PassageWay", namespace, "LuminanceAdjust", passageWay);
				System.out.println("Current Luminance Adjust is:" + resultLuminanceAdjust);
				newPath[index++] = "M01" + tempInt1[0] + "M1" + tempInt2[0] + ",M2" + tempInt3[0] + ",M3" + tempInt4[0] + ",M4" + tempInt5[0];
				lastM0 = "M01";
			}
			else
			{
				newPath[index++] = "M01" + tempInt1[0] + "M1" + tempInt2[0] + ",M2" + tempInt3[0] + ",M3" + tempInt4[0] + ",M4" + tempInt5[0];

			}
			if (tempStrings2[2].contains("M30") && !lastM1.equals("M30"))
			{
				newPath[index++] = "M01" + tempInt1[0] + "M1" + tempInt2[0] + ",M2" + tempInt3[0] + ",M30,M4" + tempInt5[0];
			}
			else if (tempStrings2[2].contains("M31") && !lastM1.equals("M31"))
			{
				// 1->0
				String currentLuminance = big.getInfo("get current light");
				String perferLuminanceString = big.getInfo("get perfer light");
				String passageWay = currentLuminance + "&" + tele;
				String resultLuminanceAdjust = serviceUtil.invokeService(url + "PassageWay", namespace, "LuminanceAdjust", passageWay);
				System.out.println("Current Luminance Adjust is:" + resultLuminanceAdjust);
				// System.out.println("c" + "M01" + "");
				newPath[index++] = "M01" + tempInt1[0] + "M1" + tempInt2[0] + ",M2" + tempInt3[0] + ",M3" + tempInt4[0] + ",M4" + tempInt5[0];
				lastM0 = "M01";
			}
			else
			{
				newPath[index++] = "M01" + tempInt1[0] + "M1" + tempInt2[0] + ",M2" + tempInt3[0] + ",M3" + tempInt4[0] + ",M4" + tempInt5[0];

			}
			if (tempStrings2[2].contains("M40") && !lastM1.equals("M40"))
			{
				newPath[index++] = "M0" + tempInt1[i] + ",M1" + tempInt2[i] + ",M2" + tempInt3[i] + ",M3" + tempInt4[i] + ",M40";
			}
			else if (tempStrings2[2].contains("M41") && !lastM1.equals("M41"))
			{
				// 1->0
				// System.out.println("c" + "M01" + "");
				String currentLuminance = big.getInfo("get current light");
				String perferLuminanceString = big.getInfo("get perfer light");
				String passageWay = currentLuminance + "&" + tele;
				String resultLuminanceAdjust = serviceUtil.invokeService(url + "PassageWay", namespace, "LuminanceAdjust", passageWay);
				System.out.println("Current Luminance Adjust is:" + resultLuminanceAdjust);
				newPath[index++] = "M01" + tempInt1[0] + "M1" + tempInt2[0] + ",M2" + tempInt3[0] + ",M3" + tempInt4[0] + ",M4" + tempInt5[0];
				lastM0 = "M01";
			}
			else
			{
				newPath[index++] = "M01" + tempInt1[0] + "M1" + tempInt2[0] + ",M2" + tempInt3[0] + ",M3" + tempInt4[0] + ",M4" + tempInt5[0];

			}
		}

		for (int i1 = 1; i1 <= 6; i1++)
		{
			ArrayList<String> newPaths = RPC.getPathStrings(fileName, sheetName, i1);
			String result1 = PathCompare(originalPaths, newPaths);
			System.out.print("Error" + (i1 > 9 ? "" : "0") + i1 + ":\t");
			System.out.println(result1);
		}

		// // test print
		// int i = 0;
		// for (String path : originalPaths)
		// {
		// System.out.print(path);
		//
		// System.out.print("\n");
		// i++;
		// }
	}

	public ArrayList<String> getPathStrings(String fileName, String sheetName, int column) throws FileNotFoundException, IOException
	{
		ArrayList<String> paths = new ArrayList<String>();
		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(new File(fileName)));
		XSSFSheet sheet = wb.getSheet(sheetName);
		int start = sheet.getFirstRowNum() + 1;
		int end = sheet.getLastRowNum();
		for (int i = start; i <= end; i++)
		{
			String path = null;
			XSSFRow row = sheet.getRow(i);
			XSSFCell cell = row.getCell(column);
			try
			{
				String s = cell.getStringCellValue();
				if (s.trim().length() > 0)
				{
					path = cell.getStringCellValue();
				}
				paths.add(path);
			}
			catch (Exception e)
			{
				// TODO: handle exception
			}
		}
		return paths;
	}

	public static String PathCompare(ArrayList<String> originalPath, ArrayList<String> path2)
	{
		if (originalPath.size() != path2.size())
			return "size not same.";
		String result = "";
		for (int i = 0; i < originalPath.size(); i++)
		{
			ArrayList<Integer> originalPath_S = PathProcessing(originalPath.get(i));
			ArrayList<Integer> path2_S = PathProcessing(path2.get(i));
			int r = SimplePathCompare(originalPath_S, path2_S);
			result += r + "\t";
		}
		return result;

	}

	private static int SimplePathCompare(ArrayList<Integer> originalPath, ArrayList<Integer> path2)
	{
		if (originalPath.size() != path2.size())
			return 1;
		for (int i = 0; i < originalPath.size(); i++)
		{
			if (originalPath.get(i) != path2.get(i))
				return 1;
		}
		return 0;
	}

	private static ArrayList<Integer> PathProcessing(String string)
	{
		ArrayList<Integer> result = new ArrayList<Integer>();
		String[] temp = string.split(",");
		for (String item : temp)
		{
			if (item.startsWith("M"))
			{
				int t = Integer.parseInt(item.substring(1, 3));
				if (result.size() == 0)
					result.add(t);
				else
				{
					if (result.get(result.size() - 1) != t)
						result.add(t);
				}
			}
		}
		return result;
	}
}
