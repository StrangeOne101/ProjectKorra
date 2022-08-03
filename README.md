# ProjectKorra | Fork Roku

![Core Icon](https://i.imgur.com/0mWZWFT.png)

## About Fork Roku

Fork Roku is a fork of the popular plugin [ProjectKorra](https://www.spigotmc.org/resources/projectkorra.12071/). It contains a number of features and changes that are not available in the original plugin.

The development team are no longer working on the current PK Core and are only working on a rework. This rework is going in the wrong direction with things, so this aims to continue the plugin with the original code, continuing to support all existing addons, while making changes that will benefit the plugin and its community.

If you want to see the changelog of this fork, [go here](https://github.com/StrangeOne101/ProjectKorra/blob/master/CHANGELOG.md)

For screenshots, [go here](https://imgur.com/a/U5Jav9h
)

## Changes from ProjectKorra

- Added hex colors for each subelement
- Added a `/b cooldown` command to view/set/reset cooldowns for a player
- Made the config generate blocks based on the current server version
- Better supports 1.16
- The \[ProjectKorra\] prefix in front of commands is completely configurable
- Commands can affect offline players
- The combinations used to trigger combos is configurable
- Removed autoannouncer

## Compatibility

✅ JedCore<br>
✅ ProjectAddons* (LeafStorm)<br>
✅ Hyperion* (IceBreath, IceCrawl, EarthLine)<br>
✅ Addon Moves<br>
🔁 BendingGUI<br>
🚫 Spirits<br>

✅ with star = works with minor errors for the moves listed, but can be ignored. Compiling them with ForkRoku is enough to stop the errors (no code changes needed)<br>
🔁 = Alternate working version available<br>
🚫 = Doesn't work but can patch at request<br>
❔ = Untested

Some plugins break due to hex color codes being supported. This can easily be fixed, and we are happy to help you patch your own plugins to support Fork Roku.


## Developer Changes
These are changes that benefit developers of side plugins and addon abilities

- Added TempFallingBlock class
- Added PlayerSwingEvent for addon developers (better than the interact or animation event as it contains all the checks PK does to make sure users aren't interacting with anything)
- Added OfflineBendingPlayer class for offline player data
- Cleaned up GeneralMethods
- Made addons who don't define permissions have permissions defined for them instead
- Added BendingRegionProtection class so 3rd parties can add bending support
- Added MultiSubElement class - a class for subelements that have more than 1 parent element (e.g. mudbending for water and earth)

### Fixes
- Fixed the server crashing when an ability's range is 0
- Fixed addons registering their listeners twice on /b reload
- Fixed addon ability jars being locked so they couldn't be deleted while the server was on (excluding Linux, which doesn't care if it SHOULD delete it or not)
- Fixed presets halting main thread when being created (caused a lot of lag)
- Fixed presets halting main thread when being deleted
- Fixed preset tabbing not working for binding and deleting
- Fixed vanished players being visible in tab completion
- Fixes BendingBoard IllegalStateException thrown when team is unregistered twice
- Fixed Extraction not working on 1.16 servers
- Fixed cooldowns halting the main thread when saving (and causing a lot of lag)
- Fixed ALL cooldowns being saved to the database
- Fixed bending boards showing up in disabled worlds when you log in
- Fixed bending toggle reminder not being translatable
- Fixed "Proper Usage: xxx" in commands being untranslatable
- Fixes addon abilities that do not define their own permissions being unusable until the permission is set by the server owner
- Fixed subs not reloading their colors from config on pk reload
- Fixed branding messages not supporting hex colors
- Fixed bending toggle reminder not being translatable
- Fixed untranslated line for failing to bind when you have a multiability bound
- Fixed temp snow blocks not preventing fall damage in HydroSink (the water passive)
- Fixed memory leak where BendingPlayer objects are never unloaded from memory
- Fixed toggled bending not persisting over relogs
- Fixed `/b choose avatar` not giving all 4 elements but instead giving the internal "Avatar" element that is useless
