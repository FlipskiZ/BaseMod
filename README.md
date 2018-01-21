# BaseMod #
BaseMod provides a number of hooks and a console.

## Requirements ##
#### General Use ####
* Java 8+

#### Development ####
* Java 8+
* Maven

## Building ##
1. Run `mvn package`

## Installation ##
1. Copy `target/BaseMod.jar` to your ModTheSpire mods directory.

## Console ##
Default hotkey is `` ` ``, can be changed from BaseMod's settings screen.
* `info` toggle Settings.isInfo
* `relic [id]` generate relic
* `relic r [id]` lose relic

## For Modders ##
### Hooks ###
#### Subscription handling ####
* BaseMod.subscribeTo...()
* BaseMod.unsubscribeFrom...()

#### Subscriptions ####
* PostInitialize() - One time only, at the end of CardCrawlGame.initialize()
* Render(SpriteBatch) - Under tips and the cursor, above everything else
* PostRender(SpriteBatch) - Above everything
* PreUpdate() - Immediately after input is handled
* PostUpdate() - Immediately before input is disposed

### Mod Badges ###
Currently only has full support for the badges themselves. Clicking any will open the BaseMod settings page for now.
* BaseMod.registerModBadge(Texture texture, String modName, String author, String description)

## Changelog ##
#### v1.0.0 ####
* Initial release

#### v1.0.1 ####
* Scale console by Settings.scale
* Prevent game hotkeys from activating while console is visible

#### v1.1.0 ####
* Add mod badges
* Add initial support for mod settings screens
* Add `relic` console command
* Add option to change console keybind to BaseMod settings screen

#### v1.1.1 #####
* Scale mod badges by Settings.scale
* Scale mod settings screens by Settings.scale