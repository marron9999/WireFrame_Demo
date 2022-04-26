package mesh;

import javafx.application.Application;
import javafx.stage.Stage;

public class example3 extends Application {
	@Override
	public void start(final Stage stage) {
		stage.setTitle("FX: 3D Model: Hughes");
		stage.setWidth(600);
		stage.setHeight(600);
		new FrameD(stage) {
			@Override
			public void init(ThreeD threeD) {
				// threeD.setProperty("scale", "2.0");
				threeD.setProperty("model", "models/hughes_500.obj");
				threeD.setProperty("alt",
						"Your browser understands the &lt;APPLET&gt; tag but isn't running the applet, for some reason."
								+ "Your browser is completely ignoring the &lt;APPLET&gt; tag!");
				// <a href="ThreeD.java">The source</a>.
			}
		};
	}

	public static void main(String[] args) {
		try {
			launch(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
