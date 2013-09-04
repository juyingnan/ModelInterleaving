package system;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestCaseCompare
{

	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		ResultPathCompare RPC = new ResultPathCompare();
		String fileName = "D:\\Airport_Result.xlsx";
		String sheetName = "Airport";
		ArrayList<String> originalPaths = RPC.getPathStrings(fileName, sheetName, 0);
		for (int i = 1; i <= 6; i++)
		{
			ArrayList<String> newPaths = RPC.getPathStrings(fileName, sheetName, i);
			String result = PathCompare(originalPaths, newPaths);
			System.out.print("Error" + (i > 9 ? "" : "0") + i + ":\t");
			System.out.println(result);
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
		int end = sheet.getLastRowNum() - 1;
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