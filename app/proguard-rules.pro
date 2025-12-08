# -----------------------
# General Android
# -----------------------
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes InnerClasses

-keep class android.support.v4.** { *; }
-keep class androidx.** { *; }

-dontwarn android.support.**
-dontwarn androidx.**

# -----------------------
# Kotlin
# -----------------------
-keepclassmembers class kotlin.Metadata { *; }

# -----------------------
# Hilt & Dagger
# -----------------------
# Keep Hilt Application
-keep class com.purple.aicalendar.AICalendar { *; }

# Keep Hilt-generated components
-keep class dagger.hilt.** { *; }
-keep class dagger.hilt.internal.** { *; }
-keep class dagger.hilt.android.internal.** { *; }

# Hilt ViewModel
-keep class * extends androidx.lifecycle.ViewModel { *; }
-keep class * extends dagger.hilt.android.internal.managers.ViewComponentManager$FragmentContextWrapper { *; }

# -----------------------
# Room Database
# -----------------------
# Keep Room entities & DAOs
-keep class androidx.room.** { *; }
-keep class com.purple.aicalendar.data.** { *; }

# Keep Room annotations
-keep class androidx.room.Entity { *; }
-keep class androidx.room.Dao { *; }
-keep class androidx.room.Database { *; }

# -----------------------
# DataStore / Preferences
# -----------------------
-keep class androidx.datastore.preferences.** { *; }
-keep class androidx.datastore.core.** { *; }

# -----------------------
# Retrofit / OkHttp / Gson
# -----------------------
# Retrofit
-keep class retrofit2.** { *; }
-keep class com.squareup.okhttp3.** { *; }

# Gson
-keep class com.google.gson.** { *; }
-keepattributes Signature

# Retrofit interfaces and models
-keep class com.purple.aicalendar.network.** { *; }

# -----------------------
# Misc
# -----------------------
-dontwarn kotlinx.coroutines.**
-dontwarn javax.annotation.**
-dontwarn kotlin.reflect.**
