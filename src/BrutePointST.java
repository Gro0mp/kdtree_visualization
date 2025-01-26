import dsa.LinkedQueue;
import dsa.MinPQ;
import dsa.Point2D;
import dsa.RectHV;
import dsa.RedBlackBinarySearchTreeST;
import stdlib.StdIn;
import stdlib.StdOut;

public class BrutePointST<Value> implements PointST<Value> {
    RedBlackBinarySearchTreeST<Point2D, Value> bst; // Creates a new Red Black
    // Binary Search Tree Symbol Table

    // Constructs an empty symbol table.
    public BrutePointST() {
        bst = new RedBlackBinarySearchTreeST<>();
    }

    // Returns true if this symbol table is empty, and false otherwise.
    public boolean isEmpty() {
        return bst.isEmpty();
    }

    // Returns the number of key-value pairs in this symbol table.
    public int size() {
        return bst.size();
    }

    // Inserts the given point and value into this symbol table.
    public void put(Point2D p, Value value) {
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        if (value == null) {
            throw new NullPointerException("value is null");
        }
        bst.put(p, value);
    }

    // Returns the value associated with the given point in this symbol table, or null.
    public Value get(Point2D p) {
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        return bst.get(p);
    }

    // Returns true if this symbol table contains the given point, and false otherwise.
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        return bst.contains(p);
    }

    // Returns all the points in this symbol table.
    public Iterable<Point2D> points() {
        return bst.keys(bst.min(), bst.max());
    }

    // Returns all the points in this symbol table that are inside the given rectangle.
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new NullPointerException("rect is null");
        }
        // Creates a new queue and enqueues every point in points
        // that is contained in rect. Returns the queue.
        LinkedQueue<Point2D> q = new LinkedQueue<>();
        for (Point2D p : points()) {
            if (rect.contains(p)) {
                q.enqueue(p);
            }
        }
        return q;
    }

    // Returns the point in this symbol table that is different from and closest to the given point,
    // or null.
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        Point2D diffP = p;
        // For every point in nearest(), if the given point is different to the point
        // in the symbol table, set diffP to that point.
        for (Point2D points : nearest(p, 1)) {
            if (!p.equals(points)) {
                diffP = points;
            }
        }
        return diffP;
    }

    // Returns up to k points from this symbol table that are different from and closest to the
    // given point.
    public Iterable<Point2D> nearest(Point2D p, int k) {
        // The MinPQ is used to traverse the symbol table, and the
        // LinkedQueue stores the closet k points to the given point.
        // Count is used to count the number of values stored in q
        // so far.
        MinPQ<Point2D> pq = new MinPQ<>(p.distanceToOrder());
        LinkedQueue<Point2D> q = new LinkedQueue<>();
        int count  = 0;
        // For every point in points(), insert the point into pq.
        for (Point2D point : points()) {
            pq.insert(point);
        }
        // While count is less than k, set a temporary Point2D value to
        // pq.delMin(). And if p does not equal to temp, enqueue temp into
        // q and increment count.
        while (count < k) {
            Point2D temp = pq.delMin();
            if (!p.equals(temp)) {
                q.enqueue(temp);
                ++count;
            }
        }
        return q;
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        BrutePointST<Integer> st = new BrutePointST<>();
        double qx = Double.parseDouble(args[0]);
        double qy = Double.parseDouble(args[1]);
        int k = Integer.parseInt(args[2]);
        Point2D query = new Point2D(qx, qy);
        RectHV rect = new RectHV(-1, -1, 1, 1);
        int i = 0;
        while (!StdIn.isEmpty()) {
            double x = StdIn.readDouble();
            double y = StdIn.readDouble();
            Point2D p = new Point2D(x, y);
            st.put(p, i++);
        }
        StdOut.println("st.empty()? " + st.isEmpty());
        StdOut.println("st.size() = " + st.size());
        StdOut.printf("st.contains(%s)? %s\n", query, st.contains(query));
        StdOut.printf("st.range(%s):\n", rect);
        for (Point2D p : st.range(rect)) {
            StdOut.println("  " + p);
        }
        StdOut.printf("st.nearest(%s) = %s\n", query, st.nearest(query));
        StdOut.printf("st.nearest(%s, %d):\n", query, k);
        for (Point2D p : st.nearest(query, k)) {
            StdOut.println("  " + p);
        }
    }
}
