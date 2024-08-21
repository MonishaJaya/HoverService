package demo.model.request;

import java.util.List;

public class HooverRequest {

	private int[] roomSize;

	private int[] coords;

	private int[][] patches;

	private String instructions;

	public int[] getRoomSize() {
		return roomSize;
	}

	public void setRoomSize(int[] roomSize) {
		this.roomSize = roomSize;
	}

	public int[] getCoords() {
		return coords;
	}

	public void setCoords(int[] coords) {
		this.coords = coords;
	}

	public int[][] getPatches() {
		return patches;
	}

	public void setPatches(int[][] patches) {
		this.patches = patches;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

}
