# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}

# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
-renamesourcefileattribute SourceFile

-keep public class cn.neday.sheep.R$*{
    public static final int *;
}

# Bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
-keep class android.support.**{*;}

# Analytics & Push
-keep class com.umeng.** {*;}
-keepclassmembers class * {
    public <init> (org.json.JSONObject);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# for DexGuard only
-keepresourcexmlelements manifest/application/meta-data@value=GlideModule

# 阿里百川
-keepattributes Signature
-keep class sun.misc.Unsafe { *; }
-keep class com.alibaba.** {*;}
-dontwarn com.alibaba.**
-keep class com.alipay.** {*;}
-dontwarn com.alipay.**
-keep class com.taobao.** {*;}
-dontwarn com.taobao.**
-keep class com.ut.** {*;}
-dontwarn com.ut.**
-keep class com.ta.** {*;}
-dontwarn com.ta.**
-keep class mtopsdk.** {*;}
-dontwarn mtopsdk.**
-keep class org.json.** {*;}
-keep class com.ali.auth.**  {*;}
-dontwarn com.alimama.**
-keep class com.alimama.** {*;}
-keeppackagenames com.alimama.tunion.sdk.**
-keeppackagenames com.alimama.tunion.sdk.**
-keep class com.alimama.tunion.sdk.** {
    public <fields>;
    public <methods>;
}

# Mob Share
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class **.R$* {*;}
-keep class **.R{*;}
-keep class com.mob.**{*;}
-keep class m.framework.**{*;}
-keep class com.bytedance.**{*;}
-dontwarn cn.sharesdk.**
-dontwarn com.sina.**
-dontwarn com.mob.**
-dontwarn **.R$*

# Mob SMS
-keep class com.mob.**{*;}
-keep class cn.smssdk.**{*;}
-dontwarn com.mob.**