# Java Puzzle Game Engine

A console grid-puzzle game built to be extended. Each level is written in a small custom language that an ANTLR 4 grammar parses. A level can load Java plugins at runtime and run embedded Python scripts, and both talk to the game through one shared API. The player explores a grid hidden by fog, collects items, gets past obstacles that need certain items, and tries to reach the goal.

Built for the Software Architecture and Extensible Design unit at Curtin University (2025).

## What it shows

- **A domain-specific language.** Levels live in `.map` files, which `GameConfig.g4` parses. A level declares the grid size, start, goal, items and their messages, obstacles and the items each one requires, the plugins to load, and inline Python scripts.
- **A plugin architecture.** The `api` module defines `GameAPI` and its callback interfaces: `MoveCallback`, `ItemCallback`, `ObstacleCallback` and `MenuCallback`. Plugins are separate Gradle subprojects written only against `api`. The engine loads them by reflection from the class names in the map file, so new plugins need no change to the engine.
- **Embedded scripting.** `script !{ ... }` blocks run in Jython, with the game API bound to `api`. The demo level uses scripts to reveal every item and the goal when you pick up the map, and to award a bonus item once you have collected five items or crossed five obstacles in total.
- **Internationalisation.** Messages come from resource bundles: `en_US`, plus a `th` bundle with placeholder text. The `locale` command switches language and date format while the game runs, and each move advances the in-game date by one day. Item names are compared after Unicode NFKC normalisation, and the file's encoding (UTF-8, 16 or 32) is chosen from its extension.
- **Code quality.** PMD 7 runs on every module.

## Example level

```
size (10,10)
start (1,5)
goal (9,8)

item "Key" {
    at (3,3)
    message "Key for obstacle."
}

obstacle {
    at (1,1), (2,3)
    requires "Key"
}

plugin edu.curtin.gameplugins.Teleport

script !{
from edu.curtin.api import ItemCallback
class Hello(ItemCallback):
    def onItemAcquired(self, itemName):
        print("You found " + itemName)
api.registerItemCallback(Hello())
}
```

## Included plugins

| Plugin | What it does |
|---|---|
| `Teleport` | Adds a menu option that moves the player to a random cell that isn't an obstacle or the goal. It can be used once. |
| `Penalty` | If more than 5 seconds pass between moves, drops a new obstacle next to the player. |

## Project layout

```
api/             GameAPI and the callback interfaces that plugins code against
app/             Game engine (PuzzleGame), ANTLR grammar, resource bundles
teleportplugin/  Teleport plugin
penaltyplugin/   Penalty plugin
demoinput.utf8.map   Demo level using both plugins and two scripts
testinput.utf8.map   Messy input with bad plugin names and an invalid script, for testing error handling
```

## Running it

You need JDK 21.

```bash
./gradlew run --args="../demoinput.utf8.map"
```

On Windows, use `gradlew` instead of `./gradlew`. Move with `w` `a` `s` `d`, type a menu number to use a plugin option, type `locale` to switch language, and type `quit` to exit. On the grid, `P` is you, `#` is hidden, `I` is an item, `X` is an obstacle and `G` is the goal.
