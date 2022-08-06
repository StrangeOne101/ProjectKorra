# Changelog
A list of changes made to ForkRoku over time

## v1.15
- Implemented FastMath
    - ProjectKorra now uses a FastMath implementation to speed up sin, cosine, power and square root calculations used in abilities
    - Can be used by addons as well
- Updated EarthArmor colors
    - Has better colors overall and supports 1.19 blocks too
    - Now supports 246 block colors
- Fixed not setting up the bending board and prefixes for newly joined players
- Fixed loading BendingPlayers on the main thread instead of a parallel thread
- Fixed errors not canceling the CompletableFuture for getting OfflineBendingPlayers
- Fixed BendingPlayer#getOrLoadOffline causing server stall
- Fixed Night and Day factors not existing for 90% of abilities
- Fixed the blue fire factor not being used for a lot of abilities
- Made the blue fire factor not affect lightning or combustion abilities
- Fixed earth abilities not using the Metal power factor for damage when using a metal source
- Fixed outdated combo addons causing all other combos to fail to load
- Changed cached fixes in PK listener to use Sets instead of Lists (faster)
- Fixed combos being triggered from block placing

## v1.14
- Made the combination to trigger combos completely configurable

## v1.13
- Added OfflineBendingPlayer class
    - Allows commands to be run on offline players
    - Players who log off will now have their data converted to an offline version of BendingPlayer, so if they log back in before the data is uncached, the data won't have to be fetched from the database again.
    - Accessible with `BendingPlayer.getOrLoadOffline(offlinePlayer)` (the main thread will wait till this returns)
    - Accessible with `BendingPlayer.getOrLoadOfflineAsync(offlinePlayer)` which returns a CompletedFuture\<OfflineBendingPlayer\>, allowing you to chain your functions that need to be run
- Added TempFallingBlock class
- Adjusted PhaseChange config slightly
- Fixed bending not remaining toggled when relogging
- Fixed vanished players being visible in tab completion
- Fixed untranslated line for failing to bind when you have a multiability bound
- Fixed temp snow blocks not preventing fall damage in HydroSink
- Fixed memory leak where BendingPlayer objects are never unloaded from memory

## v1.12
- Made addons who don't define permissions for themselves have permissions defined for them instead
- Made the \[ProjectKorra\] prefix in front of commands be configurable
- Fixed custom subelements causing errors when being bound
- Fixed hex color coded ability names displaying as black in bending preview (action bar) 

## v1.11
- Added config options for the color of each individual subelement
- Added hex color codes for subelements
- Added MultiSubElement class
- Added a `/b cooldown` command to view/set/reset cooldowns on a player
- Updated TimeUtil. Adds a method to convert formatted time back to a long
- Fixed "Proper Usage: xxx" in commands being untranslatable
- Fixed addon jars being unable to be deleted in windows while the server was on (PK now no longer have a hold on addon ability jars)
- Fixed cooldowns halting the main thread when saving
- Fixed ALL cooldowns being saved to the database
- Fixed branding messages not supporting hex colors
- Fixed subs not reloading their colors from config on pk reload
- Disabled the update command
- Removed the auto announcer
- Updated the branding to be fork Roku

## v1.10
- Reverted 1.17 requirement of the plugin to 1.16
- Made the config now generate based on what version the server is on
- Added PlayerSwingEvent. An event for addons to hook onto for when the player swings (#1210)
- Added BendingRegionProtection class. Allows plugins to add region protection support to ProjectKorra without ProjectKorra doing anything (#1210)
- Added GriefDefender support (#1208)
- Add EarthSmash and ChargedFireBlast min/max damage options (#1203)
- Switched bukkit ChatColor to bungee ChatColor. This allows hex color codes to be supported. (#1210)
- Fixed Extraction not working for 1.16
- Fixed addons registering their listeners twice on /b reload
- Fixed a range if 0 crashing the server
- Fixed presets halting main thread when being created (caused a lot of lag)
- Fixed presets halting main thread when being deleted
- Fixed preset tabbing not working for binding and deleting

## PK 1.9.3
This fork started from PK 1.9.3. No more history to see!
  


