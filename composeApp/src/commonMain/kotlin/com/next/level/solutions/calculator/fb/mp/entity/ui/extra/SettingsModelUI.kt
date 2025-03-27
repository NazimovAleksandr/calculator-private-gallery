package com.next.level.solutions.calculator.fb.mp.entity.ui.extra

import androidx.compose.ui.graphics.vector.ImageVector
import calculator_privategallery.composeapp.generated.resources.Res
import calculator_privategallery.composeapp.generated.resources.change_password
import calculator_privategallery.composeapp.generated.resources.change_security_question
import calculator_privategallery.composeapp.generated.resources.language
import calculator_privategallery.composeapp.generated.resources.more
import calculator_privategallery.composeapp.generated.resources.privacy_policy
import calculator_privategallery.composeapp.generated.resources.rate_5_stars
import calculator_privategallery.composeapp.generated.resources.security
import calculator_privategallery.composeapp.generated.resources.share_with_friends
import calculator_privategallery.composeapp.generated.resources.tos
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.ChangePassword
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.ChangeSecurityQuestion
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Language
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Rate5Stars
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Share
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Privacy
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableMap
import org.jetbrains.compose.resources.StringResource

enum class SettingsType(val title: StringResource) {
  Security(title = Res.string.security),
  Language(title = Res.string.language),
  More(title = Res.string.more),
}

enum class SettingsModelUI(
  val type: SettingsType,
  val icon: ImageVector,
  val title: StringResource,
) {
  /**
   * Security
   */
  ChangePassword(
    type = SettingsType.Security,
    icon = MagicIcons.All.ChangePassword,
    title = Res.string.change_password,
  ),
  ChangeSecurityQuestion(
    type = SettingsType.Security,
    icon = MagicIcons.All.ChangeSecurityQuestion,
    title = Res.string.change_security_question,
  ),

  /**
   * Language
   */
  Language(
    type = SettingsType.Language,
    icon = MagicIcons.All.Language,
    title = Res.string.language,
  ),

  /**
   * More
   */
  Rate5Stars(
    type = SettingsType.More,
    icon = MagicIcons.All.Rate5Stars,
    title = Res.string.rate_5_stars,
  ),
  Share(
    type = SettingsType.More,
    icon = MagicIcons.All.Share,
    title = Res.string.share_with_friends,
  ),
  Privacy(
    type = SettingsType.More,
    icon = MagicIcons.All.Privacy,
    title = Res.string.privacy_policy,
  ),
  Tos(
    type = SettingsType.More,
    icon = MagicIcons.All.Privacy,
    title = Res.string.tos,
  ),
}

fun getSettingsItems(): ImmutableMap<SettingsType, ImmutableList<SettingsModelUI>> = SettingsModelUI.entries
  .groupBy { it.type }
  .mapValues { it.value.toImmutableList() }
  .toImmutableMap()