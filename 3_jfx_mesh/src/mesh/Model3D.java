package mesh;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javafx.geometry.Point3D;
import javafx.scene.shape.TriangleMesh;

//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.paint.Color;

/** The representation of a 3D model */
final class Model3D {

//	float vert[];
//	int tvert[];
//	int nvert, maxvert;
//	int con[];
//	int ncon, maxcon;
//	boolean transformed;
//	Matrix3D mat;
	float xmin, xmax, ymin, ymax, zmin, zmax;
	float xfac = 1;

	List<Point3D> verts = new ArrayList<>();
	List<int[]> cons = new ArrayList<>();
	List<Long> fos = new ArrayList<>();
	
	Model3D() {
//		mat = new Matrix3D();
//		mat.xrot(20);
//		mat.yrot(30);
	}

	/** Create a 3D model by parsing an input stream */
	Model3D(InputStream is) throws Exception {
		this();
		StreamTokenizer st = new StreamTokenizer(new BufferedReader(new InputStreamReader(is, "UTF-8")));
		st.eolIsSignificant(true);
		st.commentChar('#');
		scan: while (true) {
			switch (st.nextToken()) {
			default:
				break scan;
			case StreamTokenizer.TT_EOL:
				break;
			case StreamTokenizer.TT_WORD:
				if ("v".equals(st.sval)) {
					double x = 0, y = 0, z = 0;
					if (st.nextToken() == StreamTokenizer.TT_NUMBER) {
						x = st.nval;
						if (st.nextToken() == StreamTokenizer.TT_NUMBER) {
							y = st.nval;
							if (st.nextToken() == StreamTokenizer.TT_NUMBER) {
								z = st.nval;
							}
						}
					}
					//addVert((float) x, (float) y, (float) z);
					verts.add(new Point3D(x, y, z));
					while (st.ttype != StreamTokenizer.TT_EOL && st.ttype != StreamTokenizer.TT_EOF) {
						st.nextToken();
					}
				} else if ("f".equals(st.sval) || "fo".equals(st.sval) || "l".equals(st.sval)) {
					String sv = st.sval;
					int start = -1;
					int prev = -1;
					int n = -1;
					int[] ns = new int [32];
					int ni = 0;
					while (true) {
						if (st.nextToken() == StreamTokenizer.TT_NUMBER) {
							n = (int) st.nval;
							if (prev >= 0) {
								if(prev == n) continue;
								//add(prev - 1, n - 1);
								cons.add(new int[] {prev - 1, n - 1});
							}
							if (start < 0) {
								start = n;
							}
							prev = n;
							ns[ni] = n - 1;
							ni++;
						} else if (st.ttype == '/') {
							st.nextToken();
						} else {
							break;
						}
					}
					if (start >= 0) {
						//add(start - 1, prev - 1);
						cons.add(new int[] {start - 1, prev - 1});
					}
					if("fo".equals(sv) || "f".equals(sv)) {
						if(ni >= 3) {
							for(int i=2; i<ni; i++) {
								long nv = (((long) ns[0]) << 32)
										| (((long) ns[i -1]) << 16)
										| ( ns[i]);
									if( ! fos.contains(nv))
										fos.add(nv);
							}
						}
//						else {
//							// error !
//							break scan;
//						}
					}
					if (st.ttype != StreamTokenizer.TT_EOL) {
						break scan;
					}
				} else {
					while (st.nextToken() != StreamTokenizer.TT_EOL && st.ttype != StreamTokenizer.TT_EOF) {
						// no-op
					}
				}
			}
		}
		is.close();
		if (st.ttype != StreamTokenizer.TT_EOF) {
			throw new Exception("Format:" + st.toString());
		}
	}

//	/** Add a vertex to this model */
//	int addVert(float x, float y, float z) {
//		int i = nvert;
//		if (i >= maxvert) {
//			if (vert == null) {
//				maxvert = 100;
//				vert = new float[maxvert * 3];
//			} else {
//				maxvert *= 2;
//				float nv[] = new float[maxvert * 3];
//				System.arraycopy(vert, 0, nv, 0, vert.length);
//				vert = nv;
//			}
//		}
//		i *= 3;
//		vert[i] = x;
//		vert[i + 1] = y;
//		vert[i + 2] = z;
//		return nvert++;
//	}

//	/** Add a line from vertex p1 to vertex p2 */
//	void add(int p1, int p2) {
//		int i = ncon;
//		if (p1 >= nvert || p2 >= nvert) {
//			return;
//		}
//		if (i >= maxcon) {
//			if (con == null) {
//				maxcon = 100;
//				con = new int[maxcon];
//			} else {
//				maxcon *= 2;
//				int nv[] = new int[maxcon];
//				System.arraycopy(con, 0, nv, 0, con.length);
//				con = nv;
//			}
//		}
//		if (p1 > p2) {
//			int t = p1;
//			p1 = p2;
//			p2 = t;
//		}
//		con[i] = (p1 << 16) | p2;
//		ncon = i + 1;
//	}

//	/** Transform all the points in this model */
//	void transform() {
//		if (transformed || nvert <= 0) {
//			return;
//		}
//		if (tvert == null || tvert.length < nvert * 3) {
//			tvert = new int[nvert * 3];
//		}
//		mat.transform(vert, tvert, nvert);
//		transformed = true;
//	}

//	/*
//	 * Quick Sort implementation
//	 */
//	private void quickSort(int a[], int left, int right) {
//		int leftIndex = left;
//		int rightIndex = right;
//		int partionElement;
//		if (right > left) {
//
//			/*
//			 * Arbitrarily establishing partition element as the midpoint of the array.
//			 */
//			partionElement = a[(left + right) / 2];
//
//			// loop through the array until indices cross
//			while (leftIndex <= rightIndex) {
//				/*
//				 * find the first element that is greater than or equal to the partionElement
//				 * starting from the leftIndex.
//				 */
//				while ((leftIndex < right) && (a[leftIndex] < partionElement)) {
//					++leftIndex;
//				}
//
//				/*
//				 * find an element that is smaller than or equal to the partionElement starting
//				 * from the rightIndex.
//				 */
//				while ((rightIndex > left) && (a[rightIndex] > partionElement)) {
//					--rightIndex;
//				}
//
//				// if the indexes have not crossed, swap
//				if (leftIndex <= rightIndex) {
//					swap(a, leftIndex, rightIndex);
//					++leftIndex;
//					--rightIndex;
//				}
//			}
//
//			/*
//			 * If the right index has not reached the left side of array must now sort the
//			 * left partition.
//			 */
//			if (left < rightIndex) {
//				quickSort(a, left, rightIndex);
//			}
//
//			/*
//			 * If the left index has not reached the right side of array must now sort the
//			 * right partition.
//			 */
//			if (leftIndex < right) {
//				quickSort(a, leftIndex, right);
//			}
//
//		}
//	}

//	private void swap(int a[], int i, int j) {
//		int T;
//		T = a[i];
//		a[i] = a[j];
//		a[j] = T;
//	}

//	/** eliminate duplicate lines */
//	void compress() {
//		int limit = ncon;
//		int c[] = con;
//		quickSort(con, 0, ncon - 1);
//		int d = 0;
//		int pp1 = -1;
//		for (int i = 0; i < limit; i++) {
//			int p1 = c[i];
//			if (pp1 != p1) {
//				c[d] = p1;
//				d++;
//			}
//			pp1 = p1;
//		}
//		ncon = d;
//	}

//	static Color gr[];

//	/**
//	 * Paint this model to a graphics context. It uses the matrix associated with
//	 * this model to map from model space to screen space. The next version of the
//	 * browser should have double buffering, which will make this *much* nicer
//	 */
//	void paint(GraphicsContext g) {
//		if (vert == null || nvert <= 0) {
//			return;
//		}
//		transform();
//		if (gr == null) {
//			gr = new Color[16];
//			for (int i = 0; i < 16; i++) {
//				int grey = (int) (170 * (1 - Math.pow(i / 15.0, 2.3)));
//				gr[i] = Color.color(grey / 255d, grey / 255d, grey / 255d);
//			}
//		}
//		int lg = 0;
//		int lim = ncon;
//		int c[] = con;
//		int v[] = tvert;
//		if (lim <= 0 || nvert <= 0) {
//			return;
//		}
//		for (int i = 0; i < lim; i++) {
//			int T = c[i];
//			int p1 = ((T >> 16) & 0xFFFF) * 3;
//			int p2 = (T & 0xFFFF) * 3;
//			int grey = v[p1 + 2] + v[p2 + 2];
//			if (grey < 0) {
//				grey = 0;
//			}
//			if (grey > 15) {
//				grey = 15;
//			}
//			if (grey != lg) {
//				lg = grey;
//				g.setStroke(gr[grey]);
//			}
//			g.strokeLine(v[p1], v[p1 + 1], v[p2], v[p2 + 1]);
//		}
//	}

