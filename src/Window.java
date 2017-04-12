import java.awt.Color;
import java.awt.Graphics;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class Window extends JPanel {

	private int width, height;
	private int scaleUnit;

	private Timer timer;
	private Cell[][] cells;
	private ArrayList<Cell> visitedCells;
	private Cell cursor;

	public Window(int w, int h, int scale) {
		int s = Math.min(w, h);
		setSize(s, s);

		setVisible(true);
		width = s;
		height = s;
		scaleUnit = scale; // number of boxes in a given s value.
		cells = new Cell[s / scaleUnit][s / scaleUnit];
		visitedCells = new ArrayList<Cell>();
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells.length; j++) {
				cells[i][j] = new Cell();
				cells[i][j].setX(j);
				cells[i][j].setY(i);
			}
		}
		// first step here
		cursor = cells[0][0];
		cursor.setVisited(true);
		visitedCells.add(cursor);

		KeyboardFocusManager.getCurrentKeyboardFocusManager()
				.addKeyEventDispatcher(new KeyEventDispatcher() {

					@Override
					public boolean dispatchKeyEvent(KeyEvent e) {
						if (e.getKeyChar() == 'r') {
							visitedCells.clear();
							timer.cancel();
							timer = new Timer();
							for (int i = 0; i < cells.length; i++) {
								for (int j = 0; j < cells.length; j++) {
									cells[i][j].setVisited(false);
									cells[i][j].setEastWall(true);
									cells[i][j].setWestWall(true);
									cells[i][j].setNorthWall(true);
									cells[i][j].setSouthWall(true);
								}
							}
							cursor = cells[0][0];
							timer.scheduleAtFixedRate(new TimeTask(), 0, 200);
						}
						return false;
					}
				});

		repaint();
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimeTask(), 0, 1);
	}

	public boolean checkForUnivistedCellsNextToCursor() {
		// checking cells next to cursor
		if (cursor.getX() + 1 < width / scaleUnit
				&& !cells[cursor.getY()][cursor.getX() + 1].isVisited()) {
			return true;
		}
		if (cursor.getX() - 1 >= 0
				&& !cells[cursor.getY()][cursor.getX() - 1].isVisited()) {
			return true;
		}
		if (cursor.getY() + 1 < width / scaleUnit
				&& !cells[cursor.getY() + 1][cursor.getX()].isVisited()) {
			return true;
		}
		if (cursor.getY() - 1 >= 0
				&& !cells[cursor.getY() - 1][cursor.getX()].isVisited()) {
			return true;
		}
		return false;
	}

	public boolean checkForUnivistedCells() {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells.length; j++) {
				if (!cells[i][j].isVisited()) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);

		g.setColor(Color.red);
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells.length; j++) {
				if (cells[i][j].isVisited() == false) {
					g.setColor(Color.red);
				} else {
					g.setColor(Color.white);
				}
				g.fillRect(j * scaleUnit, i * scaleUnit, scaleUnit, scaleUnit);
				// painting walls
				g.setColor(Color.BLACK);
				if (cells[i][j].isNorthWall()) {
					g.drawLine(scaleUnit * j, scaleUnit * i, scaleUnit * j
							+ scaleUnit, scaleUnit * i);
				}
				if (cells[i][j].isSouthWall()) {
					g.drawLine(scaleUnit * j, scaleUnit * i + scaleUnit,
							scaleUnit * j + scaleUnit, scaleUnit * i
									+ scaleUnit);
				}
				if (cells[i][j].isWestWall()) {
					g.drawLine(scaleUnit * j, scaleUnit * i, scaleUnit * j,
							scaleUnit * i + scaleUnit);
				}
				if (cells[i][j].isEastWall()) {
					g.drawLine(scaleUnit * j + scaleUnit, scaleUnit * i,
							scaleUnit * j + scaleUnit, scaleUnit * i
									+ scaleUnit);
				}
			}
		}
		g.setColor(Color.blue);
		g.fillRect(cursor.getX() * scaleUnit, cursor.getY() * scaleUnit,
				scaleUnit - 1, scaleUnit - 1);
	}

	public class TimeTask extends TimerTask {

		@Override
		public void run() {

			// second step, checking for unvisited cells
			boolean unvisited = checkForUnivistedCells();

			if (unvisited) {
				if (checkForUnivistedCellsNextToCursor()) {
					boolean found = false;
					int dir = -1;
					while (!found) {
						dir = (int) new Random().nextInt(4);
						switch (dir) {
						case 0:
							if (cursor.getX() + 1 < width / scaleUnit) {
								found = true;
							}
							break;
						case 1:
							if (cursor.getY() - 1 >= 0) {
								found = true;
							}
							break;
						case 2:
							if (cursor.getX() - 1 >= 0) {
								found = true;
							}
							break;
						case 3:
							if (cursor.getY() + 1 < width / scaleUnit) {
								found = true;
							}
							break;
						}
					}

					if (dir == 0) {
						if (!cells[cursor.getY()][cursor.getX() + 1]
								.isVisited()) {
							cursor.setEastWall(false);
							cursor = cells[cursor.getY()][cursor.getX() + 1];
							cursor.setVisited(true);
							cursor.setWestWall(false);
							visitedCells.add(cursor);
						}
					} else if (dir == 1) {
						if (!cells[cursor.getY() - 1][cursor.getX()]
								.isVisited()) {
							cursor.setNorthWall(false);
							cursor = cells[cursor.getY() - 1][cursor.getX()];
							cursor.setVisited(true);
							cursor.setSouthWall(false);
							visitedCells.add(cursor);
						}
					} else if (dir == 2) {
						if (!cells[cursor.getY()][cursor.getX() - 1]
								.isVisited()) {
							cursor.setWestWall(false);
							cursor = cells[cursor.getY()][cursor.getX() - 1];
							cursor.setVisited(true);
							cursor.setEastWall(false);
							visitedCells.add(cursor);
						}
					} else if (dir == 3) {
						if (!cells[cursor.getY() + 1][cursor.getX()]
								.isVisited()) {
							cursor.setSouthWall(false);
							cursor = cells[cursor.getY() + 1][cursor.getX()];
							cursor.setVisited(true);
							cursor.setNorthWall(false);
							visitedCells.add(cursor);
						}
					}

				} else {
					// if no neighbors for cursor
					cursor = visitedCells
							.get((int) (Math.random() * visitedCells.size()));
				}

			} else {
				// if all cells have been visited
				System.out.println("Generation Completed!");
				// for (int i = 0; i < cells.length; i++) {
				// for (int j = 0; j < cells.length; j++) {
				// System.out.println("(" + i + ", " + j + " ) " +
				// cells[i][j].isVisited());
				// }
				// }
				cursor = new Cell();
				cursor.setX(-scaleUnit);
				cursor.setY(-scaleUnit);
				Window.this.repaint();
				timer.cancel();
			}
			Window.this.repaint();
		}

	}

}
