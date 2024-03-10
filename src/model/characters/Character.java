package model.characters;

import java.awt.Point;

import application.Play;
import model.world.CharacterCell;
import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;
import javafx.application.Platform;
import javafx.scene.control.Alert;

public abstract class Character {

	private String name;
	private int maxHp;
	private int currentHp;
	private Point location;
	private int attackDmg;
	private Character target;

	public Character(String name, int maxHp, int attackDamage) {
		this.name = name;
		this.maxHp = maxHp;
		this.attackDmg = attackDamage;
		this.currentHp = maxHp;
	}

	public int getCurrentHp() {
		return currentHp;
	}

	public void setCurrentHp(int currentHp) {
		if (currentHp <= 0) {
			this.currentHp = 0;
			onCharacterDeath();
			
		} else if (currentHp > maxHp) {
			this.currentHp = maxHp;
		} else
			this.currentHp = currentHp;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public Character getTarget() {
		return target;
	}

	public void setTarget(Character target) {
		this.target = target;
	}

	public String getName() {
		return name;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public int getAttackDmg() {
		return attackDmg;
	}

	public void attack() throws NotEnoughActionsException,
			InvalidTargetException {
		getTarget().setCurrentHp(getTarget().getCurrentHp() - getAttackDmg());
		getTarget().defend(this);
		this.setTarget(null);
	}

	public void defend(Character c) {
		c.setCurrentHp(c.getCurrentHp() - getAttackDmg() / 2);
	}

	public void onCharacterDeath() {
		Point p = this.getLocation();
		
		if (this instanceof Zombie) {
			Game.zombies.remove(this);
			Game.spawnNewZombie();
		} else if (this instanceof Hero) {
			Game.heroes.remove(this);
		}
		if ( Game.heroes.size() == 0) {
//			Alert alert = new Alert(Alert.AlertType.WARNING);
//			alert.setTitle("Game Over");
//			alert.setHeaderText("You have lost :(");
//			alert.showAndWait();
//			Platform.exit();
		} else {
			if(this == Play.selectedHero) {
				Play.selectedHero = Game.heroes.get(0);
			}
		}
		Game.map[p.x][p.y] = new CharacterCell(null);
	}

}
