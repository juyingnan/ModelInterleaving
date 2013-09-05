package system;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		// �������·��
		ResultPathCompare RPC = new ResultPathCompare();
		String fileName = "D:\\Airport_Result.xlsx";
		String sheetName = "Airport";
		ArrayList<String> originalPaths = RPC.getPathStrings(fileName,
				sheetName, 0);

		String[] sensor = { "M0", "M1", "M2", "M3", "M4" };
		sensor[0] = "Shop";
		sensor[1] = "Light1";
		sensor[2] = "Light2";
		sensor[3] = "BillBoard";
		sensor[4] = "Light3";

		// ��ȡÿһ������·��
		for (int pathCount = 0; pathCount < originalPaths.size(); pathCount++) {
			// Ŀ��·��
			String[] currentPath = originalPaths.get(pathCount).split("->");
			// ���ɵ�ʵ��·��
			String[] newPathStrings = new String[currentPath.length * 6];

			// TODO ��ʼ�������龳ģ����

			// ��¼��һ״ֵ̬
			// lastM[0] �ǿ�λ ����lastM�е�index���Ժ�statusCount%6һ��
			int[] lastM = new int[6];
			boolean isSame = true;

			// ����·���ϵ�ÿһ���� ��Ԫ��
			int statusCount = 0;
			for (String status : currentPath) {
				int casenum = statusCount % 6;

				switch (casenum) {
				case 0:
					// ��һ��״̬ ����CX ֱ�ӵ��ñȽϺ���
					isSame = statusCompare(status, status);
					break;

				default:
					// ���ݲ�ͬ״̬
					String actual = treatMX(casenum, status, lastM);
					isSame = statusCompare(status, actual);
					break;
				}
				if (!isSame) {// ���ֲ�ͬ�ĵ�
					break;
				}
				statusCount++;
			}

			if (!isSame) {// ���ֲ�ͬ�ĵ�
				System.out.println("path " + pathCount + "failed!");
			} else {
				System.out.println("path " + pathCount + "Success!");
			}

		}
	}


	// ����һ�鴦�� currentM��lastM���ǵ�ǰ��MX״̬����һ��MX��״̬
	// treatM0�Ĺ��ܾ��Ǹ���M0�Ĺ��ܣ��������ô�������֣����ر���ϵͳ��Ϊ�� ��һ��ת��״̬�͵��÷���
	public static int treatM0(int currentM, int lastM) {
		if (lastM == currentM) {// ��ǰ״̬����һ��״̬��ͬ��˵��Mû�б仯
			return currentM;
		} else {
			if (lastM == 0) {// 0->1
				callM0Service(lastM);
				return 1;// �����������״̬ת�����󣨱��� get�����˽�����ʱ��
							// Ӧ��ת����1״̬�������޸ĳ�0�Ļ�������һ�������ת���� �㶮�ģ�
			} else {// 1->0
				callM0Service(lastM);
				return 0;
			}
		}
	}

	public static int treatM1(int currentM, int lastM) {
		if (lastM == currentM) {// ��ǰ״̬����һ��״̬��ͬ��˵��Mû�б仯
			return currentM;
		} else {
			if (lastM == 0) {// 0->1
				callM1Service(lastM);
				return 1;// �����������״̬ת�����󣨱��� get�����˽�����ʱ��
							// Ӧ��ת����1״̬�������޸ĳ�0�Ļ�������һ�������ת���� �㶮�ģ�
			} else {// 1->0
				callM1Service(lastM);
				return 0;
			}
		}
	}

	public static int treatM2(int currentM, int lastM) {
		if (lastM == currentM) {// ��ǰ״̬����һ��״̬��ͬ��˵��Mû�б仯
			return currentM;
		} else {
			if (lastM == 0) {// 0->1
				callM2Service(lastM);
				return 1;// �����������״̬ת�����󣨱��� get�����˽�����ʱ��
							// Ӧ��ת����1״̬�������޸ĳ�0�Ļ�������һ�������ת���� �㶮�ģ�
			} else {// 1->0
				callM2Service(lastM);
				return 0;
			}
		}
	}

	public static int treatM3(int currentM, int lastM) {
		if (lastM == currentM) {// ��ǰ״̬����һ��״̬��ͬ��˵��Mû�б仯
			return currentM;
		} else {
			if (lastM == 0) {// 0->1
				callM3Service(lastM);
				return 1;// �����������״̬ת�����󣨱��� get�����˽�����ʱ��
							// Ӧ��ת����1״̬�������޸ĳ�0�Ļ�������һ�������ת���� �㶮�ģ�
			} else {// 1->0
				callM3Service(lastM);
				return 0;
			}
		}
	}

	public static int treatM4(int currentM, int lastM) {
		if (lastM == currentM) {// ��ǰ״̬����һ��״̬��ͬ��˵��Mû�б仯
			return currentM;
		} else {
			if (lastM == 0) {// 0->1
				callM4Service(lastM);
				return 1;// �����������״̬ת�����󣨱��� get�����˽�����ʱ��
							// Ӧ��ת����1״̬�������޸ĳ�0�Ļ�������һ�������ת���� �㶮�ģ�
			} else {// 1->0
				callM4Service(lastM);
				return 0;
			}
		}
	}

	private static void callM4Service(int lastM) {
		// TODO Auto-generated method stub

	}

	private static void callM3Service(int lastM) {
		// TODO Auto-generated method stub

	}

	private static void callM2Service(int lastM) {
		// TODO Auto-generated method stub

	}

	private static void callM1Service(int lastM) {
		// TODO Auto-generated method stub

	}

	private static void callM0Service(int lastM) {
		// TODO M0�������
		// lastM ��ʾ��һ��M0��״̬ ���ݲ�ͬ��lastM ѡ���Ƿ���÷��񡢵����������
		// �Է���ֵ���ж�ʲô�� ����������

	}

	// ����MX
	public static String treatMX(int index, String target, int[] lastM) {
		// index 1 2 3 4 5
		String[] mx = target.split(",");
		String actual = "";
		int currentM = Integer.parseInt(mx[index].substring(2));// 0 1 mx��״ֵ̬
		int outputM = 0;// ������¼����ֵ����treatM0�ķ���ֵ ���ֵ��������¼ʵ��·���� MX��״ֵ̬��
		// ����index��ֵ ѡ��ͬ�Ĵ�����
		switch (index) {
		case 1:
			outputM = treatM0(currentM, lastM[index]);
			break;
		case 2:
			outputM = treatM1(currentM, lastM[index]);
			break;
		case 3:
			outputM = treatM1(currentM, lastM[index]);
			break;
		case 4:
			outputM = treatM1(currentM, lastM[index]);
			break;
		case 5:
			outputM = treatM1(currentM, lastM[index]);
			break;
		default:
			break;
		}

		for (int i = 0; i < mx.length; i++) {
			if (i == index) {
				actual = actual + ",M" + i + outputM;
			} else {
				actual = actual + "," + mx[i];
			}
		}
		lastM[index] = currentM;// ��¼��һ��

		return actual;
	}

	// �Ƚ��������Ƿ�ͬ
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
