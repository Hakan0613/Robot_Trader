package Simulator;

import java.util.ArrayList;

public class AlgoPrediction {
	enum algo{
		Hasard
	}
	private ArrayList<Cotation> historiqueCotation;
	public AlgoPrediction() {
		// TODO Auto-generated constructor stub
	}
	public boolean prediction(Object coteActuel, algo choix) {
		// TODO Auto-generated constructor stub
		switch (choix) {
			case Hasard:
				return this.hasard();
		default:
			return false;
		}
	}
	
	private boolean hasard() {
		return Math.random()<0.1;
	}
}
