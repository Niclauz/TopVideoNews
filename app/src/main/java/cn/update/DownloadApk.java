package cn.update;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

public class DownloadApk {

	private static ApkInstallReceiver apkInstallReceiver;

	/**
	 * ����APK�ļ�
	 * 
	 * @param context
	 * @param url
	 * @param title
	 * @param appName
	 */
	public static void downloadApk(Context context, String url, String title, final String appName) {

		// ��ȡ�洢������ID
		long downloadId = SystemParams.getInstance().getLong(DownloadManager.EXTRA_DOWNLOAD_ID, -1L);
		if (downloadId != -1) {
			// ����downloadId
			DownLoadUtils downLoadUtils = DownLoadUtils.getInstance(context);
			// ��ȡ��ǰ״̬
			int status = downLoadUtils.getDownloadStatus(downloadId);
			if (DownloadManager.STATUS_SUCCESSFUL == status) {
				// ״̬Ϊ���سɹ�
				// ��ȡ����·��URI
				Uri downloadUri = downLoadUtils.getDownloadUri(downloadId);
				if (null != downloadUri) {
					// �������ص�APK���������APK��ͬ���������½��档��֮��ɾ�����������ء�
					if (compare(getApkInfo(context, downloadUri.getPath()), context)) {
						startInstall(context, downloadUri);
						return;
					} else {
						// ɾ�����������Լ��ļ�
						downLoadUtils.getDownloadManager().remove(downloadId);
					}
				}
				start(context, url, title, appName);
			} else if (DownloadManager.STATUS_FAILED == status) {
				// ����ʧ��,��������
				start(context, url, title, appName);
			} else {
				Log.d(context.getPackageName(), "apk is already downloading");
			}
		} else {
			// ������downloadId��û�����ع�APK
			start(context, url, title, appName);
		}
	}

	/**
	 * ��ʼ����
	 * 
	 * @param context
	 * @param url
	 * @param title
	 * @param appName
	 */
	private static void start(Context context, String url, String title, String appName) {

		if (hasSDKCard()) {
			long id = DownLoadUtils.getInstance(context).download(url, title, "������ɺ�����", appName);
			SystemParams.getInstance().setLong(DownloadManager.EXTRA_DOWNLOAD_ID, id);
		} else {
			Toast.makeText(context, "�ֻ�δ��װSD��������ʧ��", Toast.LENGTH_LONG).show();
		}
	}

	public static void registerBroadcast(Context context) {
		apkInstallReceiver = new ApkInstallReceiver();
		context.registerReceiver(apkInstallReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
	}

	public static void unregisterBroadcast(Context context) {
		if (null != apkInstallReceiver) {
			context.unregisterReceiver(apkInstallReceiver);
		}
	}

	/**
	 * ��ת����װ����
	 * 
	 * @param context
	 * @param uri
	 */
	private static void startInstall(Context context, Uri uri) {

		Intent install = new Intent(Intent.ACTION_VIEW);
		install.setDataAndType(uri, "application/vnd.android.package-archive");
		install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(install);
	}

	/**
	 * ��ȡAPK������Ϣ
	 * 
	 * @param context
	 * @param path
	 * @return
	 */
	private static PackageInfo getApkInfo(Context context, String path) {

		PackageManager pm = context.getPackageManager();
		PackageInfo pi = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
		if (null != pi) {
			return pi;
		}
		return null;
	}

	/**
	 * �Ƚ�����APK����Ϣ
	 * 
	 * @param apkInfo
	 *            apk��Ϣ
	 * @param context
	 *            ������
	 * @return
	 */
	private static boolean compare(PackageInfo apkInfo, Context context) {

		if (null == apkInfo) {
			return false;
		}
		String localPackageName = context.getPackageName();
		if (localPackageName.equals(apkInfo.packageName)) {
			try {
				PackageInfo packageInfo = context.getPackageManager().getPackageInfo(localPackageName, 0);
				// �Ƚϵ�ǰAPK�����ص�APK�汾��
				if (apkInfo.versionCode > packageInfo.versionCode) {
					// ������ص�APK�汾�Ŵ��ڵ�ǰ��װ��APK�汾�ţ�����true
					return true;
				}
			} catch (PackageManager.NameNotFoundException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * �Ƿ����SD��
	 */
	private static boolean hasSDKCard() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * ɾ�������ص��ļ�
	 */
	public static void removeFile(Context context) {
		String filePath = SystemParams.getInstance().getString("downloadApk", null);
		if (null != filePath) {
			File downloadFile = new File(filePath);
			if (downloadFile != null && downloadFile.exists()) {
				// ɾ��֮ǰ���ж��û��Ƿ��Ѿ���װ�ˣ���װ�˲�ɾ����
				if (!compare(getApkInfo(context, filePath), context)) {
					downloadFile.delete();
					Log.e("----", "��ɾ��");
				}
			}
		}
	}

}
