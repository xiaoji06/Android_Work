package com.xiaoji.android_work.androidstudio;

public class GradleSetting {
    /**
     *  #####配置Gradle自定义属性#####
     *
     *  1.工程根目录创建  config.gradle
     *  2.设置相关参数
     *  3.在根build.gradle 引用  apply from:'config.gradle'
     *  4.在主Modle中引用 rootProject.ext.xxx["xxx"]
     *
     *  #####配置Gradle静态常量#####
     *  自定义Gradle常量
     *  buildConfigField("boolean","LOG_DEBUG","true")
     *  buildConfigField("String","LOG_TAG","\"Xiao\"")
     *
     *  使用
     *  BuildConfig.LOG_DEBUG
     *
     *  #####提升Gradle构建速度#####
     *  Setting -> Build -> Compiler -> -PdevBuild
     *  android {
     *      //调试
     *     if (rootProject.hasProperty('devBuild')){
     *         splits.abi.enable=false
     *         splits.density.enable=false
     *     }
     * }
     *
     *  //调试 (去除多余资源)
     *  defaultConfig {
     *      resConfigs("zh","xxhdpi")
     *  }
     *
     *  gradle.properties
     *  #通过调整这个数值来设置AS内存优化
     *  org.gradle.jvmargs=-Xmx1536m
     *  #开启Gradle构建缓存
     *  org.gradle.caching=true
     */
}
