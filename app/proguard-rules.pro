# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


-dontwarn com.google.android.material.**
-keep class com.google.android.material.** { *; }

-dontwarn androidx.**
-keep class androidx.** { *; }
-keep interface androidx.** { *; }

-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }

-dontwarn android.support.v7.**
-keep class android.support.v7.** { *; }


-keep class androidx.appcompat.widget.Toolbar { *** mMenuView; }
-keep class android.support.v7.widget.ActionMenuView { *** mPresenter; }
-keep class android.support.v7.widget.ActionMenuPresenter { *** mOverflowButton; }
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes EnclosingMethod
-keepattributes InnerClasses
-dontwarn com.google.errorprone.annotations.**

-keep class com.google.gson.** { *; }
-keep public class com.google.gson.** {public private protected *;}
-keep class retrofit.** { *; }
-dontwarn com.squareup.okhttp.*
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }


# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions
#-assumenosideeffects class android.widget.Toast {
#    public static *** makeText(...);
#    public *** show();
#}

-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception

-printmapping mapping.txt


# This will strip `Log.v`, `Log.d`, and `Log.i` statements and will leave `Log.e` statements intact.

-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int d(...);
    public static int i(...);
}


-keep class com.thelumiereguy.matchesapp.data.db.enitity.** { *; }
-keep class com.thelumiereguy.matchesapp.domain.enitity.** { *; }
-keep class com.thelumiereguy.matchesapp.ui.ui_models.** { *; }