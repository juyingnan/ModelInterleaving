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

public class ResultPathCompare {
	ServiceInvokeUtil serviceUtil = new ServiceInvokeUtil();
	String url = "http://localhost:8080/AirportInterleavingServices/services/";
	String namespace = "http://interleaving";
	static Bigraph big = new Bigraph();

	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		// 读入测试路径
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

		// 获取每一条测试路径
		for(int pathCount = 0; pathCount < originalPaths.size(); pathCount++){
			// 目标路径
			String[] currentPath = originalPaths.get(pathCount).split("->");
			// 生成的实际路径
			String[] newPathStrings = new String[currentPath.length*6];
			
			// TODO 初始化服务、情境模拟器
			
			// 记录上一状态值
			// lastM[0] 是空位 这样lastM中的index可以和statusCount%6一致
			int[] lastM = new int[6];
			boolean isSame = true;
			
			// 处理路径上的每一个点 六元组
			int statusCount = 0;
			for(String status : currentPath){
				int casenum = statusCount % 6;
				
				switch (casenum) {
				case 0:
					// 第一个状态 处理CX 直接调用比较函数
					isSame = statusCompare(status,status);
					break;

				default:
					// 根据不同状态
					String actual = treatMX(casenum, status, lastM);
					isSame = statusCompare(status, actual);
					break;
				}
				if(!isSame){// 发现不同的点
					break;
				}
			}
			
			if(!isSame){// 发现不同的点
				System.out.println("path " + pathCount + "failed!");
			}else{
				System.out.println("path " + pathCount + "Success!");
			}
			
		}
	}
	
	// call service
	public static void callService(int index, int mstatus){
		// TODO:
//		String currentLuminance = big.getInfo("get passenger age");
//		String passageWay = currentLuminance + "&" + tele;
//		String resultLuminanceAdjust = serviceUtil.invokeService(url + "PassageWay", namespace, "LuminanceAdjust", passageWay);
//		System.out.println("Current Luminance Adjust is:" + resultLuminanceAdjust);
//		String resultAd = serviceUtil.invokeService(url + "PassageWay", namespace, "shoppingRecommendationByAge", tele);
//		System.out.println("CurrentAd is:" + resultAd);
	}

	// 处理MX
	public static String treatMX(int index, String target, int[] lastM) {
		String[] mx = target.split(",");
		String actual = "";
		int mstatus = Integer.parseInt(mx[index].substring(2));
		if (mstatus == lastM[index]) {
			actual = target;
		} else {
			if (lastM[index] == 0) {
				// 0->1
				// TODO:call service
				callService(index, mstatus);

				for (int i = 0; i < mx.length; i++) {
					if (i == index) {
						actual = actual + ",M" + i + "1";
					} else {
						actual = actual + "," + mx[i];
					}
				}
			} else {
				// 1->0
				// TODO:call service
				callService(index, mstatus);

				for (int i = 0; i < mx.length; i++) {
					if (i == index) {
						actual = actual + ",M" + i + "0";
					} else {
						actual = actual + "," + mx[i];
					}
				}
			}

			lastM[index] = mstatus;
		}

		return actual;
	}

	// 比较两个点是否不同
	public static boolean statusCompare(String target, String actual) {
		if (target.equals(actual)) {
			return true;
		} else {
			return false;
		}
	}

	public ArrayList<String> getPathStrings(String fileName, String sheetName,
			int column) throws FileNotFoundException, IOException {
		ArrayList<String> paths = new ArrayList<String>();
		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(new File(
				fileName)));
		XSSFSheet sheet = wb.getSheet(sheetName);
		int start = sheet.getFirstRowNum() + 1;
		int end = sheet.getLastRowNum();
		for (int i = start; i <= end; i++) {
			String path = null;
			XSSFRow row = sheet.getRow(i);
			XSSFCell cell = row.getCell(column);
			try {
				String s = cell.getStringCellValue();
				if (s.trim().length() > 0) {
					path = cell.getStringCellValue();
				}
				paths.add(path);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return paths;
	}

	public static String PathCompare(ArrayList<String> originalPath,
			ArrayList<String> path2) {
		if (originalPath.size() != path2.size())
			return "size not same.";
		String result = "";
		for (int i = 0; i < originalPath.size(); i++) {
			ArrayList<Integer> originalPath_S = PathProcessing(originalPath
					.get(i));
			ArrayList<Integer> path2_S = PathProcessing(path2.get(i));
			int r = SimplePathCompare(originalPath_S, path2_S);
			result += r + "\t";
		}
		return result;

	}

	private static int SimplePathCompare(ArrayList<Integer> originalPath,
			ArrayList<Integer> path2) {
		if (originalPath.size() != path2.size())
			return 1;
		for (int i = 0; i < originalPath.size(); i++) {
			if (originalPath.get(i) != path2.get(i))
				return 1;
		}
		return 0;
	}

	private static ArrayList<Integer> PathProcessing(String string) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		String[] temp = string.split(",");
		for (String item : temp) {
			if (item.startsWith("M")) {
				int t = Integer.parseInt(item.substring(1, 3));
				if (result.size() == 0)
					result.add(t);
				else {
					if (result.get(result.size() - 1) != t)
						result.add(t);
				}
			}
		}
		return result;
	}
}
