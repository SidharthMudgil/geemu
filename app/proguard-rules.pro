-dontobfuscate

-keep class com.sidharth.geemu.domain.model.** { *;}
-keep class com.sidharth.geemu.core.enums.** { *; }

-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

-keep class coil.request.CachePolicy { *; }

-keep class android.app.Application {
        public <fields>;
        private <fields>;
       public <methods>;
    }

-keep public class * extends android.app.Application