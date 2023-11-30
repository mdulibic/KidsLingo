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
}
