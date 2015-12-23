/*
 * Copyright (C) 2015 Bernardo Sulzbach
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mafagafogigante.dungeon.entity.creatures;

import org.mafagafogigante.dungeon.io.Split;
import org.mafagafogigante.dungeon.io.Writer;
import org.mafagafogigante.dungeon.logging.DungeonLogger;
import org.mafagafogigante.dungeon.spells.Spell;
import org.mafagafogigante.dungeon.util.Matches;
import org.mafagafogigante.dungeon.util.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Spellcaster implementation the Hero uses.
 */
public class HeroSpellcaster implements Serializable, Spellcaster {

  private final Hero hero;
  private final List<Spell> spellList = new ArrayList<>();

  public HeroSpellcaster(Hero hero) {
    this.hero = hero;
  }

  @Override
  public List<Spell> getSpellList() {
    return spellList;
  }

  @Override
  public boolean knowsSpell(Spell spell) {
    return spellList.contains(spell);
  }

  @Override
  public void learnSpell(Spell spell) {
    if (knowsSpell(spell)) {
      DungeonLogger.warning("called learnSpell with " + spell.getName() + " which is already known.");
    } else {
      DungeonLogger.info("Learned " + spell.getName() + ".");
      spellList.add(spell);
    }
  }

  @Override
  public void parseCast(String[] arguments) {
    if (arguments.length > 0) {
      Split split = Split.splitOnOn(Arrays.asList(arguments));
      List<String> spellMatcher = split.getBefore();
      List<String> targetMatcher = split.getAfter();
      String[] spellMatcherArray = spellMatcher.toArray(new String[spellMatcher.size()]);
      String[] targetMatcherArray = targetMatcher.toArray(new String[targetMatcher.size()]);
      Matches<Spell> matches = Utils.findBestCompleteMatches(spellList, spellMatcherArray);
      if (matches.size() == 0) {
        Writer.write("That did not match any spell you know.");
      }
      if (matches.getDifferentNames() == 1) {
        Spell spell = matches.getMatch(0);
        DungeonLogger.info("Casted " + spell.getName().getSingular() + ".");
        spell.operate(hero, targetMatcherArray);
      } else if (matches.getDifferentNames() > 1) {
        Writer.write("Provided input is ambiguous in respect to spell.");
      }
    } else {
      Writer.write("Cast what?");
    }
  }

}
