/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COMP417.PartA.util;

public class Pair<X, Y> {
	private final X a;

	private final Y b;

	public Pair(X a, Y b) {
		this.a = a;
		this.b = b;
	}

	public X getFirst() {
		return a;
	}

	public Y getSecond() {
		return b;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Pair<?, ?>) {
			Pair<?, ?> p = (Pair<?, ?>) o;
			return a.equals(p.a) && b.equals(p.b);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return a.hashCode() + 31 * b.hashCode();
	}

	@Override
	public String toString() {
		return "< " + getFirst().toString() + " , " + getSecond().toString()
				+ " > ";
	}
}
