import java.util.Random;

import javafx.event.EventHandler;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public abstract class FrameD
{

	public abstract void init(ThreeD threeD);

	public ThreeD threeD;
	public TriangleMesh mesh;
	public PerspectiveCamera camera;
	public PointLight light1;
	public AmbientLight light0;
	public Rotate rX, rY, rZ;
	public StackPane body;
	public Group group;
	
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

		group = new Group();
		rX = new Rotate(0, Rotate.X_AXIS);
		rY = new Rotate(0, Rotate.Y_AXIS);
		rZ = new Rotate(0, Rotate.Z_AXIS);
		group.getTransforms().addAll(rX, rY, rZ);
		body = new StackPane();
		body.getChildren().add(group);
		Scene scene = new Scene(body, width, height, true);
		stage.setScene(scene);
		

		mesh = threeD.md.triangleMesh();
		MeshView view = new MeshView();
		view.setDrawMode(DrawMode.FILL);
		PhongMaterial material = new PhongMaterial();
		material.setDiffuseColor(Color.SILVER);
		material.setSpecularColor(Color.WHITE);
		view.setMaterial(material);
		view.setMesh(mesh);
		//view.getTransforms().add(new Rotate(90, Rotate.X_AXIS));
		float x = Math.abs(threeD.md.xmax - threeD.md.xmin) * threeD.md.xfac / 2;
		float y = Math.abs(threeD.md.ymax - threeD.md.ymin) * threeD.md.xfac / 2;
		float z = Math.abs(threeD.md.zmax - threeD.md.zmin) * threeD.md.xfac / 2;
		setTranslate(view,
				- threeD.md.xmin * threeD.md.xfac - x,
				- threeD.md.ymin * threeD.md.xfac - y,
				- threeD.md.zmin * threeD.md.xfac - z);
		group.getChildren().add(view);
		System.out.println("Points: " + mesh.getPoints().size());
		System.out.println("Faces:  " + mesh.getFaces().size());
		System.out.println("Views:  " + group.getChildren().size());

		scene.setFill(Color.WHITE);
		camera  = new PerspectiveCamera(true);
		camera.setFarClip(1000);
		setTranslate(camera, 0, 0, -800);
		scene.setCamera(camera);
		body.getChildren().add(camera);

		light0 = new AmbientLight(Color.color(0.2, 0.2, 0.2));
		light1 = new PointLight();
		setTranslate(light1, 0, -500, -500);
		body.getChildren().addAll(light0,light1);

		body.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				mousePressed(event);
			}
		});
		body.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				mouseDragged(event);
			}
		});

		stage.show();
	}

	Random random = new Random();

	private int prevx;
	private int prevy;

	private void setTranslate(Node node, double x, double y, double z) {
		//node = new AmbientLight();
		node.setTranslateX(x);
		node.setTranslateY(y);
		node.setTranslateZ(z);
	}
	public void mousePressed(MouseEvent e) {
		prevx = (int) e.getSceneX();
		prevy = (int) e.getSceneY();
		e.consume();
	}

	public void mouseDragged(MouseEvent e) {
		int x = (int) e.getSceneX();
		int y = (int) e.getSceneY();
		float w = Math.abs(threeD.md.xmax - threeD.md.xmin) * threeD.md.xfac;
		float h = Math.abs(threeD.md.ymax - threeD.md.ymin) * threeD.md.xfac;
		double xtheta = (y - prevy) * 360.0d / w / 4d;
		double ytheta = (prevx - x) * 360.0d / h / 4d;
		if( ! e.isControlDown()) {
			//System.out.println("X:" + xtheta + ", Y:" + ytheta);
			rX.setAngle(rX.getAngle() + xtheta);
			rY.setAngle(rY.getAngle() + ytheta);
		} else {
			//System.out.println("X:" + xtheta + ", Y:" + ytheta);
			rZ.setAngle(rZ.getAngle() + xtheta);
			rY.setAngle(rY.getAngle() + ytheta);
		}
		prevx = x;
		prevy = y;
		e.consume();
	}
}
