package com.example.mjj.followqqshortcuts;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * 仿QQ一键把好友发送至桌面
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendToDesk(View view) {
        addShortCuts();
    }

    public void deleteDeskShortCuts(View view) {
        removeShortcut(MainActivity.this, "快捷", getPackageName() + ".MainActivity");
    }

    /**
     * 删除快捷方式
     *
     * @param shortcutName 快捷方式名字
     * @param className    绝对路径如：getPackageName() + ".MainActivity"
     */
    public void removeShortcut(Context cxt, String shortcutName, String className) {
        Intent shortcutIntent = new Intent(Intent.ACTION_VIEW);
        shortcutIntent.setClassName(cxt, className);
        Intent intent = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
        intent.putExtra("duplicate", true); //如有多个相同图标，循环删除
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);
        cxt.sendBroadcast(intent);
        Toast.makeText(MainActivity.this, "deleted快捷方式", Toast.LENGTH_SHORT).show();
    }

    /**
     * 添加快捷方式
     */
    private void addShortCuts() {
        Intent addIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        // 设置图标
        Parcelable icon = Intent.ShortcutIconResource.fromContext(
                MainActivity.this, R.drawable.ic_launcher);
        addIntent.putExtra("duplicate", false); //不允许重复创建
        Intent myIntent = new Intent(MainActivity.this, MainActivity.class);
        // 设置快捷方式名称
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "快捷");
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        ComponentName comp = new ComponentName(this.getPackageName(), "." + this.getLocalClassName());
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).setComponent(comp));
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, myIntent);
        sendBroadcast(addIntent);
        Toast.makeText(MainActivity.this, "快捷方式已添加", Toast.LENGTH_SHORT).show();
    }

}
