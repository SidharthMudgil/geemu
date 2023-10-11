# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
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

## Keep the entry point of the app
#-keep class com.sidharth.geemu.GeemuApp { public *; }
#
## Keep the models used with Retrofit
#-keep class com.sidharth.geemu.models.** { *; }
#
## Keep Retrofit interfaces and their methods
#-keep class retrofit2.** { *; }
#-keepclasseswithmembers class * {
#    @retrofit2.http.* <methods>;
#}
#
## Keep Room database classes
#-keep class androidx.room.** { *; }
#
## Keep Hilt DI classes
#-keep class dagger.** { *; }
#-keep class javax.inject.** { *; }
#-keep class androidx.hilt.** { *; }
#
## Keep Parcelable and Serializable classes
#-keepnames class * implements android.os.Parcelable
#-keepclassmembers class * implements android.os.Parcelable {
#    public static final ** CREATOR;
#}
#-keepclassmembers class * implements java.io.Serializable {
#    static final long serialVersionUID;
#    private static final java.io.ObjectStreamField[] serialPersistentFields;
#    private void writeObject(java.io.ObjectOutputStream);
#    private void readObject(java.io.ObjectInputStream);
#    java.lang.Object writeReplace();
#    java.lang.Object readResolve();
#}
#
## Keep classes used for data binding
#-keep class androidx.databinding.** { *; }
#
## Keep specific libraries that require special handling
#-keep class io.coil.** { *; }
#-keep class com.devbrackets.android.** { *; }
#-keep class com.airbnb.lottie.** { *; }
#
## Keep any native methods and their classes
#-keepclasseswithmembernames class * {
#    native <methods>;
#}
#
## Specify the entry points for code shrinking
#-keepclasseswithmembers class * {
#    public static void main(java.lang.String[]);
#}
#
## Prevent obfuscation of classes that are used in XML layouts
#-keepclassmembers class **.R$* {
#    public static <fields>;
#}
#
## Preserve annotations
#-keepattributes *Annotation*
#
## Preserve the identity of Serializable and Parcelable implementations during obfuscation
#-keep class com.google.gson.examples.android.model.** { *; }
#
## Prevent obfuscation of types which use Guava
#-keep class com.google.common.collect.Lists