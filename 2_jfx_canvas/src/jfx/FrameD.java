package jfx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public abstract class FrameD
//	extends JFrame
//	implements MouseListener, MouseMotionListener 
{

	public abstract void init(ThreeD threeD);

	public ThreeD threeD;
	public Canvas canvas;

	public FrameD(Stage stage) {
		int width = (int) stage.getWidth();
		int height = (int) stage.getWidth();
		if (stage.getWidth() <= 20)
			stage.setWidth(width = 400);
		if (stage.getHeight() <= 20)
			stage.setHeight(height = 400);
		threeD = new ThreeD();
		init(threeD);
		threeD.init();
		threeD.run(width, height);

		StackPane body = new StackPane();
		Scene scene = new Scene(body);
		stage.setScene(scene);

		canvas = new Canvas() {
			@Override
			public boolean isResizable() {
				return true;
			}
		};
		canvas.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				paint();
			}
		});
		canvas.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				paint();
			}
		});
		body.getChildren().add(canvas);
		canvas.widthProperty().bind(body.widthProperty());
		canvas.heightProperty().bind(body.heightProperty());
		canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				mousePressed(event);
			}
		});
		canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				mouseDragged(event);
			}
		});

		stage.show();
	}

//	private BufferedImage bi;
	private int prevx, prevy;
	private boolean painted = true;

	public void paint() {
		GraphicsContext g = canvas.getGraphicsContext2D();
		paint(g);
	}

	public void paint(GraphicsContext g) {
		int width = (int) canvas.getWidth();
		int height = (int) canvas.getWidth();
		g.setFill(Color.WHITE);
		g.fillRect(0, 0, width, height);
		threeD.paint(g, width, height);
		painted = true;
	}

	public void mousePressed(MouseEvent e) {
		prevx = (int) e.getSceneX();
		prevy = (int) e.getSceneY();
		e.consume();
	}

	public void mouseDragged(MouseEvent e) {
		int x = (int) e.getSceneX();
		int y = (int) e.getSceneY();
		float xtheta = (prevy - y) * 360.0f / (float) canvas.getWidth();
		float ytheta = (x - prevx) * 360.0f / (float) canvas.getHeight();
		threeD.dragged(xtheta, ytheta);
		if (painted) {
			painted = false;
			paint();
		}
		prevx = x;
		prevy = y;
		e.consume();
	}
}
