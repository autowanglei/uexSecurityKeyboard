package org.zywx.wbpalmstar.plugin.uexsecuritykeyboard;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.gson.reflect.TypeToken;

import org.zywx.wbpalmstar.engine.DataHelper;
import org.zywx.wbpalmstar.engine.EBrowserView;
import org.zywx.wbpalmstar.engine.universalex.EUExBase;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.view.SecKeyboardView;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.vo.OpenDataVO;
import org.zywx.wbpalmstar.plugin.uexsecuritykeyboard.vo.ResultVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EUExSecurityKeyboard extends EUExBase {

    private static final String BUNDLE_DATA = "data";
    public static final String TAG = "EUExSecurityKeyboard";
    private HashMap<String, SecKeyboardView> mInputTexts = new HashMap<String, SecKeyboardView>();

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

        SecKeyboardView view = new SecKeyboardView(mContext, fl,
                dataVO.getKeyboardType());
        view.setOnInputStatusListener(new InputStatusListener(dataVO.getId()));
        if (!TextUtils.isEmpty(dataVO.getKeyboardDescription())){
            view.setDescription(dataVO.getKeyboardDescription());
        }

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addViewToCurrentWindow(view, layoutParams);

        mInputTexts.put(id, view);

    }

    public void close(String[] params) {
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
                    SecKeyboardView view = mInputTexts.get(id);
                    removeViewFromCurrentWindow(view);
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

    public Object getContent(String[] params) {
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
                    EditText editText = mInputTexts.get(id).getInputEditText();
                    if (editText != null){
                        String content = editText.getText().toString();
                        resultVO.setContent(content);
                        list.add(resultVO);
                    }
                }
            }
        }
        callBackPluginJs(JsConst.CALLBACK_GET_CONTENT, DataHelper.gson.toJson(list));
        return list;
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
