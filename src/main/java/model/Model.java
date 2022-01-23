package model;

import java.util.List;

import controllers.Observer;

public interface Model {
	abstract void notifyObservers();
}
