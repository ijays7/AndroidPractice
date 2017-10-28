package com.ijays.apolo;

import android.app.Application;
import android.util.Log;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

/**
 * Created by ijaysdev on 28/10/2017.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        initHotFix();
    }

    private void initHotFix() {

        Log.e("SONGJIE","----->"+BuildConfig.hotfixAppId);
        SophixManager.getInstance().setContext(this)
                .setAppVersion(BuildConfig.VERSION_NAME)
                .setSecretMetaData(BuildConfig.hotfixAppId, BuildConfig.hotfixSecret, BuildConfig.hotfixRSA)
                .setEnableDebug(true).setPatchLoadStatusStub(new PatchLoadStatusListener() {
            @Override
            public void onLoad(int mode, int code, String info, int handlePatchVersion) {
                switch (code) {
                    case PatchStatus.CODE_LOAD_SUCCESS:
                        //补丁加载成功
                        //TODO
                        Log.e("SONGJIE", "patch loaded");
                        break;
                    case PatchStatus.CODE_LOAD_RELAUNCH:
                        //表明补丁生效需要重启
                        break;
                    default:
                        break;
                }

            }
        }).initialize();

        //拉取补丁
        SophixManager.getInstance().queryAndLoadNewPatch();
    }
}
