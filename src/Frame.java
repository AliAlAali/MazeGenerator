import javax.swing.JFrame;


public class Frame extends JFrame{

	public Frame(Window win) {
		setSize(win.getWidth(), win.getHeight());
		setTitle("The Maze");
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(win);
	}
	
	public static void main(String[] args) {
		new Frame(new Window(700, 700, 10));
	}
}
