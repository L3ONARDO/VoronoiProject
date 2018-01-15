import model.DCEL;
import model.HalfEdge;
import model.HalfEdgeBuilder;

public class Main {

    public static void main(String[] args) {
        DCEL dcel = new DCEL();
        HalfEdgeBuilder heb = new HalfEdgeBuilder();
        HalfEdge e1 = heb.buildPair(true, 0, 0, 0, 1);
        HalfEdge e2 = heb.buildPair(true, 0, 1, 1, 0);
        HalfEdge e3 = heb.buildPair(true, 1, 0, 0, 0);
        dcel.addBack(e2);
        dcel.addBack(e3);
        dcel.addFront(e1);
        System.out.println("Done!");
    }
}
