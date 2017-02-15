package cn.update;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * ��ȡ����Ϣ�Ĺ���
 * 
 * @author ����
 *
 */
public class PackageUtil {
	// ��ȡ�汾��
	public static String getVersionName(Context context) {
		
		PackageManager pm = context.getPackageManager();
		
		PackageInfo info;
		try {
			// 0������Ǳ�־λ
			info = pm.getPackageInfo(context.getPackageName(), 0);
			
			return info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		return "δ֪�汾��";
	}

	// ��ȡ�汾��
	public static int getVersionCode(Context context) {
		
		PackageManager pm = context.getPackageManager();
		
		PackageInfo info;
		try {
			info = pm.getPackageInfo(context.getPackageName(), 0);
			return info.versionCode;
		} catch (NameNotFoundException e) {
			
			e.printStackTrace();
		}

		return 1;
	}

}
