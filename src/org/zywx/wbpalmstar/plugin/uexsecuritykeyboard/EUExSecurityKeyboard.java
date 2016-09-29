package org.zywx.wbpalmstar.plugin.uexsecuritykeyboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.zywx.wbpalmstar.engine.DataHelper;
import org.zywx.wbpalmstar.engine.EBrowserView;
import org.zywx.wbpalmstar.engine.universalex.EUExBase;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.util.SeckeyboardData;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.view.SecKeyboardView;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.vo.OpenDataVO;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.vo.ResultVO;

import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class EUExSecurityKeyboard extends EUExBase {

    private static final String BUNDLE_DATA = "data";
    public static final String TAG = "EUExSecurityKeyboard";
    private HashMap<String, SeckeyboardData> mInputTexts = new HashMap<String, SeckeyboardData>();

    public EUExSecurityKeyboard(Context context, EBrowserView eBrowserView) {
        super(context, eBrowserView);
    }

    @Override
    protected boolean clean() {
        return false;
    }

    @Override
    public void onHandleMessage(Message message) {
        if(message == null){
            return;
        }
        Bundle bundle=message.getData();
        switch (message.what) {

        default:
                super.onHandleMessage(message);
        }
    }

    public void open(String[] params) {
        if (params == null || params.length < 1) {
            errorCallback(0, 0, "error params!");
            return;
        }
        String json = params[0];
        Log.i(TAG, "open:" + json);

        OpenDataVO dataVO = DataHelper.gson.fromJson(json, OpenDataVO.class);
        String id = dataVO.getId();
        if (TextUtils.isEmpty(id)){
            return;
        }
        if (mInputTexts.containsKey(id)){
            return;
        }

        RelativeLayout.LayoutParams fl = new RelativeLayout.LayoutParams(dataVO.getWidth(),
                dataVO.getHeight());
        fl.leftMargin = dataVO.getX();
        fl.topMargin = dataVO.getY();

        SecKeyboardView view = new SecKeyboardView(mContext, this,
                fl, dataVO);
        view.setOnInputStatusListener(new InputStatusListener(dataVO.getId()));
        if (!TextUtils.isEmpty(dataVO.getKeyboardDescription())){
            view.setDescription(dataVO.getKeyboardDescription());
        }

        if (dataVO.isScrollWithWeb()){
            AbsoluteLayout.LayoutParams param = new AbsoluteLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,
                    0, 0);
            addViewToWebView(view, param, TAG + dataVO.getId());
        }else {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            addViewToCurrentWindow(view, layoutParams);
        }
        mInputTexts.put(id, new SeckeyboardData(view, dataVO.isScrollWithWeb()));

    }

    public void close(String[] params) {
        Log.i(TAG, "close:" + Arrays.toString(params));
        List<String> ids = null;
        if (params != null && params.length > 0) {
            String json = params[0];
            ids = DataHelper.gson.fromJson(json,
                    new TypeToken<List<String>>() {
                    }.getType());
        }
        if (ids == null || ids.size() < 1){
            //关闭全部
            ids = getAllListViewIds();
        }

        if (ids != null && ids.size() > 0) {
            for (int i = 0; i < ids.size(); i++) {
                String id = ids.get(i);
                if (mInputTexts.containsKey(id)){
                    if (mInputTexts.get(id).isScrollWithWeb()){
                        removeViewFromWebView(TAG + id);
                    }else{
                        SecKeyboardView view = mInputTexts.get(id).getView();
                        removeViewFromCurrentWindow(view);
                    }
                }
                mInputTexts.remove(id);
            }
        }
    }

    private List<String> getAllListViewIds() {
        List<String> list = new ArrayList<String>();
        for (String s : mInputTexts.keySet()) {
            list.add(s);
        }
        return list;
    }

    public void getContent(String[] params) {
        List<String> ids = null;
        if (params != null && params.length > 0) {
            String json = params[0];
            ids = DataHelper.gson.fromJson(json,
                    new TypeToken<List<String>>() {
                    }.getType());
        }
        if (ids == null || ids.size() < 1){
            //获取全部数据
            ids = getAllListViewIds();
        }
        List<ResultVO> list = new ArrayList<ResultVO>();
        if (ids != null && ids.size() > 0) {
            for (int i = 0; i < ids.size(); i++) {
                ResultVO resultVO = new ResultVO();
                String id = ids.get(i);
                resultVO.setId(id);
                if (mInputTexts.containsKey(id)){
                    EditText editText = mInputTexts.get(id).getView().getInputEditText();
                    if (editText != null){
                        String content = editText.getText().toString();
                        resultVO.setContent(content);
                        list.add(resultVO);
                    }
                }
            }
        }
        callBackPluginJs(JsConst.CALLBACK_GET_CONTENT, DataHelper.gson.toJson(list));
    }

    public void onKeyPress(int type) {
        JSONObject onKeyPressJson = new JSONObject();
        try {
            onKeyPressJson.put("inputType", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        callBackPluginJs(JsConst.ON_KEY_PRESS, onKeyPressJson.toString());
    }

    private void callBackPluginJs(String methodName, String jsonData){
        String js = SCRIPT_HEADER + "if(" + methodName + "){"
                + methodName + "('" + jsonData + "');}";
        Log.i(TAG, "callBackPluginJs:" + js);
        onCallback(js);
    }

    public interface OnInputStatusListener{
        public void onInputCompleted(ResultVO resultVO);
        public void onKeyboardDismiss(ResultVO resultVO);
    }

    private class InputStatusListener implements OnInputStatusListener{

        private String id;
        public InputStatusListener(String id) {
            this.id = id;
        }

        @Override
        public void onInputCompleted(ResultVO resultVO) {
            resultVO.setId(id);
            //callBackPluginJs(JsConst.ON_INPUT_COMPLETED, DataHelper.gson.toJson(resultVO));
        }

        @Override
        public void onKeyboardDismiss(ResultVO resultVO) {
            resultVO.setId(id);
            //callBackPluginJs(JsConst.ON_KEYBOARD_DISMISS, DataHelper.gson.toJson(resultVO));
        }
    }
}
