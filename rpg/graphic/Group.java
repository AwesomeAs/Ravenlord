package graphic;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

public class Group extends Drawable {
	
	private ArrayList<Object> list = new ArrayList<Object>();

	@Override
	public void onDraw(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean add(Drawable d) {
		return list.add(d);
	}
	
	public boolean add(Group g) {
		return list.add(g);
	}
	
	public boolean remove(Object o) {
		return list.remove(o);
	}
	
	public Drawable[] getObjects() {
		ArrayList<Drawable> d = new ArrayList<Drawable>();
		Iterator<Object> iter = list.iterator();
		while (iter.hasNext()) {
			Object o = iter.next();
			if (o instanceof Drawable) {
				d.add(((Drawable)o));
			} else {
				Drawable[] l = ((Group)o).getObjects();
				for (int i = 0; i < l.length; i++) {
					d.add(l[i]);
				}
			}
		}
		Drawable[] l = new Drawable[d.size()];
		for (int i = 0; i < d.size(); i++) {
			l[i] = d.get(i);
		}
		return l;
	}
	
}
