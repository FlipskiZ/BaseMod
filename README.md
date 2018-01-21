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
Default hotkey is '`', can be changed from BaseMod's settings screen.

`info` toggle Settings.isInfo
`relic [id]` generate relic
`relic r [id]` lose relic

## For Modders ##
* Inset info about hooks here...

## Changelog ##
#### v1.0.0 ####
* Initial release

#### v1.0.1 ####
* Scale console by Settings.scale
* Prevent game hotkeys from activating while console is visible

#### v1.1.0 ####
* Add mod badges
* Add support for mod settings screens
* Add `relic` console command
* Add option to change console keybind to BaseMod settings screen
