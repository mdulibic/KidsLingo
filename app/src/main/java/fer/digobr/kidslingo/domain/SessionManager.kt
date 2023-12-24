package fer.digobr.kidslingo.domain

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SessionManager @Inject constructor(@ApplicationContext private val appContext: Context) {

    companion object {
        private const val PREFERENCES_APP = "prefs_app"
        private const val USERNAME = "username"
        private const val LANGUAGE = "language"
        private const val CATEGORY = "category"
        private const val LEVEL = "level"
    }

    private val appPreferences: SharedPreferences by lazy {
        appContext.getSharedPreferences(PREFERENCES_APP, Context.MODE_PRIVATE)
    }

    var username: String?
        get() = appPreferences.getString(USERNAME, "Korisnik")
        set(value) {
            appPreferences.edit().putString(USERNAME, value).apply()
        }

    var language: String?
        get() = appPreferences.getString(LANGUAGE, "en")
        set(value) {
            appPreferences.edit().putString(LANGUAGE, value).apply()
        }

    var category: String?
        get() = appPreferences.getString(CATEGORY, "animals")
        set(value) {
            appPreferences.edit().putString(CATEGORY, value).apply()
        }

    var level: String?
        get() = appPreferences.getString(LEVEL, "2")
        set(value) {
            appPreferences.edit().putString(LEVEL, value).apply()
        }
}
