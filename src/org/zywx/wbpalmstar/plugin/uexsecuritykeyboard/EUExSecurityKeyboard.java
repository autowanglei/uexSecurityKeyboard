package org.zywx.wbpalmstar.plugin.uexsecuritykeyboard;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.RelativeLayout;

import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.zywx.wbpalmstar.engine.DataHelper;
import org.zywx.wbpalmstar.engine.EBrowserView;
import org.zywx.wbpalmstar.engine.universalex.EUExBase;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.keyboardMgr.KeyboardBaseMgr;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.util.ConstantUtil;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.util.JsConst;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.util.SeckeyboardData;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.view.KeyboardBaseView;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.view.RandomKeyboardView;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.view.SecKeyboardView;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.vo.OpenDataVO;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.vo.ResultVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EUExSecurityKeyboard extends EUExBase {

    private static final String BUNDLE_DATA = "data";
    public static final String TAG = "EUExSecurityKeyboard";
    private HashMap<String, SeckeyboardData> mInputTexts = new HashMap<String, SeckeyboardData>();
    final String INVALID_CODE = null;
    /** * 保存添加到网页的view */
    private static Map<String, View> addToWebViewsMap = new HashMap<String, View>();

    public EUExSecurityKeyboard(Context context, EBrowserView eBrowserView) {
        super(context, eBrowserView);
    }

    @Override
    protected boolean clean() {
        return false;
    }

    @Override
    public void onHandleMessage(Message message) {
        if (message == null) {
            return;
        }
        Bundle bundle = message.getData();
        switch (message.what) {

            default:
                super.onHandleMessage(message);
        }
    }

    public String open(String[] params) {
        if (params == null || params.length < 1) {
            errorCallback(0, 0, "error params!");
            return INVALID_CODE;
        }
        String json = params[0];

        OpenDataVO dataVO = DataHelper.gson.fromJson(json, OpenDataVO.class);
        //String id = dataVO.getId();
        if (TextUtils.isEmpty(dataVO.getId())) {
            dataVO.setId(String.valueOf(getRandomId()));
        }
        if (mInputTexts.containsKey(dataVO.getId())) {
            KeyboardBaseMgr mgr = mInputTexts.get(dataVO.getId())
                    .getView().mKeyboardBaseMgr;
            if (mgr != null) {
                mgr.showKeyboard(mContext, mgr.mEUExKeyboard,
                        mgr.keyboardViewParent, dataVO.getId());
            }
        } else {
            RelativeLayout.LayoutParams inputEditLpRl = new RelativeLayout.LayoutParams(
                    dataVO.getWidth(), dataVO.getHeight());
            inputEditLpRl.leftMargin = dataVO.getX();
            inputEditLpRl.topMargin = dataVO.getY();
            KeyboardBaseView view = null;
            /** 乱序、数字键盘 */
            if (dataVO.isRandom()
                    && (ConstantUtil.KEYBOARD_MODE_NUMBER == dataVO
                    .getKeyboardType())) {
                view = new RandomKeyboardView(mContext, this,
                        new InputStatusListener(dataVO.getId()), inputEditLpRl,
                        dataVO);
            } else {
                view = new SecKeyboardView(mContext, this,
                        new InputStatusListener(dataVO.getId()), inputEditLpRl,
                        dataVO);
            }
            addPluginViewToWeb(view, dataVO);
            mInputTexts.put(dataVO.getId(),
                    new SeckeyboardData(view, dataVO.isScrollWithWeb()));
        }
        return dataVO.getId();
    }

    private int getRandomId() {
        return (int) (Math.random() * 100000);
    }

    private void addPluginViewToWeb(View view, OpenDataVO dataVO) {
        String tag = TAG + dataVO.getId();
        if (dataVO.isScrollWithWeb()) {
            AbsoluteLayout.LayoutParams param = new AbsoluteLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT, 0, 0);
            addViewToWebView(view, param, tag);
        } else {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            addViewToCurrentWindow(view, layoutParams);
        }
        addToWebViewsMap.put(tag, view);
    }


    public void close(String[] params) {
        List<String> ids = null;
        //如果只传一个id, 则只删除某一个
        if (params != null && params.length == 1 && (!TextUtils.isEmpty(params[0]) && !params[0].contains(","))) {
            String id = params[0];
            removePluginViewFromWeb(id);
            return;
        }
        if (params != null && params.length > 0) {
            String json = params[0];
            ids = DataHelper.gson.fromJson(json,
                    new TypeToken<List<String>>() {
                    }.getType());
        }
        if (ids == null || ids.size() < 1) {
            //关闭全部
            ids = getAllListViewIds();
        }

        if (ids != null && ids.size() > 0) {
            for (int i = 0; i < ids.size(); i++) {
                String id = ids.get(i);
                removePluginViewFromWeb(id);
            }
        }
    }

    private void removePluginViewFromWeb(String id) {
        if (mInputTexts.containsKey(id)) {
            if (mInputTexts.get(id).isScrollWithWeb()) {
                removeViewFromWebView(TAG + id);
            } else {
                KeyboardBaseView view = mInputTexts.get(id).getView();
                removeViewFromCurrentWindow(view);
            }
            addToWebViewsMap.remove(TAG + id);
        }
        mInputTexts.remove(id);
    }

    private List<String> getAllListViewIds() {
        List<String> list = new ArrayList<String>();
        for (String s : mInputTexts.keySet()) {
            list.add(s);
        }
        return list;
    }

    public static void onActivityResume(Context context) {
        Set<String> tagList = addToWebViewsMap.keySet();
        for (String tag : tagList) {
            onResume(tag);
        }
    }

    private static void onResume(String viewTag) {
        View removeView = addToWebViewsMap.get(viewTag);
        if (removeView != null) {
            ((KeyboardBaseView) removeView).onResume();
        }
    }

    public Object getContent(String[] params) {
        List<String> ids = null;
        if (params != null && params.length > 0) {
            String json = params[0];
            ids = DataHelper.gson.fromJson(json,
                    new TypeToken<List<String>>() {
                    }.getType());
        }
        if (ids == null || ids.size() < 1) {
            //获取全部数据
            ids = getAllListViewIds();
        }
        List<ResultVO> list = new ArrayList<ResultVO>();
        if (ids != null && ids.size() > 0) {
            for (int i = 0; i < ids.size(); i++) {
                ResultVO resultVO = new ResultVO();
                String id = ids.get(i);
                resultVO.setId(id);
                if (mInputTexts.containsKey(id)) {
                    String content = mInputTexts.get(id).getView().getInputValue();
                    resultVO.setContent(content);
                    list.add(resultVO);
                }
            }
        }
        callBackPluginJs(JsConst.CALLBACK_GET_CONTENT, DataHelper.gson.toJson(list));
        return list;
    }

    public String getData(String[] params) {
        if (params == null || params.length < 1) {
            return null;
        }
        String id = params[0];
        if (mInputTexts.containsKey(id)) {
            String content = mInputTexts.get(id).getView().getInputValue();
            return content;
        }
        return null;
    }

    public void onKeyPress(int type) {
        JSONObject onKeyPressJson = new JSONObject();
        try {
            onKeyPressJson.put(ConstantUtil.JK_INPUT_TYPE, type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        callBackPluginJs(JsConst.ON_KEY_PRESS, onKeyPressJson.toString());
    }

    public void onKeyboardVisibilityChange(String id, int visibility) {
        JSONObject onKeyPressJson = new JSONObject();
        try {
            onKeyPressJson.put(ConstantUtil.JK_KEY_BORAD_ID, id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        callBackPluginJs(
                (ConstantUtil.KEY_BORAD_VISIBLE == visibility)
                        ? JsConst.ON_SHOW_KEY_BOARD : JsConst.ON_HIDE_KEY_BOARD,
                onKeyPressJson.toString());
    }

    private void callBackPluginJs(String methodName, String jsonData) {
        String js = SCRIPT_HEADER + "if(" + methodName + "){"
                + methodName + "('" + jsonData + "');}";
        Log.i(TAG, "callBackPluginJs:" + js);
        onCallback(js);
    }
}
