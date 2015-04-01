package org.springfield.lou.application.types;

public abstract class Entity {
	protected int x;
	protected int y;
	
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public Entity(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
