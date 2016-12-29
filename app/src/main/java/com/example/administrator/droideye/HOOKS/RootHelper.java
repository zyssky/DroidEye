package com.example.administrator.droideye.HOOKS;

import android.os.Build;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class RootHelper {
    /**
     * @author Kevin Kowalewski
     */

    private RootHelper() {
    }

    /**
     * 应用程序运行命令获取 Root权限，设备必须已破解(获得ROOT权限)
     * @param command 命令：String apkRoot="chmod 777 "+getPackageCodePath(); RootCommand(apkRoot);
     * @return 应用程序是/否获取Root权限
     */
    public static boolean RootCommand(String command)
    {
        Process process = null;
        DataOutputStream os = null;
        try
        {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(command + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e)
        {
            Log.d("*** DEBUG ***", "ROOT REE" + e.getMessage());
            return false;
        } finally
        {
            try
            {
                if (os != null)
                {
                    os.close();
                }
                process.destroy();
            } catch (Exception e)
            {
                e.getMessage();
            }
        }
        Log.d("*** DEBUG ***", "Root SUC ");
        return true;
    }

    public static boolean isDeviceRooted() {
        return checkRootMethod1() || checkRootMethod2() || checkRootMethod3() || checkRootMethod4();
    }

    public static boolean checkRootMethod1() {
        String buildTags = Build.TAGS;
        return buildTags != null && buildTags.contains("test-keys");
    }

    public static boolean checkRootMethod2() {
        try {
            File file = new File("/system/app/Superuser.apk");
            return file.exists();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean checkRootMethod4() {
        try {
            File file = new File("/system/xbin/su");
            return file.exists();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean checkRootMethod3() {
        return new ExecShell().executeCommand(ExecShell.SHELL_CMD.check_su_binary) != null;
    }


    public static void handleSELinux() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Process localProcess = null;
            try {

                Process process = Runtime.getRuntime().exec(new String[]{"su"});
                DataOutputStream os = new DataOutputStream(process.getOutputStream());
                os.writeBytes("/system/bin/chcon u:object_r:system_data_file:s0 /data/data/com.ryansteckler.nlpunbounce \n");
                os.flush();
                os.writeBytes("chcon u:object_r:system_data_file:s0 /data/data/com.ryansteckler.nlpunbounce/files \n");
                os.flush();
                os.writeBytes("chcon u:object_r:system_data_file:s0 /data/data/com.ryansteckler.nlpunbounce/files/nlp* \n");
                os.flush();
                os.writeBytes("chcon u:object_r:system_data_file:s0 /data/data/com.ryansteckler.nlpunbounce/files/nlp* \n");
                os.flush();
                os.writeBytes("exit\n");
                os.flush();
                os.close();

                BufferedReader in = new BufferedReader(new InputStreamReader(
                        process.getInputStream()));
                String line;
                String fullResponse = "";
                try {
                    while ((line = in.readLine()) != null) {
                        fullResponse += ("\n" + (line));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                int i = 0;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

    /** @author Kevin Kowalewski */
    class ExecShell {

        private static String LOG_TAG = ExecShell.class.getName();

        public static enum SHELL_CMD {
            check_su_binary(new String[]{"/system/xbin/which", "su"});
            String[] command;

            SHELL_CMD(String[] command) {
                this.command = command;
            }
        }

        public ArrayList<String> executeCommand(SHELL_CMD shellCmd) {
            String line = null;
            ArrayList<String> fullResponse = new ArrayList<String>();
            Process localProcess = null;
            try {
                localProcess = Runtime.getRuntime().exec(shellCmd.command);
            } catch (Exception e) {
                return null;
            }
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                    localProcess.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    localProcess.getInputStream()));
            try {
                while ((line = in.readLine()) != null) {
                    fullResponse.add(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return fullResponse;
        }
    }

