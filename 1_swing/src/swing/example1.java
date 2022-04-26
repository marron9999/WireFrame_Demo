package swing;

public class example1 {
	public void run() {
		new FrameD() {
			private static final long serialVersionUID = 1L;

			@Override
			public void init(ThreeD threeD) {
				setTitle("3D Model: Cube");
				setSize(600, 600);
				threeD.setProperty("scale", "0.7");
				threeD.setProperty("model", "models/cube.obj");
				threeD.setProperty("alt",
						"Your browser understands the &lt;APPLET&gt; tag but isn't running the applet, for some reason."
								+ "Your browser is completely ignoring the &lt;APPLET&gt; tag!");
				// <a href="ThreeD.java">The source</a>.
			}
		};
	}

	public static void main(String[] args) {
		try {
			new example1().run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
