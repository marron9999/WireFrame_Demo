import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public abstract class FrameD
	extends JFrame
	implements MouseListener, MouseMotionListener
{
	private static final long serialVersionUID = 1L;

	public abstract void init(ThreeD threeD);

	public ThreeD threeD;

	public FrameD() {
		threeD = new ThreeD();
		init(threeD);
		setSize(getSize().width <= 20 ? 400 : getSize().width, getSize().height <= 20 ? 400 : getSize().height);
		threeD.init();
		threeD.run(getSize().width, getSize().height);
		// repaint();
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addMouseListener(this);
		addMouseMotionListener(this);
		setVisible(true);
	}

	private BufferedImage bi;
	private int prevx, prevy;
	private boolean painted = true;

	@Override
	public void paint(Graphics g) {
		Dimension size = getSize();
		if (bi == null || bi.getWidth() != size.width || bi.getHeight() != size.height)
			bi = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
		Graphics gg = bi.getGraphics();
		gg.setColor(Color.WHITE);
		gg.fillRect(0, 0, size.width, size.height);
		threeD.paint(gg, size.width, size.height);
		gg.dispose();
		g.drawImage(bi, 0, 0, null);
		painted = true;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		prevx = e.getX();
		prevy = e.getY();
		e.consume();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		float xtheta = (prevy - y) * 360.0f / getSize().width;
		float ytheta = (x - prevx) * 360.0f / getSize().height;
		threeD.dragged(xtheta, ytheta);
		if (painted) {
			painted = false;
			repaint();
		}
		prevx = x;
		prevy = y;
		e.consume();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
}
