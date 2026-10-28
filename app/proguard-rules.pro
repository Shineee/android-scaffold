# ---------- kotlinx-serialization ----------
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.**
-keepclassmembers class com.scaffold.app.** {
    *** Companion;
}
-keepclasseswithmembers class com.scaffold.app.** {
    kotlinx.serialization.KSerializer serializer(...);
}
-keep,includedescriptorclasses class com.scaffold.app.**$$serializer { *; }

# ---------- Retrofit ----------
-keepattributes Signature, Exceptions
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn javax.annotation.**
-dontwarn kotlin.Unit
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

# ---------- OkHttp ----------
-dontwarn okhttp3.**
-dontwarn okio.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# ---------- Room ----------
-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.room.paging.**

# ---------- Kotlin / coroutines ----------
-dontwarn kotlin.**
-dontwarn kotlinx.coroutines.**
-keepclassmembers class kotlinx.coroutines.** {
    volatile <fields>;
}
-keepclassmembers class ** {
    @kotlinx.coroutines.ExperimentalCoroutinesApi <methods>;
}

# ---------- R8 默认 ----------
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile
