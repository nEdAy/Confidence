//package cn.neday.sheep.view;
//
//
//import android.app.Dialog;
//import android.content.ClipData;
//import android.content.ClipboardManager;
//import android.content.Context;
//import android.os.Handler;
//import android.os.Message;
//import android.view.*;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import cn.neday.sheep.R;
//import cn.neday.sheep.util.CommonUtils;
//import okhttp3.internal.platform.Platform;
//
//import java.util.HashMap;
//
//
///**
// * 六格分享页
// *
// * @author nEdAy
// */
//public class ShareDialog implements PlatformActionListener {
//    private final Context mContext;
//    private final Display display;
//    private final ShareHandler shareHandler = new ShareHandler();
//    private Dialog dialog;
//
//    public ShareDialog(Context context) {
//        mContext = context;
//        WindowManager windowManager = (WindowManager) context
//                .getSystemService(Context.WINDOW_SERVICE);
//        display = windowManager.getDefaultDisplay();
//    }
//
//    public ShareDialog builder(String title, String text, String imageUrl, String url) {
//        // 获取Dialog布局
//        View view = LayoutInflater.from(mContext).inflate(R.layout.include_pop_six_icon, null);
//        // 设置Dialog最小宽度为屏幕宽度
//        view.setMinimumWidth(display.getWidth());
//        // 获取自定义Dialog布局中的控件
//        TextView tv_cancel = view.findViewById(R.id.tv_left);
//        TextView tv_copy = view.findViewById(R.id.tv_copy);
//        LinearLayout wechat = view.findViewById(R.id.wechat);
//        wechat.setOnClickListener(v -> {
//            Platform.ShareParams sp = new Platform.ShareParams();
//            sp.setShareType(Platform.SHARE_WEBPAGE);//非常重要：一定要设置分享属性
//            sp.setTitle(title);  //分享标题
//            sp.setText(text);   //分享文本
//            sp.setImageUrl(imageUrl);//网络图片rul
//            sp.setUrl(url);   //网友点进链接后，可以看到分享的详情
//            Platform wechat_ = ShareSDK.getPlatform(Wechat.NAME);
//            wechat_.setPlatformActionListener(this); // 设置分享事件回调
//            wechat_.share(sp);
//            dismiss();
//        });
//        LinearLayout wechatf = view.findViewById(R.id.wechatf);
//        wechatf.setOnClickListener(v -> {
//            Platform.ShareParams sp = new Platform.ShareParams();
//            sp.setShareType(Platform.SHARE_WEBPAGE); //非常重要：一定要设置分享属性
//            sp.setTitle(title);  //分享标题
//            sp.setText(text);   //分享文本
//            sp.setImageUrl(imageUrl);//网络图片rul
//            sp.setUrl(url);   //网友点进链接后，可以看到分享的详情
//            Platform wechatMoments = ShareSDK.getPlatform(WechatMoments.NAME);
//            wechatMoments.setPlatformActionListener(this); // 设置分享事件回调
//            wechatMoments.share(sp);
//            dismiss();
//        });
//        LinearLayout qqzone = view.findViewById(R.id.qqzone);
//        qqzone.setOnClickListener(v -> {
//            Platform.ShareParams sp = new Platform.ShareParams();
//            sp.setTitle(title);  //分享标题
//            sp.setTitleUrl(url); // 标题的超链接
//            sp.setText(text);   //分享文本
//            sp.setImageUrl(imageUrl);//网络图片rul
//            sp.setSite(title); //分享的网站名称
//            sp.setSiteUrl(url);//分享网站的地址
//            Platform qzone = ShareSDK.getPlatform(QZone.NAME);
//            qzone.setPlatformActionListener(this); // 设置分享事件回调
//            qzone.share(sp);
//            dismiss();
//        });
//        LinearLayout qq = view.findViewById(R.id.qq);
//        qq.setOnClickListener(v -> {
//            Platform.ShareParams sp = new Platform.ShareParams();
//            sp.setTitle(title);  //分享标题
//            sp.setText(text);   //分享文本
//            sp.setImageUrl(imageUrl);//网络图片rul
//            sp.setTitleUrl(url);   //网友点进链接后，可以看到分享的详情
//            Platform qq_ = ShareSDK.getPlatform(QQ.NAME);
//            qq_.setPlatformActionListener(this); // 设置分享事件回调
//            qq_.share(sp);
//            dismiss();
//        });
//        LinearLayout sina = view.findViewById(R.id.sina);
//        sina.setOnClickListener(v -> {
//            Platform.ShareParams sp = new Platform.ShareParams();
//            sp.setText(text); //分享文本
//            sp.setImageUrl(imageUrl);//网络图片rul
//            Platform sinaWeibo = ShareSDK.getPlatform(SinaWeibo.NAME);
//            sinaWeibo.setPlatformActionListener(this); // 设置分享事件回调
//            sinaWeibo.share(sp);
//            dismiss();
//        });
//        LinearLayout sms = view.findViewById(R.id.sms);
//        sms.setOnClickListener(v -> {
//            Platform.ShareParams sp = new Platform.ShareParams();
//            sp.setText(text); //分享文本
//            Platform sms_ = ShareSDK.getPlatform(ShortMessage.NAME);
//            sms_.setPlatformActionListener(this); // 设置分享事件回调
//            sms_.share(sp);
//            dismiss();
//        });
//        tv_cancel.setOnClickListener(v -> dismiss());
//        tv_copy.setOnClickListener(v -> {
//            ClipboardManager mClipboardManager =
//                    (ClipboardManager) CustomApplication.getInstance().getSystemService(Context.CLIPBOARD_SERVICE);
//            ClipData mClipData = ClipData.newPlainText("url", url);
//            mClipboardManager.setPrimaryClip(mClipData);
//            shareHandler.sendEmptyMessage(0);
//        });
//        // 定义Dialog布局和参数
//        dialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);
//        dialog.setContentView(view);
//        Window dialogWindow = dialog.getWindow();
//        dialogWindow.setGravity(Gravity.BOTTOM);
//        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        lp.x = 0;
//        lp.y = 0;
//        dialogWindow.setAttributes(lp);
//        dialog.setCanceledOnTouchOutside(true);
//        return this;
//    }
//
//    public void show() {
//        ShareSDK.initSDK(CustomApplication.getInstance());
//        dialog.show();
//    }
//
//    /**
//     * 关闭对话框
//     */
//    private void dismiss() {
//        ShareSDK.stopSDK(CustomApplication.getInstance());
//        dialog.dismiss();
//    }
//
//    @Override
//    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//        //回调的地方是子线程，进行UI操作要用handle处理
//        if (platform.getName().equals(SinaWeibo.NAME)) {// 判断成功的平台是不是新浪微博
//            shareHandler.sendEmptyMessage(1);
//        } else if (platform.getName().equals(Wechat.NAME)) {
//            shareHandler.sendEmptyMessage(2);
//        } else if (platform.getName().equals(WechatMoments.NAME)) {
//            shareHandler.sendEmptyMessage(3);
//        } else if (platform.getName().equals(QQ.NAME)) {
//            shareHandler.sendEmptyMessage(4);
//        } else if (platform.getName().equals(QZone.NAME)) {
//            shareHandler.sendEmptyMessage(5);
//        } else if (platform.getName().equals(ShortMessage.NAME)) {
//            shareHandler.sendEmptyMessage(6);
//        }
//    }
//
//    @Override
//    public void onError(Platform platform, int i, Throwable throwable) {
//        throwable.printStackTrace();
//        Message msg = new Message();
//        msg.what = 8;
//        msg.obj = throwable.getMessage();
//        shareHandler.sendMessage(msg);
//    }
//
//    @Override
//    public void onCancel(Platform platform, int i) {
//        shareHandler.sendEmptyMessage(7);
//    }
//
//    private static final class ShareHandler extends Handler {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 0:
//                    CommonUtils.showToast("已复制内容到剪切板");
//                    break;
//                case 1:
//                    CommonUtils.showToast("微博分享成功");
//                    break;
//                case 2:
//                    CommonUtils.showToast("微信分享成功");
//                    break;
//                case 3:
//                    CommonUtils.showToast("朋友圈分享成功");
//                    break;
//                case 4:
//                    CommonUtils.showToast("QQ分享成功");
//                    break;
//                case 5:
//                    CommonUtils.showToast("QQ空间分享成功");
//                    break;
//                case 6:
//                    CommonUtils.showToast("短信分享成功");
//                    break;
//                case 7:
//                    CommonUtils.showToast("取消分享");
//                    break;
//                case 8:
//                    CommonUtils.showToast("分享失败啊" + msg.obj);
//                    break;
//                default:
//                    break;
//            }
//        }
//    }
//}
