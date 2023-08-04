package blblblbl.simplelife.user_preferences

import android.content.SharedPreferences

/**
 * @author Kirill Tolmachev 03.08.2023
 */
class UserPreferencesRepositoryImpl(
    sharedPreferences: SharedPreferences,
) : UserPreferencesRepository {

    override var hasUserSeenOnboarding by sharedPreferences.booleanDelegate("HAS_USER_SEEN_ONBOARDING_KEY", false)
}