	/** Find the bounding box of this model */
	void findBB() {
//		if (nvert <= 0) {
//			return;
//		}
		Point3D v = verts.get(0);
		xmin = xmax = (float) v.getX();
		ymin = ymax = (float) v.getY();
		zmin = zmax = (float) v.getZ();
		for (int i = 1; i<verts.size(); i++) {
			v = verts.get(i);
			xmin = Math.min(xmin, (float) v.getX());
			xmax = Math.max(xmax, (float) v.getX());
			ymin = Math.min(ymin, (float) v.getY());
			ymax = Math.max(ymax, (float) v.getY());
			zmin = Math.min(zmin, (float) v.getZ());
			zmax = Math.max(zmax, (float) v.getZ());
		}
	}

	TriangleMesh triangleMesh() {
//		System.out.println("verts:" + verts.size());
//		System.out.println("fos:" + fos.size());
		TriangleMesh mesh = new TriangleMesh();
		for (Point3D v : verts) {
			mesh.getPoints().addAll(
					(float) v.getX() * xfac,
					(float) v.getY() * xfac,
					(float) v.getZ() * xfac);
		}
		if(fos.size() > 0) {
			for (long fo : fos) {
				mesh.getFaces().addAll(
					(int)(fo >> 32), 0,
					(int)((fo >> 16) & 0x0000ffff), 0,
					(int)(fo & 0x0000ffff), 0);
			}
		} else {
			for (long fo : fos) {
				mesh.getFaces().addAll(
					(int)(fo >> 32), 0,
					(int)((fo >> 16) & 0x0000ffff), 0,
					(int)(fo & 0x0000ffff), 0);
			}
		}
		mesh.getTexCoords().addAll(0f, 0f);
		return mesh;
	}
}
