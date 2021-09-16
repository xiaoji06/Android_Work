package com.xiaoji.android_work;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.Locale;

public class LanguageUtils {

    /**
     * 语言设置KEY
     */
    public static final String LANGUAGE_KEY = "LANGUAGE_KEY";

    /**
     * 系统默认语言
     */
    public static Locale systemLocale;

    /**
     * 应用默认语言
     */
    private static Locale defaultLocala = Locale.CHINESE;

    /**
     * 语言类型：
     *  此处支持2种语言类型，更多可以自行添加。
     */
    private static ArrayList<Locale> languagesList = new ArrayList<Locale>(2) {{
        add(Locale.CHINESE);
        add(Locale.ENGLISH);
    }};

    /**
     * 获取指定语言的locale信息
     */
    private static Locale getLocaleByLanguage(int locale) {
        switch (locale) {
            case 0:
                return getSystemLocale();
            case 1:
                return Locale.CHINESE;
            case 2:
                return Locale.ENGLISH;
            default:
                return defaultLocala;
        }
    }

    /**
     * 设置页面进行设置时使用的方法
     *
     * @param context
     * @param language
     * @return
     */
    public static void setLanguageConfiguration(Context context,int language) {
        if (context == null) {
            return;
        }
        //设置activity
        setLanguage(context,language);
        //设置application
        setLanguage(context.getApplicationContext(),language);
    }

    /**
     * 设置语言信息
     *  在BaseActivity基类中重新方法 attachBaseContext（），在之后调用
     *  protected void attachBaseContext(Context newBase) {
     *      super.attachBaseContext(newBase);
     *      LanguageUtils.setActivityConfiguration(newBase, MMKV.defaultMMKV().decodeInt(LanguageUtils.LANGUAGE_KEY));
     *  }
     * 说明：
     * attachBaseContext可以保证页面加载时修改语言信息
     * @param context application context
     */
    public static Context setActivityConfiguration(Context context,int language) {
        if (context == null) {
            return context;
        }
        return setLanguage(context,language);
    }

    /**
     * 设置语言信息
     * 该方法建议在 Application attachBaseContext和onConfigurationChange中调用，
     * 这里主要使用applicationContext，以防getApplicationContext().getResources().getString(R.string.xxxx)不生效
     * attachBaseContext可以保证应用加载时修改语言信息，
     * 而onConfigurationChange则是为了对应横竖屏切换时系统更新Resource的问题
     * Application:
     *     protected void attachBaseContext(Context base) {
     *         super.attachBaseContext(base);
     *         MMKV.initialize(base);
     *         //保存原始系统的语言
     *         LanguageUtils.systemLocale = LanguageUtils.getSysLocale();
     *         LanguageUtils.setApplicationConfiguration(base,MMKV.defaultMMKV().decodeInt(LanguageUtils.LANGUAGE_KEY));
     *     }
     *
     *     public void onConfigurationChanged(@NonNull Configuration newConfig) {
     *         super.onConfigurationChanged(newConfig);
     *         //保存原始系统的语言
     *         LanguageUtils.systemLocale = LanguageUtils.getSysLocale(newConfig);
     *         LanguageUtils.setApplicationConfiguration(getApplicationContext(),MMKV.defaultMMKV().decodeInt(LanguageUtils.LANGUAGE_KEY));
     *     }
     * @param applicationContext
     * @param language
     * @return
     */
    public static Context setApplicationConfiguration(Context applicationContext,int language){
        if (applicationContext == null) {
            return applicationContext;
        }
        return setLanguage(applicationContext,language);
    }

    /**
     * 进行语言设置
     *
     * @param context
     * @param language
     */
    private static Context setLanguage(Context context,int language){
        // 获得res资源对象
        Resources resources = context.getResources();
        // 获得屏幕参数：主要是分辨率，像素等。
        DisplayMetrics metrics = resources.getDisplayMetrics();
        // 获得配置对象
        Configuration configuration = resources.getConfiguration();
        // 如果没有准备系统语言的语言资源则默认使用中文
        Locale locale = getLocaleByLanguage(language);

        //Android 7.0以上的方法
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(locale);
            configuration.setLocales(new LocaleList(locale));
            context = context.createConfigurationContext(configuration);
            //实测，updateConfiguration这个方法虽然很多博主说是版本不适用
            //但是我的生产环境androidX+Android Q环境下，必须加上这一句，才可以通过重启App来切换语言
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            //Android 4.1 以上方法
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        resources.updateConfiguration(configuration,metrics);
        return context;
    }

    /**
     * 如果当前系统语言，做了语言适配则使用，否则默认使用中文
     *
     * @return
     */
    private static Locale getSystemLocale() {
        for (Locale locale : languagesList){
            if (locale.getLanguage().equals(systemLocale.getLanguage())){
                return locale;
            }
        }
        return defaultLocala;
    }

    /**
     * 获取系统首选语言
     * 注意：该方法获取的是用户实际设置的不经API调整的系统首选语言
     * @return
     */
    public static Locale getSysLocale() {
        Locale locale;
        //7.0以上获取系统首选语言
        if (Build.VERSION.SDK_INT >= 24) {
            /*
             * 以下两种方法等价，都是获取经API调整过的系统语言列表（可能与用户实际设置的不同）
             * 1.context.getResources().getConfiguration().getLocales()
             * 2.LocaleList.getAdjustedDefault()
             */
            // 获取用户实际设置的语言列表
            locale = LocaleList.getDefault().get(0);
        } else {
            //7.0以下直接获取系统默认语言
            // 等同于context.getResources().getConfiguration().locale;
            locale = Locale.getDefault();
        }

        return locale;
    }

    /**
     * 获取系统首选语言
     * 注意：该方法获取的是用户实际设置的不经API调整的系统首选语言
     * @param newConfig
     * @return
     */
    public static Locale getSysLocale(Configuration newConfig) {
        Locale locale;
        //7.0以上获取系统首选语言
        if (Build.VERSION.SDK_INT >= 24) {
            /*
             * 以下两种方法等价，都是获取经API调整过的系统语言列表（可能与用户实际设置的不同）
             * 1.context.getResources().getConfiguration().getLocales()
             * 2.LocaleList.getAdjustedDefault()
             */
            // 获取用户实际设置的语言列表
            locale = newConfig.getLocales().get(0);
        } else {
            //7.0以下直接获取系统默认语言
            // 等同于context.getResources().getConfiguration().locale;
            locale = newConfig.locale;
        }
        return locale;
    }
}
