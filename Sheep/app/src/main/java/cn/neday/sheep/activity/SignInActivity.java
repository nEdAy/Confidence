//package cn.neday.sheep.activity;
//
//import android.os.Bundle;
//import android.widget.TextView;
//import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
//import com.flyco.animation.BounceEnter.BounceTopEnter;
//import com.flyco.animation.SlideExit.SlideBottomExit;
//import com.flyco.dialog.listener.OnBtnClickL;
//import com.flyco.dialog.widget.NormalDialog;
//import com.neday.bomb.R;
//import com.neday.bomb.StaticConfig;
//import com.neday.bomb.base.BaseOnlineActivity;
//import com.neday.bomb.entity.User;
//import com.neday.bomb.network.RxFactory;
//import com.neday.bomb.util.AliTradeHelper;
//import com.neday.bomb.util.CommonUtils;
//import com.neday.bomb.view.loading.CatLoadingView;
//import com.orhanobut.logger.Logger;
//
///**
// * 签到页
// *
// * @author nEdAy
// */
//public class SignInActivity extends BaseOnlineActivity {
//    private final static String TAG = "SignInActivity";
//    private TextView tv_sign_in_1, tv_sign_in_2, tv_sign_in_3;
//    private CatLoadingView catLoadingView;
//
//    @Override
//    public int bindLayout() {
//        return R.layout.activity_sign_in;
//    }
//
//    @Override
//    public void initView(Bundle savedInstanceState) {
//        setTintManager();
//        initTopBarForBoth("每日签到", getString(R.string.tx_back), "规则说明", () -> {
//            AliTradeHelper aliTradeUtils = new AliTradeHelper(this);
//            aliTradeUtils.showItemURLPage(StaticConfig.KZ_JF);
//        });
//        catLoadingView = new CatLoadingView();
//        tv_sign_in_1 = findViewById(R.id.tv_sign_in_1);
//        tv_sign_in_2 = findViewById(R.id.tv_sign_in_2);
//        tv_sign_in_3 = findViewById(R.id.tv_sign_in_3);
//    }
//
//    @Override
//    public void onResumeAfter() {
//        String currentUserId = currentUser.getObjectId();
//        toSubscribe(RxFactory.getUserServiceInstance(null)
//                        .getUser(currentUserId, "_User[sign_in]").map(User::getSign_in),
//                () ->
//                        catLoadingView.show(getSupportFragmentManager(), TAG),
//                isNoSign_in -> {
//                    catLoadingView.dismissAllowingStateLoss();
//                    if (isNoSign_in) {
//                        tv_sign_in_1.setEnabled(true);
//                        tv_sign_in_2.setEnabled(true);
//                        tv_sign_in_3.setEnabled(true);
//                    } else {
//                        CommonUtils.showToast("当日已签到");
//                    }
//                },
//                throwable -> {
//                    catLoadingView.dismissAllowingStateLoss();
//                    CommonUtils.showToast("签到状态异常");
//                    Logger.e(throwable.getMessage());
//                });
//        tv_sign_in_1.setOnClickListener(v -> signIn(currentUserId, 0));
//        tv_sign_in_2.setOnClickListener(v -> signIn(currentUserId, 1));
//        tv_sign_in_3.setOnClickListener(v -> signIn(currentUserId, 2));
//    }
//
//    /**
//     * 开始签到
//     *
//     * @param currentUserId 当前用户ID
//     * @param type          签到类型
//     */
//    private void signIn(String currentUserId, int type) {
//        toSubscribe(RxFactory.getUserServiceInstance(null)
//                        .signIn(currentUserId, type),
//                () ->
//                        catLoadingView.show(getSupportFragmentManager(), TAG),
//                creditsHistory -> {
//                    catLoadingView.dismissAllowingStateLoss();
//                    tv_sign_in_1.setEnabled(false);
//                    tv_sign_in_2.setEnabled(false);
//                    tv_sign_in_3.setEnabled(false);
//                    //TODO:xx
//                    int nextGet = 1;
////                    switch (creditsHistory.getUserId()) {
////                        case 0:
////                            nextGet = "3";
////                            break;
////                        case 1:
////                            nextGet = "3";
////                            break;
////                        case 2:
////                            nextGet = "3";
////                            break;
////                        case 3:
////                            nextGet = "5";
////                            break;
////                        case 4:
////                            nextGet = "6";
////                            break;
////                        case 5:
////                            nextGet = "6";
////                            break;
////                        case 6:
////                            nextGet = "8";
////                            break;
////                        default:
////                            nextGet = "签到类型异常";
////                            break;
////                    }
//                    final NormalDialog dialog = new NormalDialog(mContext);
//                    dialog.content("今日签到获得" + creditsHistory.getChange() +
//                            "枚口袋币,您当前总口袋币数为" + creditsHistory.getCredit() +
//                            "，您已累计签到" + creditsHistory.getType() +
//                            "天,明日连续签到将获得" + nextGet + "枚口袋币")
//                            .btnNum(1)
//                            .btnText("朕已阅")
//                            .showAnim(new BounceTopEnter())
//                            .dismissAnim(new SlideBottomExit())
//                            .show();
//                    dialog.setOnBtnClickL((OnBtnClickL) dialog::dismiss);
//                },
//                throwable -> {
//                    catLoadingView.dismissAllowingStateLoss();
//                    CommonUtils.showToast("签到异常");
//                    Logger.e(throwable.getMessage());
//                });
//    }
//
//    @Override
//    protected void onDestroy() {
//        AlibcTradeSDK.destory();
//        super.onDestroy();
//    }
//
//}
