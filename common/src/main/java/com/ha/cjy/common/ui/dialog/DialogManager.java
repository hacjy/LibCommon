package com.ha.cjy.common.ui.dialog;

import android.app.Dialog;

import java.util.Iterator;
import java.util.Stack;

/**
 * 对话框管理
 * Created by cjy on 2018/7/17.
 */

public class DialogManager {
    //栈表
    private static Stack<Dialog> dialogStack;

    private static DialogManager instance;

    private DialogManager() {
    }

    public static DialogManager getInstance() {
        if (instance == null){
            synchronized (DialogManager.class){
                if(instance == null) {
                    instance = new DialogManager();
                }
            }
        }
        return instance;
    }

    /**
     * 添加dialog
     * @param Dialog
     */
    public void addDialog(Dialog Dialog) {
        if(dialogStack == null) {
            dialogStack = new Stack();
        }

        dialogStack.add(Dialog);
    }

    /**
     * 当前的dialog
     * @return
     */
    public Dialog currentDialog() {
        if(dialogStack != null && !dialogStack.isEmpty()) {
            Dialog Dialog = (Dialog)dialogStack.lastElement();
            return Dialog;
        } else {
            return null;
        }
    }

    /**
     * 查找dialog
     * @param cls
     * @return
     */
    public Dialog findDialog(Class<?> cls) {
        Dialog Dialog = null;
        Iterator iterator = dialogStack.iterator();

        while(iterator.hasNext()) {
            Dialog aty = (Dialog)iterator.next();
            if(aty.getClass().equals(cls)) {
                Dialog = aty;
                break;
            }
        }

        return Dialog;
    }

    /**
     * 移除dialog
     */
    public void removeDialog() {
        Dialog dialog = (Dialog)dialogStack.lastElement();
        this.removeDialog(dialog);
    }

    public void removeDialog(Dialog dialog) {
        if(dialog != null) {
            dialogStack.remove(dialog);
            dialog.dismiss();
            dialog = null;
        }

    }

    /**
     * 结束dialog
     * @param cls
     */
    public void finishDialog(Class<?> cls) {
        Iterator iterator = dialogStack.iterator();

        while(iterator.hasNext()) {
            Dialog dialog = (Dialog)iterator.next();
            if(dialog.getClass().equals(cls)) {
                this.removeDialog(dialog);
            }
        }

    }

    public static Stack<Dialog> getDialogStack() {
        return dialogStack;
    }

    public void finishOthersDialog(Class<?> cls) {
        Iterator iterator = dialogStack.iterator();

        while(iterator.hasNext()) {
            Dialog dialog = (Dialog)iterator.next();
            if(!dialog.getClass().equals(cls)) {
                this.removeDialog(dialog);
            }
        }

    }

    /**
     * 结束所有dialog
     */
    public void finishAllDialog() {
        for(int i = dialogStack.size() - 1; i >= 0; --i) {
            if(null != dialogStack.get(i)) {
                ((Dialog)dialogStack.get(i)).dismiss();
            }
        }

        dialogStack.clear();
    }
}
