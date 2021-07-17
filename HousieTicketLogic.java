package com.skc;

import java.util.Random;
import java.util.Scanner;

public class HousieTicketLogic {

	int ticket[][];

	public void inlialise(int row, int cols) {
		this.ticket = new int[row][cols];
	}

	public int getRowCount(int cols, int r) {
		int count = 0;
		for (int i = 0; i < cols; i++) {
			if (this.ticket[r][i] != 0)
				count++;
		}

		return count;
	}

	public int getRand(int min, int max) {
		Random rand = new Random();
		return rand.nextInt(max - min + 1) + min;
	}

	public int getNumberOfElementsFilled(int rows, int cols) {
		int count = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (this.ticket[i][j] != 0)
					count++;
			}
		}
		return count;
	}

	public int getColCount(int c, int rows) {
		int count = 0;
		for (int i = 0; i < rows; i++) {
			if (this.ticket[i][c] != 0)
				count++;
		}
		return count;
	}

	public void updateValue(int rows, int cols, int value) {
		for (int i = 0; i < this.ticket.length; i++) {
			if (this.ticket[i][cols] == value) {
				return;
			}
		}

		if (this.ticket[rows][cols] == 0) {
			this.ticket[rows][cols] = value;
		}
	}

	public void printTicket(int rows, int cols) {
		for (int r = 0; r < rows; r++) {
			for (int col = 0; col < cols; col++) {
				int num = this.ticket[r][col];
				System.out.print(num + "\t");
			}
			// if (r != 2)
			System.out.println();
		}
		System.out.println();
		System.out.println();
	}

	public int getEmptyCellInCol(int rows, int c) {
		for (int i = 0; i < rows; i++) {
			if (this.ticket[i][c] == 0)
				return i;
		}
		return -1;
	}

	private void sortColumnWithTwoNumbers(int rows, int c) throws Exception {
		int emptyCell = this.getEmptyCellInCol(rows, c);
		if (emptyCell == -1) {
			throw new Exception("Hey! your column has 3 cells filled, invalid function called");
		}

		int cell1, cell2;
		if (emptyCell == 0) {
			cell1 = 1;
			cell2 = 2;
		} else if (emptyCell == 1) {
			cell1 = 0;
			cell2 = 2;
		} else { // emptyCell == 2
			cell1 = 0;
			cell2 = 1;
		}

		if (this.ticket[cell1][c] < this.ticket[cell2][c]) {
			return;
		} else {
			// swap
			int temp = this.ticket[cell1][c];
			this.ticket[cell1][c] = this.ticket[cell2][c];
			this.ticket[cell2][c] = temp;
		}
	}

	private void sortColumn(int rows, int c) throws Exception {
		if (this.getColCount(c, rows) == 1) {
			return;
		} else {
			this.sortColumnWithTwoNumbers(rows, c);
		}
	}

	public void sortTicket(int rows, int cols) throws Exception {
		for (int c = 0; c < cols; c++) {
			this.sortColumn(rows, c);
		}
	}

	public static void main(String... strings) {
		try {
			int rows = 3;
			int cols = 9;

			Scanner sc = new Scanner(System.in);
			System.out.println("Please enter the colomns number should be greater 9 ?");
			cols = sc.nextInt();
			if(cols < 9) {
				System.out.println("Please enter the value greater then 9.");
				return;
			}
			HousieTicketLogic housieTicketLogic = new HousieTicketLogic();
			housieTicketLogic.inlialise(rows, cols);

			for (int i = 0; i < rows; i++) {

				if (i > 1 && housieTicketLogic.getNumberOfElementsFilled(rows, cols) == rows * 5) {
					break;
				}

				while (true) {
					int colsIndex = housieTicketLogic.getRand(0, cols - 1);
					int value = housieTicketLogic.getRand(colsIndex * 10 + 1, colsIndex * 10 + 10);
					if (i > 1 && housieTicketLogic.getColCount(colsIndex, rows) == 2) {
						continue;
					}

					if (housieTicketLogic.getRowCount(cols, i) == 5) {
						break;
					}

					housieTicketLogic.updateValue(i, colsIndex, value);
				}
			}

			housieTicketLogic.sortTicket(rows, cols);
			housieTicketLogic.printTicket(rows, cols);
		} catch (Exception e) {
			System.out.println("Some error occured, try later");
		}
	}
}

